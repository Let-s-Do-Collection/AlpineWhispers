package net.satisfy.alpinewhispers.core.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrostySlabBlock extends SlabBlock implements FrostyBlockInterface {
    public FrostySlabBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM)
                        .setValue(BlockStateProperties.WATERLOGGED, false)
                        .setValue(FrostyBlockInterface.FROSTY, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.SLAB_TYPE, BlockStateProperties.WATERLOGGED, FrostyBlockInterface.FROSTY);
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState blockState) {
        return blockState.getValue(BlockStateProperties.SLAB_TYPE) != SlabType.DOUBLE;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        BlockState existing = blockPlaceContext.getLevel().getBlockState(blockPos);
        if (existing.is(this)) {
            return existing
                    .setValue(BlockStateProperties.SLAB_TYPE, SlabType.DOUBLE)
                    .setValue(BlockStateProperties.WATERLOGGED, false)
                    .setValue(FrostyBlockInterface.FROSTY, existing.getValue(FrostyBlockInterface.FROSTY));
        }

        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPos);
        BlockState base = this.defaultBlockState()
                .setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM)
                .setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(FrostyBlockInterface.FROSTY, false);

        boolean upper = blockPlaceContext.getClickLocation().y - (double) blockPos.getY() > 0.5;
        Direction face = blockPlaceContext.getClickedFace();

        if (face == Direction.DOWN) {
            return base.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
        }
        if (face == Direction.UP) {
            return base;
        }
        return upper ? base.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP) : base;
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(BlockStateProperties.WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return FrostyBlockInterface.super.handleFrostyUse(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        FrostyBlockInterface.super.handleFrostyProjectileHit(level, blockState, blockHitResult, projectile);
        super.onProjectileHit(level, blockState, blockHitResult, projectile);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        int beige = 0xF5DEB3;
        int gold = 0xFFD700;
        if (!Screen.hasShiftDown()) {
            Component key = Component.literal("[SHIFT]").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(gold)));
            list.add(Component.translatable("tooltip.alpinewhispers.tooltip_information.hold", key).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
            return;
        }
        list.add(Component.translatable("tooltip.alpinewhispers.frosty_block.info_0").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
        list.add(Component.empty());
        list.add(Component.translatable("tooltip.alpinewhispers.frosty_block.info_1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }
}