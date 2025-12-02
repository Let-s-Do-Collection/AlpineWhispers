package net.satisfy.alpinewhispers.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.alpinewhispers.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GarlandBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<GarlandType> TYPE = EnumProperty.create("type", GarlandType.class);
    public static final Map<Direction, VoxelShape> SHAPES = new HashMap<>();

    static {
        VoxelShape baseShape = makeShape();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            SHAPES.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, baseShape));
        }
    }

    public GarlandBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, GarlandType.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return SHAPES.getOrDefault(facing, SHAPES.get(Direction.NORTH));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);

        BlockPos wallPos = pos.relative(facing.getOpposite());
        BlockState wallState = level.getBlockState(wallPos);
        if (canAttachTo(wallState, level, wallPos, facing)) {
            return true;
        }

        BlockPos ceilingPos = pos.above();
        BlockState ceilingState = level.getBlockState(ceilingPos);
        return canAttachTo(ceilingState, level, ceilingPos, Direction.DOWN);
    }

    private static boolean canAttachTo(BlockState blockState, LevelReader levelReader, BlockPos blockPos, Direction face) {
        return !blockState.isAir();
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction[] directions = context.getNearestLookingDirections();

        for (Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                BlockState candidate = this.defaultBlockState()
                        .setValue(FACING, direction.getOpposite())
                        .setValue(TYPE, GarlandType.NONE);
                if (candidate.canSurvive(level, pos)) {
                    return candidate;
                }
            }
        }

        return null;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        Direction facing = state.getValue(FACING);
        if (direction.getOpposite() == facing && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        GarlandType newType = resolveType(state, level, pos);
        if (newType != state.getValue(TYPE)) {
            return state.setValue(TYPE, newType);
        }
        return state;
    }

    private GarlandType resolveType(BlockState state, LevelAccessor level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        Direction leftDirection = facing.getCounterClockWise();
        Direction rightDirection = facing.getClockWise();

        BlockState leftState = level.getBlockState(pos.relative(leftDirection));
        BlockState rightState = level.getBlockState(pos.relative(rightDirection));

        boolean hasLeft = leftState.is(this);
        boolean hasRight = rightState.is(this);

        if (hasLeft && hasRight
                && leftState.getValue(FACING) == facing
                && rightState.getValue(FACING) == facing) {
            return GarlandType.STRAIGHT;
        }

        if (hasLeft) {
            Direction leftFacing = leftState.getValue(FACING);
            if (leftFacing == leftDirection) {
                return GarlandType.OUTER_LEFT;
            }
            if (leftFacing == leftDirection.getOpposite()) {
                return GarlandType.INNER_LEFT;
            }
            if (leftFacing == facing) {
                return GarlandType.STRAIGHT;
            }
        }

        if (hasRight) {
            Direction rightFacing = rightState.getValue(FACING);
            if (rightFacing == rightDirection) {
                return GarlandType.OUTER_RIGHT;
            }
            if (rightFacing == rightDirection.getOpposite()) {
                return GarlandType.INNER_RIGHT;
            }
            if (rightFacing == facing) {
                return GarlandType.STRAIGHT;
            }
        }

        return GarlandType.NONE;
    }

    private static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.6875, 0.75, 1, 0.9375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.6875, 0.6875, 0.25, 0.8125, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.8125, 0.6875, 0.5625, 0.9375, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.75, 0.6875, 0.875, 0.875, 0.75), BooleanOp.OR);
        return shape;
    }

    public enum GarlandType implements StringRepresentable {
        NONE("none"),
        STRAIGHT("straight"),
        INNER_LEFT("inner_left"),
        INNER_RIGHT("inner_right"),
        OUTER_LEFT("outer_left"),
        OUTER_RIGHT("outer_right");

        private final String name;

        GarlandType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        int beige = 0xF5DEB3;
        list.add(Component.translatable("tooltip.alpinewhispers.canbeplacedonwalls").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }
}
