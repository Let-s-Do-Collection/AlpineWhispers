package net.satisfy.alpinewhispers.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collections;

public interface FrostyBlockInterface {
    BooleanProperty FROSTY = BooleanProperty.create("frosty");

    default ItemInteractionResult handleFrostyUse(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!blockState.hasProperty(FROSTY)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (itemStack.is(Items.SNOWBALL)) {
            if (!level.isClientSide) {
                BlockState newState = getIncreasedFrostyState(blockState, level, blockPos);
                if (newState != blockState) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
            }
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    default void handleFrostyProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        if (level.isClientSide) {
            return;
        }

        if (!(projectile instanceof Snowball)) {
            return;
        }

        if (!blockState.hasProperty(FROSTY)) {
            return;
        }

        BlockPos blockPos = blockHitResult.getBlockPos();
        getIncreasedFrostyState(blockState, level, blockPos);
    }

    private static IntegerProperty getStageProperty(BlockState blockState) {
        for (Property<?> property : blockState.getProperties()) {
            if (property instanceof IntegerProperty integerProperty && property.getName().equals("stage")) {
                return integerProperty;
            }
        }
        return null;
    }

    private static BlockState getIncreasedFrostyState(BlockState blockState, Level level, BlockPos blockPos) {
        BlockState newState = blockState;
        boolean changed = false;

        if (!blockState.getValue(FROSTY)) {
            newState = newState.setValue(FROSTY, true);
            changed = true;

            if (level instanceof ServerLevel serverLevel) {
                double centerX = blockPos.getX() + 0.5;
                double centerY = blockPos.getY() + 0.5;
                double centerZ = blockPos.getZ() + 0.5;
                BlockParticleOption particleOption = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState());
                serverLevel.sendParticles(particleOption, centerX, centerY, centerZ, 8, 0.25, 0.25, 0.25, 0.02);
            }
        }

        IntegerProperty stageProperty = getStageProperty(blockState);
        if (stageProperty != null) {
            int currentStage = blockState.getValue(stageProperty);
            int maxStage = Collections.max(stageProperty.getPossibleValues());
            if (currentStage < maxStage) {
                newState = newState.setValue(stageProperty, currentStage + 1);
                changed = true;
            }
        }

        if (changed) {
            level.setBlock(blockPos, newState, Block.UPDATE_ALL);
        }

        return newState;
    }
}