package net.satisfy.alpinewhispers.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;

public class SinkBlockEntity extends BlockEntity {
    public static final int MAX_LEVEL = 3;
    public static final int FILL_TICKS = 40;
    public static final int TURN_TICKS = 6;

    private int waterLevel;
    private boolean open;
    private long toggleTime = -100;
    private int fillTicks;
    private float displayLevel;
    private float prevDisplayLevel;
    private float slosh;
    private float prevSlosh;

    public SinkBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.SINK_BLOCK_ENTITY.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SinkBlockEntity sink) {
        if (!sink.open || sink.waterLevel >= MAX_LEVEL) {
            return;
        }
        if (++sink.fillTicks >= FILL_TICKS) {
            sink.fillTicks = 0;
            sink.waterLevel++;
            level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 0.25F, 1.4F);
            sink.sync();
        }
    }

    // Client only: the level rises smoothly between the server's whole steps, and the surface rocks while the tap runs.
    public static void clientTick(SinkBlockEntity sink) {
        sink.prevDisplayLevel = sink.displayLevel;
        if (sink.open && sink.waterLevel < MAX_LEVEL) {
            float rising = Math.min(sink.displayLevel + 1.0F / FILL_TICKS, sink.waterLevel + 1.0F);
            sink.displayLevel = Math.max(rising, sink.waterLevel);
        } else {
            sink.displayLevel = Mth.approach(sink.displayLevel, sink.waterLevel, 0.15F);
        }
        sink.prevSlosh = sink.slosh;
        sink.slosh = sink.open && sink.waterLevel < MAX_LEVEL ? Mth.approach(sink.slosh, 1.0F, 0.08F) : sink.slosh * 0.95F;
    }

    public float getDisplayLevel(float partial) {
        return Mth.lerp(partial, prevDisplayLevel, displayLevel);
    }

    public float getSlosh(float partial) {
        return Mth.lerp(partial, prevSlosh, slosh);
    }

    public void toggle() {
        if (level == null) {
            return;
        }
        open = !open;
        toggleTime = level.getGameTime();
        fillTicks = 0;
        level.playSound(null, worldPosition.above(), SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.4F, open ? 1.2F : 0.9F);
        sync();
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = Math.max(0, Math.min(MAX_LEVEL, waterLevel));
        sync();
    }

    /** Water surface height inside the basin of the lower half, in block units. */
    public static float surfaceHeight(float waterLevel) {
        return (12.0F + waterLevel * 1.25F) / 16.0F;
    }

    public boolean isOpen() {
        return open;
    }

    public long getToggleTime() {
        return toggleTime;
    }

    private void sync() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        waterLevel = tag.getInt("WaterLevel");
        open = tag.getBoolean("Open");
        toggleTime = tag.contains("ToggleTime") ? tag.getLong("ToggleTime") : -100;
        fillTicks = tag.getInt("FillTicks");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("WaterLevel", waterLevel);
        tag.putBoolean("Open", open);
        tag.putLong("ToggleTime", toggleTime);
        tag.putInt("FillTicks", fillTicks);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        saveAdditional(tag, provider);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
