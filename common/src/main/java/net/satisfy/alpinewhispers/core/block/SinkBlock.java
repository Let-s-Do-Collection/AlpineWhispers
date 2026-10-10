package net.satisfy.alpinewhispers.core.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.alpinewhispers.core.block.entity.SinkBlockEntity;
import net.satisfy.alpinewhispers.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "deprecation"})
public class SinkBlock extends Block implements EntityBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = EnumProperty.create("half", DoubleBlockHalf.class);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final Map<Direction, VoxelShape> TOP_SHAPES = new HashMap<>();
    public static final Map<Direction, VoxelShape> BOTTOM_SHAPES = new HashMap<>();
    public final boolean hasLegs;
    public static final Map<Direction, VoxelShape> BOTTOM_SHAPES_WITH_LEGS = new HashMap<>();

    static {
        Supplier<VoxelShape> topShapeSupplier = SinkBlock::makeTopShape;
        Supplier<VoxelShape> bottomShapeSupplier = SinkBlock::makeBottomShape;
        Supplier<VoxelShape> bottomWithLegsShapeSupplier = SinkBlock::makeBottomWithLegsShape;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            TOP_SHAPES.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, topShapeSupplier.get()));
            BOTTOM_SHAPES.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, bottomShapeSupplier.get()));
            BOTTOM_SHAPES_WITH_LEGS.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, bottomWithLegsShapeSupplier.get()));
        }
    }

    public SinkBlock(Properties settings, boolean hasLegs) {
        super(settings);
        this.hasLegs = hasLegs;
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH));
    }

    private static VoxelShape makeTopShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.875, 0.5625, 0.1875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.0625, 0.6875, 0.5625, 0.1875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.8125, 0.375, 0.0625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.8125, 0.75, 0.0625, 0.9375), BooleanOp.OR);
        return shape;
    }

    private static VoxelShape makeBottomShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.125, 1, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
        return shape;
    }

    private static VoxelShape makeBottomWithLegsShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.125, 1, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.125, 0.1875, 0.125, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.8125, 0.1875, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.8125, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.125, 1, 0.125, 0.3125), BooleanOp.OR);
        return shape;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return TOP_SHAPES.get(facing);
        } else {
            return hasLegs ? BOTTOM_SHAPES_WITH_LEGS.get(facing) : BOTTOM_SHAPES.get(facing);
        }
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (itemStack.isEmpty()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        SinkBlockEntity sink = sinkAt(world, pos, state);
        if (sink == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        int water = sink.getWaterLevel();
        ItemStack result;
        int change;
        if (itemStack.is(Items.WATER_BUCKET) && water < SinkBlockEntity.MAX_LEVEL) {
            result = new ItemStack(Items.BUCKET);
            change = SinkBlockEntity.MAX_LEVEL - water;
        } else if (itemStack.is(Items.BUCKET) && water == SinkBlockEntity.MAX_LEVEL) {
            result = new ItemStack(Items.WATER_BUCKET);
            change = -water;
        } else if (itemStack.is(Items.GLASS_BOTTLE) && water > 0) {
            result = PotionContents.createItemStack(Items.POTION, Potions.WATER);
            change = -1;
        } else if (isWaterBottle(itemStack) && water < SinkBlockEntity.MAX_LEVEL) {
            result = new ItemStack(Items.GLASS_BOTTLE);
            change = 1;
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!world.isClientSide) {
            sink.setWaterLevel(water + change);
            world.playSound(null, pos, change > 0 ? SoundEvents.BUCKET_EMPTY : SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.8F, 1.0F);
            if (change > 0 && world instanceof ServerLevel server) {
                BlockPos base = sink.getBlockPos();
                for (int i = 0; i < 12; i++) {
                    double x = base.getX() + 0.3 + world.random.nextDouble() * 0.4;
                    double y = base.getY() + 0.9;
                    double z = base.getZ() + 0.3 + world.random.nextDouble() * 0.4;
                    server.sendParticles(ParticleTypes.SPLASH, x, y, z, 1, 0, 0, 0, 0.01);
                }
            }
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
                if (!player.getInventory().add(result)) {
                    player.drop(result, false);
                }
            }
        }
        return ItemInteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        SinkBlockEntity sink = sinkAt(world, pos, state);
        if (sink == null) {
            return InteractionResult.PASS;
        }
        if (!world.isClientSide) {
            sink.toggle();
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    private static boolean isWaterBottle(ItemStack stack) {
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        return stack.is(Items.POTION) && contents != null && contents.is(Potions.WATER);
    }

    public static @Nullable SinkBlockEntity sinkAt(Level world, BlockPos pos, BlockState state) {
        BlockPos base = state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
        return world.getBlockEntity(base) instanceof SinkBlockEntity sink ? sink : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? new SinkBlockEntity(pos, state) : null;
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (state.getValue(HALF) != DoubleBlockHalf.LOWER) {
            return null;
        }
        if (world.isClientSide) {
            return (level, pos, blockState, blockEntity) -> {
                if (blockEntity instanceof SinkBlockEntity sink) {
                    SinkBlockEntity.clientTick(sink);
                }
            };
        }
        return (level, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof SinkBlockEntity sink) {
                SinkBlockEntity.serverTick(level, pos, blockState, sink);
            }
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        return context.getLevel().getBlockState(blockPos.above()).canBeReplaced(context) ? this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, context.getHorizontalDirection().getOpposite()) : null;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockPos blockPos = pos.below();
            BlockState blockState = world.getBlockState(blockPos);
            return blockState.is(this) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER;
        } else {
            return super.canSurvive(state, world, pos);
        }
    }

    public static void placeAt(LevelAccessor levelAccessor, BlockState blockState, BlockPos blockPos, int i) {
        BlockPos blockPos2 = blockPos.above();
        levelAccessor.setBlock(blockPos, copyWaterloggedFrom(levelAccessor, blockPos, blockState.setValue(HALF, DoubleBlockHalf.LOWER)), i);
        levelAccessor.setBlock(blockPos2, copyWaterloggedFrom(levelAccessor, blockPos2, blockState.setValue(HALF, DoubleBlockHalf.UPPER)), i);
    }


    private static BlockState copyWaterloggedFrom(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) ? blockState.setValue(BlockStateProperties.WATERLOGGED, levelReader.isWaterAt(blockPos)) : blockState;
    }

    protected static void preventCreativeDropFromBottomPart(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            BlockPos blockPos2 = blockPos.below();
            BlockState blockState2 = level.getBlockState(blockPos2);
            if (blockState2.is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockState3 = blockState2.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(blockPos2, blockState3, 35);
                level.levelEvent(player, 2001, blockPos2, Block.getId(blockState2));
            }
        }

    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf half = state.getValue(HALF);
        BlockPos blockPos = half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this && blockState.getValue(HALF) != half) {
            world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
            world.levelEvent(player, 2001, blockPos, Block.getId(blockState));
            if (!world.isClientSide && !player.isCreative()) {
                dropResources(state, world, pos, null, player, player.getMainHandItem());
                dropResources(blockState, world, blockPos, null, player, player.getMainHandItem());
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, Blocks.AIR.defaultBlockState(), blockEntity, itemStack);
        if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos blockPos2 = blockPos.above();
            BlockState blockState2 = level.getBlockState(blockPos2);

            if (blockState2.is(this) && blockState2.getValue(HALF) == DoubleBlockHalf.UPPER) {
                level.destroyBlock(blockPos2, true);
            }
        } else {
            BlockPos blockPos1 = blockPos.below();
            BlockState blockState1 = level.getBlockState(blockPos1);

            if (blockState1.is(this) && blockState1.getValue(HALF) == DoubleBlockHalf.LOWER) {
                level.destroyBlock(blockPos1, true);
            }
        }
    }

    @Override
    public long getSeed(BlockState blockState, BlockPos blockPos) {
        return Mth.getSeed(blockPos.getX(), blockPos.below(blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), blockPos.getZ());
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource randomSource) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER || !(world.getBlockEntity(pos.below()) instanceof SinkBlockEntity sink)) {
            return;
        }
        Vec3 spout = spout(pos, state.getValue(FACING));
        if (sink.isOpen()) {
            world.addParticle(ParticleTypes.FALLING_WATER, spout.x, spout.y, spout.z, 0.0, 0.0, 0.0);
            if (randomSource.nextInt(3) == 0) {
                double surface = pos.getY() - 1 + SinkBlockEntity.surfaceHeight(Math.max(1, sink.getWaterLevel()));
                world.addParticle(ParticleTypes.SPLASH, spout.x, surface, spout.z, 0.0, 0.0, 0.0);
            }
            if (randomSource.nextInt(20) == 0) {
                world.playLocalSound(spout.x, spout.y, spout.z, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.3F, 1.4F, false);
            }
        } else if (randomSource.nextFloat() < 0.05F) {
            world.addParticle(ParticleTypes.DRIPPING_WATER, spout.x, spout.y, spout.z, 0.0, 0.0, 0.0);
        }
    }

    /** Spout tip of the faucet in the upper half, rotated like the blockstate models. */
    public static Vec3 spout(BlockPos upper, Direction facing) {
        double x = 8.0 / 16.0, z = 11.5 / 16.0;
        double rx = switch (facing) {
            case EAST -> 1.0 - z;
            case SOUTH -> 1.0 - x;
            case WEST -> z;
            default -> x;
        };
        double rz = switch (facing) {
            case EAST -> x;
            case SOUTH -> 1.0 - z;
            case WEST -> 1.0 - x;
            default -> z;
        };
        return new Vec3(upper.getX() + rx, upper.getY() + 0.02, upper.getZ() + rz);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        int beige = 0xF5DEB3;
        int gold = 0xFFD700;
        if (!Screen.hasShiftDown()) {
            Component key = Component.literal("[SHIFT]").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(gold)));
            list.add(Component.translatable("tooltip.alpinewhispers.tooltip_information.hold", key).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
            return;
        }
        list.add(Component.translatable("tooltip.alpinewhispers.sink.info_0").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
        list.add(Component.empty());
        list.add(Component.translatable("tooltip.alpinewhispers.sink.info_1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }
}