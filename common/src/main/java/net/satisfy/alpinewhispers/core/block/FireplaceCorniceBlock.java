package net.satisfy.alpinewhispers.core.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import net.satisfy.foundation.block.mimic.MimicBlock;
import net.satisfy.foundation.block.mimic.MimicBlockEntity;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;
import net.minecraft.world.level.storage.loot.LootParams;

public class FireplaceCorniceBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT = BooleanProperty.create("right");
    public static final BooleanProperty APPLIED = MimicBlock.APPLIED;

    public FireplaceCorniceBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(TOP, true)
                .setValue(BOTTOM, true)
                .setValue(LEFT, true)
                .setValue(RIGHT, true)
                .setValue(APPLIED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, TOP, BOTTOM, LEFT, RIGHT, APPLIED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        boolean water = context.getLevel().getFluidState(pos).getType() == Fluids.WATER;
        BlockState state = defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, water)
                .setValue(TOP, true)
                .setValue(BOTTOM, true)
                .setValue(LEFT, true)
                .setValue(RIGHT, true)
                .setValue(APPLIED, false);
        return updateConnections(state, context.getLevel(), pos);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return updateConnections(state, level, pos);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MimicBlockEntity(EntityTypeRegistry.FIRE_PLACE_CORNICE_BLOCK_ENTITY.get(), pos, state);
    }

    private static boolean isConnected(Direction facing, BlockState neighbor) {
        if (!(neighbor.getBlock() instanceof FireplaceCorniceBlock)) return false;
        return neighbor.getValue(FACING) == facing;
    }

    private static BlockState updateConnections(BlockState state, BlockGetter level, BlockPos pos) {
        Direction facing = state.getValue(FACING);

        boolean top = true;
        boolean bottom = true;
        boolean left = true;
        boolean right = true;

        BlockState upState = level.getBlockState(pos.above());
        BlockState downState = level.getBlockState(pos.below());

        if (isConnected(facing, upState)) {
            top = false;
        }
        if (isConnected(facing, downState)) {
            bottom = false;
        }

        if (facing == Direction.SOUTH) {
            BlockState westState = level.getBlockState(pos.west());
            BlockState eastState = level.getBlockState(pos.east());
            if (isConnected(facing, westState)) {
                left = false;
            }
            if (isConnected(facing, eastState)) {
                right = false;
            }
        } else if (facing == Direction.NORTH) {
            BlockState eastState = level.getBlockState(pos.east());
            BlockState westState = level.getBlockState(pos.west());
            if (isConnected(facing, eastState)) {
                left = false;
            }
            if (isConnected(facing, westState)) {
                right = false;
            }
        } else if (facing == Direction.EAST) {
            BlockState southState = level.getBlockState(pos.south());
            BlockState northState = level.getBlockState(pos.north());
            if (isConnected(facing, southState)) {
                left = false;
            }
            if (isConnected(facing, northState)) {
                right = false;
            }
        } else if (facing == Direction.WEST) {
            BlockState northState = level.getBlockState(pos.north());
            BlockState southState = level.getBlockState(pos.south());
            if (isConnected(facing, northState)) {
                left = false;
            }
            if (isConnected(facing, southState)) {
                right = false;
            }
        }

        return state
                .setValue(TOP, top)
                .setValue(BOTTOM, bottom)
                .setValue(LEFT, left)
                .setValue(RIGHT, right);
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return MimicBlock.use(stack, state, level, pos, player, mimic -> true);
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
        list.add(Component.translatable("tooltip.alpinewhispers.fireplace_cornice.info_0").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
        list.add(Component.empty());
        list.add(Component.translatable("tooltip.alpinewhispers.fireplace_cornice.info_1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }

    @Override
    protected @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return MimicBlock.addDrops(super.getDrops(state, builder), builder);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        boolean top = state.getValue(TOP);
        boolean bottom = state.getValue(BOTTOM);
        boolean left = state.getValue(LEFT);
        boolean right = state.getValue(RIGHT);
        VoxelShape shape = Shapes.empty();

        if (facing == Direction.SOUTH) {
            if (left) shape = Shapes.or(shape, box(0, 0, 0, 4, 16, 4));
            if (right) shape = Shapes.or(shape, box(12, 0, 0, 16, 16, 4));
            if (top) shape = Shapes.or(shape, box(0, 11, 0, 16, 16, 5));
            if (bottom) shape = Shapes.or(shape, box(0, 0, 1, 16, 10, 2));
        } else if (facing == Direction.NORTH) {
            if (left) shape = Shapes.or(shape, box(12, 0, 12, 16, 16, 16));
            if (right) shape = Shapes.or(shape, box(0, 0, 12, 4, 16, 16));
            if (top) shape = Shapes.or(shape, box(0, 11, 11, 16, 16, 16));
            if (bottom) shape = Shapes.or(shape, box(0, 0, 14, 16, 10, 15));
        } else if (facing == Direction.EAST) {
            if (left) shape = Shapes.or(shape, box(0, 0, 12, 4, 16, 16));
            if (right) shape = Shapes.or(shape, box(0, 0, 0, 4, 16, 4));
            if (top) shape = Shapes.or(shape, box(0, 11, 0, 5, 16, 16));
            if (bottom) shape = Shapes.or(shape, box(1, 0, 0, 2, 10, 16));
        } else if (facing == Direction.WEST) {
            if (left) shape = Shapes.or(shape, box(12, 0, 0, 16, 16, 4));
            if (right) shape = Shapes.or(shape, box(12, 0, 12, 16, 16, 16));
            if (top) shape = Shapes.or(shape, box(11, 11, 0, 16, 16, 16));
            if (bottom) shape = Shapes.or(shape, box(14, 0, 0, 15, 10, 16));
        }

        return shape;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }
}