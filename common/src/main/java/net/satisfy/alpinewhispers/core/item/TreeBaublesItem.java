package net.satisfy.alpinewhispers.core.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.alpinewhispers.core.block.entity.TreeBaublesBlockEntity;
import org.jetbrains.annotations.NotNull;

public class TreeBaublesItem extends BlockItem {
    public TreeBaublesItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState originalState = level.getBlockState(blockPos);

        if (!originalState.is(BlockTags.LEAVES)) {
            return super.useOn(context);
        }

        BlockState baublesState = getBlock().defaultBlockState();
        if (!level.setBlock(blockPos, baublesState, Block.UPDATE_ALL)) {
            return InteractionResult.FAIL;
        }

        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof TreeBaublesBlockEntity treeBaublesBlockEntity) {
            treeBaublesBlockEntity.setHeldBlock(originalState);
        }

        if (!level.isClientSide) {
            level.playSound(context.getPlayer(), blockPos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            Player player = context.getPlayer();
            if (player != null && !player.getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}