package net.satisfy.alpinewhispers.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public interface FrostyBlockInterface {
    BooleanProperty FROSTY = BooleanProperty.create("frosty");

    default BlockState frostyOff(BlockState blockState) {
        if (blockState.hasProperty(FROSTY)) {
            return blockState.setValue(FROSTY, false);
        }
        return blockState;
    }

    default ItemInteractionResult handleFrostyUse(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!blockState.hasProperty(FROSTY)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (itemStack.is(Items.SNOWBALL)) {
            if (!blockState.getValue(FROSTY)) {
                if (!level.isClientSide) {
                    level.setBlock(blockPos, blockState.setValue(FROSTY, true), Block.UPDATE_ALL);
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                }
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (itemStack.is(Items.BRUSH)) {
            if (blockState.getValue(FROSTY)) {
                if (!level.isClientSide) {
                    level.setBlock(blockPos, blockState.setValue(FROSTY, false), Block.UPDATE_ALL);
                }
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}