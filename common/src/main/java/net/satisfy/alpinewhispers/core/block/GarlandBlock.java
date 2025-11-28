package net.satisfy.alpinewhispers.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GarlandBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<GarlandType> TYPE = EnumProperty.create("type", GarlandType.class);

    private static final VoxelShape NORTH_SHAPE = Block.box(0.0, 4.0, 14.0, 16.0, 12.0, 16.0);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0.0, 4.0, 0.0, 16.0, 12.0, 2.0);
    private static final VoxelShape WEST_SHAPE = Block.box(14.0, 4.0, 0.0, 16.0, 12.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.box(0.0, 4.0, 0.0, 2.0, 12.0, 16.0);

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
        return switch (facing) {
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos supportPos = pos.relative(facing.getOpposite());
        BlockState supportState = level.getBlockState(supportPos);
        return supportState.isFaceSturdy(level, supportPos, facing);
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
        Direction leftDir = facing.getCounterClockWise();
        Direction rightDir = facing.getClockWise();

        boolean hasLeft = isSameGarland(level.getBlockState(pos.relative(leftDir)), facing);
        boolean hasRight = isSameGarland(level.getBlockState(pos.relative(rightDir)), facing);

        if (hasLeft && hasRight) {
            return GarlandType.MIDDLE;
        } else if (hasLeft) {
            return GarlandType.RIGHT;
        } else if (hasRight) {
            return GarlandType.LEFT;
        }
        return GarlandType.NONE;
    }

    private boolean isSameGarland(BlockState neighborState, Direction facing) {
        if (!neighborState.is(this)) {
            return false;
        }
        return neighborState.getValue(FACING) == facing;
    }

    public enum GarlandType implements StringRepresentable {
        NONE("none"),
        LEFT("left"),
        MIDDLE("middle"),
        RIGHT("right");

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
}