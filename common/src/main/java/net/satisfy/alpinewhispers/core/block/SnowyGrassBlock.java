package net.satisfy.alpinewhispers.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SnowyGrassBlock extends GrassBlock {
    public SnowyGrassBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (!level.isClientSide) return;

        Biome biome = level.getBiome(blockPos).value();
        if (biome.getBaseTemperature() > 0.3F) return;
        if (!level.getBlockState(blockPos.above()).isAir()) return;

        long time = level.getDayTime() % 24000L;
        int chance = 0;

        if (time >= 0L && time < 4000L) {
            chance = 160;
        } else if (time >= 4000L && time < 8000L) {
            chance = 90;
        } else if (time >= 8000L && time < 12000L) {
            chance = 45;
        }

        if (chance > 0 && randomSource.nextInt(chance) == 0) {
            double x = blockPos.getX() + randomSource.nextDouble();
            double y = blockPos.getY() + 1.01;
            double z = blockPos.getZ() + randomSource.nextDouble();
            level.addParticle(ParticleTypes.SNOWFLAKE, x, y, z, 0.0, -0.01, 0.0);
        }
    }
}