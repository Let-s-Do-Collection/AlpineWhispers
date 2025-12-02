package net.satisfy.alpinewhispers.fabric.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.satisfy.alpinewhispers.core.block.FrostyBlockInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushItem.class)
public abstract class BrushItemMixin {
    @Unique
    private static final int ALPINEWHISPERS_FROSTY_TICKS_REQUIRED = 30;

    @Shadow
    private HitResult calculateHitResult(Player player) {
        return null;
    }

    @Shadow
    public abstract int getUseDuration(ItemStack itemStack, LivingEntity livingEntity);

    @Inject(method = "onUseTick", at = @At("HEAD"))
    private void alpinewhispers$frostyBrush(Level level, LivingEntity livingEntity, ItemStack itemStack, int remainingUseTicks, CallbackInfo callbackInfo) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }

        HitResult hitResult = this.calculateHitResult(player);
        if (!(hitResult instanceof BlockHitResult blockHitResult) || hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (!blockState.hasProperty(FrostyBlockInterface.FROSTY) || !blockState.getValue(FrostyBlockInterface.FROSTY)) {
            return;
        }

        int useDuration = this.getUseDuration(itemStack, livingEntity);
        int elapsedTicks = useDuration - remainingUseTicks + 1;
        if (elapsedTicks < 0) {
            return;
        }

        if (level.isClientSide) {
            double centerX = blockPos.getX() + 0.5;
            double centerY = blockPos.getY() + 0.7;
            double centerZ = blockPos.getZ() + 0.5;
            level.addParticle(ParticleTypes.SNOWFLAKE, centerX, centerY, centerZ, 0.0, 0.02, 0.0);
            return;
        }

        if (elapsedTicks >= ALPINEWHISPERS_FROSTY_TICKS_REQUIRED) {
            level.setBlock(blockPos, blockState.setValue(FrostyBlockInterface.FROSTY, false), Block.UPDATE_ALL);
        }
    }
}