package net.satisfy.alpinewhispers.neoforge.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

import net.satisfy.alpinewhispers.core.block.entity.FireplaceCorniceBlockEntity;
import net.satisfy.alpinewhispers.neoforge.client.renderer.block.FireplaceCorniceTexturedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireplaceCorniceBlockEntity.class)
abstract class FireplaceCorniceBlockEntityMixin {
    public ModelData getModelData() {
        BlockState mimic = ((FireplaceCorniceBlockEntity)(Object)this).getMimicState();
        if (mimic != null) {
            return ModelData.builder().with(FireplaceCorniceTexturedModel.MIMIC, mimic).build();
        }
        return ModelData.EMPTY;
    }

    @Inject(method = "setMimicState", at = @At("TAIL"))
    private void hearthAndTimber$refreshModelData(BlockState state, CallbackInfo ci) {
        FireplaceCorniceBlockEntity be = (FireplaceCorniceBlockEntity)(Object)this;
        Level level = be.getLevel();
        if (level == null || !level.isClientSide) return;
        be.requestModelDataUpdate();
        BlockPos pos = be.getBlockPos();
        BlockState bs = be.getBlockState();
        level.sendBlockUpdated(pos, bs, bs, Block.UPDATE_CLIENTS | Block.UPDATE_IMMEDIATE);
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void hearthAndTimber$refreshAfterLoad(CompoundTag tag, HolderLookup.Provider provider, CallbackInfo ci) {
        FireplaceCorniceBlockEntity be = (FireplaceCorniceBlockEntity)(Object)this;
        Level level = be.getLevel();
        if (level == null || !level.isClientSide) return;
        be.requestModelDataUpdate();
        BlockPos pos = be.getBlockPos();
        BlockState bs = be.getBlockState();
        level.sendBlockUpdated(pos, bs, bs, Block.UPDATE_CLIENTS | Block.UPDATE_IMMEDIATE);
    }
}