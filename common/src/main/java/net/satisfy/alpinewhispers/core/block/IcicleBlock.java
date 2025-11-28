package net.satisfy.alpinewhispers.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

public class IcicleBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);

    public IcicleBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return true;
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(12) != 0) {
            return;
        }

        BlockPos frozenPos = blockPos.above();
        BlockState frozenState = level.getBlockState(frozenPos);
        Block frozenDirtBlock = ObjectRegistry.FROZEN_DIRT.get();
        if (frozenDirtBlock == null || !frozenState.is(frozenDirtBlock)) {
            return;
        }

        BlockPos airPos = blockPos.below();
        BlockState airState = level.getBlockState(airPos);
        if (!airState.isAir()) {
            return;
        }

        BlockPos stonePos = airPos.below();
        BlockState stoneState = level.getBlockState(stonePos);
        if (!stoneState.is(Blocks.STONE)) {
            return;
        }

        Block alpineGneissBlock = ObjectRegistry.ALPINE_GNEISS.get();
        if (alpineGneissBlock == null) {
            return;
        }

        level.setBlock(stonePos, alpineGneissBlock.defaultBlockState(), 2);
        level.setBlock(frozenPos, Blocks.DIRT.defaultBlockState(), 2);
        level.destroyBlock(blockPos, false);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        BlockPos blockPosAbove = blockPos.above();
        BlockState aboveState = level.getBlockState(blockPosAbove);

        if (aboveState.isAir()) {
            return false;
        }

        if (aboveState.is(BlockTags.LEAVES)) {
            return true;
        }

        return Block.canSupportCenter(level, blockPosAbove, Direction.DOWN);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!canSurvive(blockState, level, blockPos)) {
            level.destroyBlock(blockPos, true);
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos blockPos, BlockPos neighborPos) {
        if (!canSurvive(blockState, level, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, direction, neighborState, level, blockPos, neighborPos);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(5) != 0) {
            return;
        }

        double particleX = blockPos.getX() + randomSource.nextDouble();
        double particleY = blockPos.getY() + 0.5 + randomSource.nextDouble() * 0.3;
        double particleZ = blockPos.getZ() + randomSource.nextDouble();

        double velocityX = 0.0;
        double velocityY = -0.03;
        double velocityZ = 0.0;

        level.addParticle(ParticleTypes.SNOWFLAKE, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
    }
}