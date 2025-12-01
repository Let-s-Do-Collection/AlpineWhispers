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
        BlockPos pos = context.getClickedPos();
        BlockState clicked = level.getBlockState(pos);

        if (clicked.is(BlockTags.LEAVES)) {
            if (!level.isClientSide) {
                BlockState baublesState = getBlock().defaultBlockState();
                if (level.setBlock(pos, baublesState, Block.UPDATE_ALL)) {
                    level.playSound(context.getPlayer(), pos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof TreeBaublesBlockEntity treeBaublesBlockEntity) {
                        treeBaublesBlockEntity.setHeldBlock(clicked);
                        treeBaublesBlockEntity.setChanged();
                    }
                    Player player = context.getPlayer();
                    if (player != null && !player.getAbilities().instabuild) {
                        context.getItemInHand().shrink(1);
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }
}