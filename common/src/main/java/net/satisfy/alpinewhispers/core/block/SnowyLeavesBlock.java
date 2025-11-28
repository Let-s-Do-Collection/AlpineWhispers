package net.satisfy.alpinewhispers.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SnowyLeavesBlock extends LeavesBlock {
    public static final MapCodec<SnowyLeavesBlock> CODEC = simpleCodec(SnowyLeavesBlock::new);

    public SnowyLeavesBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull MapCodec<? extends LeavesBlock> codec() {
        return CODEC;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        super.animateTick(blockState, level, blockPos, randomSource);
        if (!level.isClientSide) return;
        if (!level.getBlockState(blockPos.below()).isAir()) return;

        long timeOfDay = level.getDayTime() % 24000L;
        int spawnChance;
        int maxParticleCount;

        if (timeOfDay < 4000L) {
            spawnChance = 40;
            maxParticleCount = 2;
        } else if (timeOfDay < 8000L) {
            spawnChance = 28;
            maxParticleCount = 3;
        } else if (timeOfDay < 12000L) {
            spawnChance = 22;
            maxParticleCount = 3;
        } else {
            spawnChance = 55;
            maxParticleCount = 2;
        }

        if (randomSource.nextInt(spawnChance) != 0) return;

        int particleCount = 1 + randomSource.nextInt(maxParticleCount);
        for (int index = 0; index < particleCount; index++) {
            double particleX = blockPos.getX() + 0.2 + randomSource.nextDouble() * 0.6;
            double particleY = blockPos.getY() + 0.9;
            double particleZ = blockPos.getZ() + 0.2 + randomSource.nextDouble() * 0.6;

            double velocityX = (randomSource.nextDouble() - 0.5) * 0.02;
            double velocityY = -0.04 - randomSource.nextDouble() * 0.02;
            double velocityZ = (randomSource.nextDouble() - 0.5) * 0.02;

            level.addParticle(ParticleTypes.SNOWFLAKE, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
        }
    }
}