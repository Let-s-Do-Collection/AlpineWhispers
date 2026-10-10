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

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

public class GarlandBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<GarlandType> TYPE = EnumProperty.create("type", GarlandType.class);
    private static final Map<BlockState, VoxelShape> SHAPE_CACHE = new ConcurrentHashMap<>();

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
        return SHAPE_CACHE.computeIfAbsent(state, GarlandBlock::computeShape);
    }

    // Mirrors the y-rotations in blockstates/garland.json so shapes always match the rendered model.
    private static VoxelShape computeShape(BlockState state) {
        GarlandType type = state.getValue(TYPE);
        VoxelShape base;
        int baseSteps;
        switch (type) {
            case INNER_LEFT -> { base = innerShape(); baseSteps = 3; }
            case INNER_RIGHT -> { base = innerShape(); baseSteps = 2; }
            case OUTER_LEFT -> { base = outerShape(); baseSteps = 3; }
            case OUTER_RIGHT -> { base = outerShape(); baseSteps = 2; }
            default -> { base = makeShape(); baseSteps = 0; }
        }
        int facingSteps = (state.getValue(FACING).get2DDataValue() + 2) % 4;
        Direction target = Direction.from2DDataValue((2 + baseSteps + facingSteps) % 4);
        return GeneralUtil.rotateShape(Direction.NORTH, target, base);
    }

    private static VoxelShape innerShape() {
        return Shapes.or(
                Block.box(0, 10, 0, 16, 16, 6),
                Block.box(0, 10, 0, 6, 16, 16)
        );
    }

    private static VoxelShape outerShape() {
        return Block.box(0, 10, 0, 6, 16, 6);
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
                    return candidate.setValue(TYPE, resolveType(candidate, level, pos));
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

    private GarlandType resolveType(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        Direction left = facing.getCounterClockWise();
        Direction right = facing.getClockWise();

        BlockState backState = level.getBlockState(pos.relative(facing.getOpposite()));
        if (backState.is(this)) {
            Direction backFacing = backState.getValue(FACING);
            if (backFacing == right) return GarlandType.OUTER_LEFT;
            if (backFacing == left) return GarlandType.OUTER_RIGHT;
        }

        BlockState frontState = level.getBlockState(pos.relative(facing));
        if (frontState.is(this)) {
            Direction frontFacing = frontState.getValue(FACING);
            if (frontFacing == right) return GarlandType.INNER_LEFT;
            if (frontFacing == left) return GarlandType.INNER_RIGHT;
        }

        boolean hasLeft = level.getBlockState(pos.relative(left)).is(this);
        boolean hasRight = level.getBlockState(pos.relative(right)).is(this);
        if (hasLeft && hasRight) return GarlandType.STRAIGHT;
        if (hasLeft) return GarlandType.LEFT;
        if (hasRight) return GarlandType.RIGHT;
        return GarlandType.NONE;
    }

    private static VoxelShape makeShape() {
        return Block.box(0, 10, 11, 16, 16, 16);
    }

    public enum GarlandType implements StringRepresentable {
        NONE("none"),
        STRAIGHT("straight"),
        LEFT("left"),
        RIGHT("right"),
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
