package net.satisfy.alpinewhispers.core.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class FrostyWallBlock extends Block implements SimpleWaterloggedBlock, FrostyBlockInterface {
    public static final MapCodec<FrostyWallBlock> CODEC = simpleCodec(FrostyWallBlock::new);
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final EnumProperty<WallSide> EAST_WALL = BlockStateProperties.EAST_WALL;
    public static final EnumProperty<WallSide> NORTH_WALL = BlockStateProperties.NORTH_WALL;
    public static final EnumProperty<WallSide> SOUTH_WALL = BlockStateProperties.SOUTH_WALL;
    public static final EnumProperty<WallSide> WEST_WALL = BlockStateProperties.WEST_WALL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final Map<BlockState, VoxelShape> shapeByIndex;
    private final Map<BlockState, VoxelShape> collisionShapeByIndex;
    private static final VoxelShape POST_TEST = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape NORTH_TEST = Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
    private static final VoxelShape SOUTH_TEST = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
    private static final VoxelShape WEST_TEST = Block.box(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape EAST_TEST = Block.box(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

    @Override
    public @NotNull MapCodec<FrostyWallBlock> codec() {
        return CODEC;
    }

    public FrostyWallBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(UP, true)
                        .setValue(NORTH_WALL, WallSide.NONE)
                        .setValue(EAST_WALL, WallSide.NONE)
                        .setValue(SOUTH_WALL, WallSide.NONE)
                        .setValue(WEST_WALL, WallSide.NONE)
                        .setValue(WATERLOGGED, false)
                        .setValue(FrostyBlockInterface.FROSTY, false)
        );
        this.shapeByIndex = this.makeShapes(16.0F, 14.0F, 16.0F);
        this.collisionShapeByIndex = this.makeShapes(24.0F, 24.0F, 24.0F);
    }

    private static VoxelShape applyWallShape(VoxelShape voxelShape, WallSide wallSide, VoxelShape low, VoxelShape tall) {
        if (wallSide == WallSide.TALL) {
            return Shapes.or(voxelShape, tall);
        } else {
            return wallSide == WallSide.LOW ? Shapes.or(voxelShape, low) : voxelShape;
        }
    }

    private Map<BlockState, VoxelShape> makeShapes(float h, float j, float k) {
        float l = 8.0F - (float) 4.0;
        float m = 8.0F + (float) 4.0;
        float n = 8.0F - (float) 3.0;
        float o = 8.0F + (float) 3.0;
        VoxelShape post = Block.box(l, 0.0, l, m, h, m);
        VoxelShape northLow = Block.box(n, (float) 0.0, 0.0, o, j, o);
        VoxelShape southLow = Block.box(n, (float) 0.0, n, o, j, 16.0);
        VoxelShape westLow = Block.box(0.0, (float) 0.0, n, o, j, o);
        VoxelShape eastLow = Block.box(n, (float) 0.0, n, 16.0, j, o);
        VoxelShape northTall = Block.box(n, (float) 0.0, 0.0, o, k, o);
        VoxelShape southTall = Block.box(n, (float) 0.0, n, o, k, 16.0);
        VoxelShape westTall = Block.box(0.0, (float) 0.0, n, o, k, o);
        VoxelShape eastTall = Block.box(n, (float) 0.0, n, 16.0, k, o);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (Boolean frosty : FrostyBlockInterface.FROSTY.getPossibleValues()) {
            for (Boolean up : UP.getPossibleValues()) {
                for (WallSide east : EAST_WALL.getPossibleValues()) {
                    for (WallSide north : NORTH_WALL.getPossibleValues()) {
                        for (WallSide west : WEST_WALL.getPossibleValues()) {
                            for (WallSide south : SOUTH_WALL.getPossibleValues()) {
                                VoxelShape shape = Shapes.empty();
                                shape = applyWallShape(shape, east, eastLow, eastTall);
                                shape = applyWallShape(shape, west, westLow, westTall);
                                shape = applyWallShape(shape, north, northLow, northTall);
                                shape = applyWallShape(shape, south, southLow, southTall);
                                if (up) {
                                    shape = Shapes.or(shape, post);
                                }

                                BlockState baseState = this.defaultBlockState()
                                        .setValue(FrostyBlockInterface.FROSTY, frosty)
                                        .setValue(UP, up)
                                        .setValue(EAST_WALL, east)
                                        .setValue(WEST_WALL, west)
                                        .setValue(NORTH_WALL, north)
                                        .setValue(SOUTH_WALL, south);

                                builder.put(baseState.setValue(WATERLOGGED, false), shape);
                                builder.put(baseState.setValue(WATERLOGGED, true), shape);
                            }
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        VoxelShape voxelShape = this.shapeByIndex.get(blockState);
        return voxelShape == null ? Shapes.empty() : voxelShape;
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        VoxelShape voxelShape = this.collisionShapeByIndex.get(blockState);
        return voxelShape == null ? Shapes.empty() : voxelShape;
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    private boolean connectsTo(BlockState blockState, boolean sturdy, Direction direction) {
        Block block = blockState.getBlock();
        boolean gate = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(blockState, direction);
        return blockState.is(BlockTags.WALLS) || (!Block.isExceptionForConnection(blockState) && sturdy) || block instanceof IronBarsBlock || gate;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        LevelReader levelReader = blockPlaceContext.getLevel();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        BlockPos northPos = blockPos.north();
        BlockPos eastPos = blockPos.east();
        BlockPos southPos = blockPos.south();
        BlockPos westPos = blockPos.west();
        BlockPos abovePos = blockPos.above();

        BlockState northState = levelReader.getBlockState(northPos);
        BlockState eastState = levelReader.getBlockState(eastPos);
        BlockState southState = levelReader.getBlockState(southPos);
        BlockState westState = levelReader.getBlockState(westPos);
        BlockState aboveState = levelReader.getBlockState(abovePos);

        boolean northConnect = this.connectsTo(northState, northState.isFaceSturdy(levelReader, northPos, Direction.SOUTH), Direction.SOUTH);
        boolean eastConnect = this.connectsTo(eastState, eastState.isFaceSturdy(levelReader, eastPos, Direction.WEST), Direction.WEST);
        boolean southConnect = this.connectsTo(southState, southState.isFaceSturdy(levelReader, southPos, Direction.NORTH), Direction.NORTH);
        boolean westConnect = this.connectsTo(westState, westState.isFaceSturdy(levelReader, westPos, Direction.EAST), Direction.EAST);

        BlockState base = this.defaultBlockState()
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(FrostyBlockInterface.FROSTY, false);

        return this.updateShape(levelReader, base, abovePos, aboveState, northConnect, eastConnect, southConnect, westConnect);
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        if (direction == Direction.DOWN) {
            return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
        } else if (direction == Direction.UP) {
            return this.topUpdate(levelAccessor, blockState, blockPos2, blockState2);
        } else {
            return this.sideUpdate(levelAccessor, blockPos, blockState, blockPos2, blockState2, direction);
        }
    }

    private static boolean isConnected(BlockState blockState, Property<WallSide> property) {
        return blockState.getValue(property) != WallSide.NONE;
    }

    private static boolean isCovered(VoxelShape shape, VoxelShape test) {
        return !Shapes.joinIsNotEmpty(test, shape, BooleanOp.ONLY_FIRST);
    }

    private BlockState topUpdate(LevelReader levelReader, BlockState blockState, BlockPos blockPos, BlockState blockState2) {
        boolean north = isConnected(blockState, NORTH_WALL);
        boolean east = isConnected(blockState, EAST_WALL);
        boolean south = isConnected(blockState, SOUTH_WALL);
        boolean west = isConnected(blockState, WEST_WALL);
        return this.updateShape(levelReader, blockState, blockPos, blockState2, north, east, south, west);
    }

    private BlockState sideUpdate(LevelReader levelReader, BlockPos blockPos, BlockState blockState, BlockPos blockPos2, BlockState blockState2, Direction direction) {
        Direction opposite = direction.getOpposite();
        boolean north = direction == Direction.NORTH ? this.connectsTo(blockState2, blockState2.isFaceSturdy(levelReader, blockPos2, opposite), opposite) : isConnected(blockState, NORTH_WALL);
        boolean east = direction == Direction.EAST ? this.connectsTo(blockState2, blockState2.isFaceSturdy(levelReader, blockPos2, opposite), opposite) : isConnected(blockState, EAST_WALL);
        boolean south = direction == Direction.SOUTH ? this.connectsTo(blockState2, blockState2.isFaceSturdy(levelReader, blockPos2, opposite), opposite) : isConnected(blockState, SOUTH_WALL);
        boolean west = direction == Direction.WEST ? this.connectsTo(blockState2, blockState2.isFaceSturdy(levelReader, blockPos2, opposite), opposite) : isConnected(blockState, WEST_WALL);
        BlockPos abovePos = blockPos.above();
        BlockState aboveState = levelReader.getBlockState(abovePos);
        return this.updateShape(levelReader, blockState, abovePos, aboveState, north, east, south, west);
    }

    private BlockState updateShape(LevelReader levelReader, BlockState blockState, BlockPos blockPos, BlockState blockState2, boolean north, boolean east, boolean south, boolean west) {
        VoxelShape topShape = blockState2.getCollisionShape(levelReader, blockPos).getFaceShape(Direction.DOWN);
        BlockState updated = this.updateSides(blockState, north, east, south, west, topShape);
        return updated.setValue(UP, this.shouldRaisePost(updated, blockState2, topShape));
    }

    private boolean shouldRaisePost(BlockState blockState, BlockState aboveState, VoxelShape topShape) {
        boolean aboveWall = aboveState.getBlock() instanceof FrostyWallBlock && aboveState.getValue(UP);
        if (aboveWall) {
            return true;
        }

        WallSide north = blockState.getValue(NORTH_WALL);
        WallSide south = blockState.getValue(SOUTH_WALL);
        WallSide east = blockState.getValue(EAST_WALL);
        WallSide west = blockState.getValue(WEST_WALL);

        boolean southNone = south == WallSide.NONE;
        boolean westNone = west == WallSide.NONE;
        boolean eastNone = east == WallSide.NONE;
        boolean northNone = north == WallSide.NONE;

        boolean mixed = northNone && southNone && westNone && eastNone || northNone != southNone || westNone != eastNone;
        if (mixed) {
            return true;
        }

        boolean tallPair = north == WallSide.TALL && south == WallSide.TALL || east == WallSide.TALL && west == WallSide.TALL;
        if (tallPair) {
            return false;
        }

        return aboveState.is(BlockTags.WALL_POST_OVERRIDE) || isCovered(topShape, POST_TEST);
    }

    private BlockState updateSides(BlockState blockState, boolean north, boolean east, boolean south, boolean west, VoxelShape topShape) {
        return blockState
                .setValue(NORTH_WALL, this.makeWallState(north, topShape, NORTH_TEST))
                .setValue(EAST_WALL, this.makeWallState(east, topShape, EAST_TEST))
                .setValue(SOUTH_WALL, this.makeWallState(south, topShape, SOUTH_TEST))
                .setValue(WEST_WALL, this.makeWallState(west, topShape, WEST_TEST));
    }

    private WallSide makeWallState(boolean connected, VoxelShape topShape, VoxelShape testShape) {
        if (!connected) {
            return WallSide.NONE;
        }
        return isCovered(topShape, testShape) ? WallSide.TALL : WallSide.LOW;
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return !blockState.getValue(WATERLOGGED);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH_WALL, EAST_WALL, WEST_WALL, SOUTH_WALL, WATERLOGGED, FrostyBlockInterface.FROSTY);
    }

    @Override
    protected @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 -> blockState
                    .setValue(NORTH_WALL, blockState.getValue(SOUTH_WALL))
                    .setValue(EAST_WALL, blockState.getValue(WEST_WALL))
                    .setValue(SOUTH_WALL, blockState.getValue(NORTH_WALL))
                    .setValue(WEST_WALL, blockState.getValue(EAST_WALL));
            case COUNTERCLOCKWISE_90 -> blockState
                    .setValue(NORTH_WALL, blockState.getValue(EAST_WALL))
                    .setValue(EAST_WALL, blockState.getValue(SOUTH_WALL))
                    .setValue(SOUTH_WALL, blockState.getValue(WEST_WALL))
                    .setValue(WEST_WALL, blockState.getValue(NORTH_WALL));
            case CLOCKWISE_90 -> blockState
                    .setValue(NORTH_WALL, blockState.getValue(WEST_WALL))
                    .setValue(EAST_WALL, blockState.getValue(NORTH_WALL))
                    .setValue(SOUTH_WALL, blockState.getValue(EAST_WALL))
                    .setValue(WEST_WALL, blockState.getValue(SOUTH_WALL));
            default -> blockState;
        };
    }

    @Override
    protected @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> blockState
                    .setValue(NORTH_WALL, blockState.getValue(SOUTH_WALL))
                    .setValue(SOUTH_WALL, blockState.getValue(NORTH_WALL));
            case FRONT_BACK -> blockState
                    .setValue(EAST_WALL, blockState.getValue(WEST_WALL))
                    .setValue(WEST_WALL, blockState.getValue(EAST_WALL));
            default -> super.mirror(blockState, mirror);
        };
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