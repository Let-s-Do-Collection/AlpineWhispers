package net.satisfy.alpinewhispers.core.block;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.alpinewhispers.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FoodBlock extends Block {
    public static final DirectionProperty FACING;
    public static final IntegerProperty BITES;

    private static final Map<Direction, VoxelShape[]> SHAPES = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            VoxelShape[] shapes = new VoxelShape[4];
            shapes[0] = GeneralUtil.rotateShape(Direction.NORTH, direction, createShape0());
            shapes[1] = GeneralUtil.rotateShape(Direction.NORTH, direction, createShape1());
            shapes[2] = GeneralUtil.rotateShape(Direction.NORTH, direction, createShape2());
            shapes[3] = GeneralUtil.rotateShape(Direction.NORTH, direction, createShape3());
            map.put(direction, shapes);
        }
    });

    private final int maxBites;
    private final FoodProperties foodComponent;

    public FoodBlock(BlockBehaviour.Properties settings, int maxBites, FoodProperties foodComponent) {
        super(settings);
        this.maxBites = maxBites;
        this.foodComponent = foodComponent;
        registerDefaultState(this.defaultBlockState().setValue(BITES, 0).setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        if (!Objects.requireNonNull(ctx.getPlayer()).isShiftKeyDown()) {
            return null;
        }
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.CONSUME;
        }
        return tryEat(world, pos, state, player);
    }

    private InteractionResult tryEat(LevelAccessor world, BlockPos pos, BlockState state, Player player) {
        ItemStack stack = new ItemStack(asItem());
        FoodProperties fp = stack.get(DataComponents.FOOD);
        if (fp == null) fp = this.foodComponent;

        player.getFoodData().eat(fp.nutrition(), fp.saturation());

        if (world instanceof Level level) {
            for (FoodProperties.PossibleEffect effect : fp.effects()) {
                if (effect.probability() >= 1.0F || level.random.nextFloat() < effect.probability()) {
                    player.addEffect(new MobEffectInstance(effect.effect()));
                }
            }
        }

        world.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.5f, world.getRandom().nextFloat() * 0.1f + 0.9f);
        world.gameEvent(player, GameEvent.EAT, pos);

        if (world instanceof Level level) {
            for (int i = 0; i < 10; ++i) {
                double dx = level.random.nextGaussian() * 0.02D;
                double dy = level.random.nextGaussian() * 0.02D;
                double dz = level.random.nextGaussian() * 0.02D;
                level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, dx, dy, dz);
            }
        }

        int bites = state.getValue(BITES);
        if (bites < maxBites - 1) {
            world.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            world.destroyBlock(pos, false);
            world.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BITES);
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        BITES = IntegerProperty.create("bites", 0, 3);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        VoxelShape[] shapes = SHAPES.get(facing);
        int bites = state.getValue(BITES);
        return shapes[bites];
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        super.onRemove(state, world, pos, newState, moved);
    }

    private static VoxelShape createShape0() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.065625, 0.0625, 0.9375, 0.065625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.0625, 0.25, 0.8125, 0.1875, 0.5), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.0625, 0.5, 0.8125, 0.1875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.0625, 0.3125, 0.5, 0.1875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375), BooleanOp.OR);
        return shape;
    }

    private static VoxelShape createShape1() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.065625, 0.0625, 0.9375, 0.065625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.0625, 0.5, 0.8125, 0.1875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.0625, 0.3125, 0.5, 0.1875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375), BooleanOp.OR);
        return shape;
    }

    private static VoxelShape createShape2() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.065625, 0.0625, 0.9375, 0.065625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.0625, 0.5, 0.8125, 0.1875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375), BooleanOp.OR);
        return shape;
    }

    private static VoxelShape createShape3() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.065625, 0.0625, 0.9375, 0.065625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375), BooleanOp.OR);
        return shape;
    }
}
