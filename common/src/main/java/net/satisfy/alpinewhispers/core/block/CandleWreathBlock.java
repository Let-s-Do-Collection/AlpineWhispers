package net.satisfy.alpinewhispers.core.block;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.alpinewhispers.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class CandleWreathBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty LIT_FR = BooleanProperty.create("lit_fr");
    public static final BooleanProperty LIT_BR = BooleanProperty.create("lit_br");
    public static final BooleanProperty LIT_BL = BooleanProperty.create("lit_bl");
    public static final BooleanProperty LIT_FL = BooleanProperty.create("lit_fl");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final List<Vector3f> FIRE_POSITIONS = List.of(
            new Vector3f(11, 9, 3),
            new Vector3f(11, 10, 11),
            new Vector3f(3, 11, 11),
            new Vector3f(3, 8, 3)
    );

    private static final Map<BooleanProperty, Vector3f> PARTICLE_POSITIONS = Map.of(
            LIT_FR, FIRE_POSITIONS.get(0),
            LIT_BR, FIRE_POSITIONS.get(1),
            LIT_BL, FIRE_POSITIONS.get(2),
            LIT_FL, FIRE_POSITIONS.get(3)
    );

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.1875, 0.3125, 0.4375, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.25, 0.6875, 0.8125, 0.5625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.1875, 0.1875, 0.8125, 0.5, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.6875, 0.3125, 0.625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.375, 0.25, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.125, 0.875, 0.25, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.125, 0.625, 0.25, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.625, 0.625, 0.25, 0.875), BooleanOp.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public static final ToIntFunction<BlockState> LIGHT_EMISSION = state -> {
        int litCount = 0;
        if (state.getValue(LIT_FR)) litCount++;
        if (state.getValue(LIT_BR)) litCount++;
        if (state.getValue(LIT_BL)) litCount++;
        if (state.getValue(LIT_FL)) litCount++;
        return switch (litCount) {
            case 1 -> 4;
            case 2 -> 8;
            case 3 -> 12;
            case 4 -> 15;
            default -> 0;
        };
    };

    public CandleWreathBlock(Properties properties) {
        super(properties.lightLevel(LIGHT_EMISSION));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT_FR, false)
                .setValue(LIT_BR, false)
                .setValue(LIT_BL, false)
                .setValue(LIT_FL, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT_FR, LIT_BR, LIT_BL, LIT_FL, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean waterlogged = fluidState.getType() == Fluids.WATER;
        Direction direction = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState()
                .setValue(FACING, direction)
                .setValue(LIT_FR, false)
                .setValue(LIT_BR, false)
                .setValue(LIT_BL, false)
                .setValue(LIT_FL, false)
                .setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            extinguish(null, state, level, pos);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportCenter(level, pos.below(), Direction.UP);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Direction facing = state.getValue(FACING);
        Vec3 hitLocation = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        RelativeQuadrant quadrant = getRelativeQuadrant(hitLocation, facing);
        LitInfo info = getLitInfo(quadrant);

        boolean isLighter = stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE);
        boolean isBucket = stack.is(Items.BUCKET);

        if (!isLighter && !isBucket) {
            if (state.getValue(info.property) && level.isClientSide()) {
                Vec3 particlePos = rotatePosition(info.particlePosition, facing).add(pos.getX(), pos.getY(), pos.getZ());
                addParticlesAndSound(level, particlePos, level.random);
                level.playLocalSound(particlePos.x, particlePos.y, particlePos.z, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F, false);
                return ItemInteractionResult.sidedSuccess(true);
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (level.isClientSide()) {
            if (isLighter && !state.getValue(info.property) && !state.getValue(WATERLOGGED)) {
                Vec3 particlePos = rotatePosition(info.particlePosition, facing).add(pos.getX(), pos.getY(), pos.getZ());
                addParticlesAndSound(level, particlePos, level.random);
                level.playLocalSound(particlePos.x, particlePos.y, particlePos.z, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F, false);
                return ItemInteractionResult.sidedSuccess(true);
            }
            if (isBucket && state.getValue(info.property)) {
                Vec3 particlePos = rotatePosition(info.particlePosition, facing).add(pos.getX(), pos.getY(), pos.getZ());
                level.playLocalSound(particlePos.x, particlePos.y, particlePos.z, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                return ItemInteractionResult.sidedSuccess(true);
            }
            return ItemInteractionResult.sidedSuccess(true);
        }

        BlockState newState = state;
        boolean updated = false;

        if (isLighter && !state.getValue(info.property) && !state.getValue(WATERLOGGED)) {
            newState = newState.setValue(info.property, true);
            updated = true;
        } else if (isBucket && state.getValue(info.property)) {
            newState = newState.setValue(info.property, false);
            updated = true;
        }

        if (updated) {
            level.setBlock(pos, newState, 3);
            Vec3 particlePos = rotatePosition(info.particlePosition, facing).add(pos.getX(), pos.getY(), pos.getZ());
            addParticlesAndSound(level, particlePos, level.random);
            if (isLighter) {
                level.playSound(null, particlePos.x, particlePos.y, particlePos.z, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);
            } else {
                level.playSound(null, particlePos.x, particlePos.y, particlePos.z, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }

        return ItemInteractionResult.sidedSuccess(false);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction facing = state.getValue(FACING);
        for (Map.Entry<BooleanProperty, Vector3f> entry : PARTICLE_POSITIONS.entrySet()) {
            BooleanProperty prop = entry.getKey();
            Vector3f firePos = entry.getValue();
            if (state.getValue(prop)) {
                Vec3 rotatedPos = rotatePosition(firePos, facing).add(pos.getX(), pos.getY(), pos.getZ());
                addParticlesAndSound(level, rotatedPos, random);
            }
        }
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide && projectile.isOnFire()) {
            Direction facing = state.getValue(FACING);
            Vec3 hitLocation = hit.getLocation().subtract(hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ());
            RelativeQuadrant quadrant = getRelativeQuadrant(hitLocation, facing);
            LitInfo info = getLitInfo(quadrant);
            if (info != null && !state.getValue(info.property) && !state.getValue(WATERLOGGED)) {
                level.setBlock(hit.getBlockPos(), state.setValue(info.property, true), 11);
                Vec3 particlePos = rotatePosition(info.particlePosition, facing).add(hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ());
                addParticlesAndSound(level, particlePos, level.random);
                level.playSound(null, particlePos.x, particlePos.y, particlePos.z, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, hit.getBlockPos());
            }
        }
    }

    private static void addParticlesAndSound(Level level, Vec3 vec3, RandomSource randomSource) {
        float chance = randomSource.nextFloat();
        if (chance < 0.3F) {
            level.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
            if (chance < 0.17F) {
                level.playLocalSound(vec3.x, vec3.y, vec3.z, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + randomSource.nextFloat(), randomSource.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        level.addParticle(ParticleTypes.SMALL_FLAME, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
    }

    private static Vec3 rotatePosition(Vector3f position, Direction facing) {
        double x = (position.x() + 1) / 16.0;
        double y = (position.y() + 1) / 16.0;
        double z = (position.z() + 1) / 16.0;
        return switch (facing) {
            case EAST -> new Vec3(1.0 - z, y, x);
            case SOUTH -> new Vec3(1.0 - x, y, 1.0 - z);
            case WEST -> new Vec3(z, y, 1.0 - x);
            default -> new Vec3(x, y, z);
        };
    }

    private enum RelativeQuadrant {
        FRONT_RIGHT,
        FRONT_LEFT,
        BACK_RIGHT,
        BACK_LEFT
    }

    private record LitInfo(BooleanProperty property, Vector3f particlePosition) {
    }

    private RelativeQuadrant getRelativeQuadrant(Vec3 hitLocation, Direction facing) {
        boolean front;
        boolean right = switch (facing) {
            case EAST -> {
                front = hitLocation.x() > 0.5;
                yield hitLocation.z() > 0.5;
            }
            case SOUTH -> {
                front = hitLocation.z() > 0.5;
                yield hitLocation.x() < 0.5;
            }
            case WEST -> {
                front = hitLocation.x() < 0.5;
                yield hitLocation.z() < 0.5;
            }
            default -> {
                front = hitLocation.z() < 0.5;
                yield hitLocation.x() > 0.5;
            }
        };
        if (front && right) {
            return RelativeQuadrant.FRONT_RIGHT;
        } else if (front) {
            return RelativeQuadrant.FRONT_LEFT;
        } else if (right) {
            return RelativeQuadrant.BACK_RIGHT;
        } else {
            return RelativeQuadrant.BACK_LEFT;
        }
    }

    private LitInfo getLitInfo(RelativeQuadrant quadrant) {
        return switch (quadrant) {
            case FRONT_RIGHT -> new LitInfo(LIT_FR, FIRE_POSITIONS.get(0));
            case FRONT_LEFT -> new LitInfo(LIT_FL, FIRE_POSITIONS.get(3));
            case BACK_RIGHT -> new LitInfo(LIT_BR, FIRE_POSITIONS.get(1));
            case BACK_LEFT -> new LitInfo(LIT_BL, FIRE_POSITIONS.get(2));
        };
    }

    public static void extinguish(@Nullable Player player, BlockState state, LevelAccessor level, BlockPos pos) {
        BlockState newState = state.setValue(LIT_FR, false)
                .setValue(LIT_BR, false)
                .setValue(LIT_BL, false)
                .setValue(LIT_FL, false);
        level.setBlock(pos, newState, 11);
        Direction facing = state.getValue(FACING);
        for (Vector3f firePos : FIRE_POSITIONS) {
            Vec3 particlePos = rotatePosition(firePos, facing).add(pos.getX(), pos.getY(), pos.getZ());
            level.addParticle(ParticleTypes.SMOKE, particlePos.x, particlePos.y, particlePos.z, 0.0, 0.1, 0.0);
        }
        level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        int beige = 0xF5DEB3;
        list.add(Component.translatable("tooltip.alpinewhispers.canbeplaced").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }
}