package net.satisfy.alpinewhispers.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SnowGlobeBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final MapCodec<SnowGlobeBlock> CODEC = simpleCodec(SnowGlobeBlock::new);

    private static final VoxelShape SHAPE = Shapes.or(
            Shapes.box(0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
            Shapes.box(0.0625, 0.125, 0.0625, 0.9375, 0.875, 0.9375)
    );

    public SnowGlobeBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWER, 0));
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(POWER, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWER);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        int currentPower = blockState.getValue(POWER);
        int nextPower = Math.min(15, currentPower + 1);
        if (nextPower != currentPower) {
            level.setBlock(blockPos, blockState.setValue(POWER, nextPower), Block.UPDATE_ALL);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean isSignalSource(BlockState blockState) {
        return blockState.getValue(POWER) > 0;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return blockState.getValue(POWER);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (!level.isClientSide) {
            return;
        }

        int power = blockState.getValue(POWER);
        int chanceDivider = power > 0 ? Math.max(1, 6 - power / 3) : 8;

        if (randomSource.nextInt(chanceDivider) != 0) {
            return;
        }

        int particleCount = 1 + power / 5;
        double baseSpeed = 0.01 + power * 0.004;

        for (int i = 0; i < particleCount; i++) {
            double x = blockPos.getX() + 0.2 + randomSource.nextDouble() * 0.6;
            double y = blockPos.getY() + 0.65 + randomSource.nextDouble() * 0.2;
            double z = blockPos.getZ() + 0.2 + randomSource.nextDouble() * 0.6;

            double motionX = (randomSource.nextDouble() - 0.5) * baseSpeed;
            double motionY = (randomSource.nextDouble() - 0.5) * baseSpeed * 0.6;
            double motionZ = (randomSource.nextDouble() - 0.5) * baseSpeed;

            level.addParticle(ParticleTypes.SNOWFLAKE, x, y, z, motionX, motionY, motionZ);
        }
    }
}