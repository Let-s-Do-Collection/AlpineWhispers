package net.satisfy.alpinewhispers.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

public class SnowyDoublePlantBlock extends DoublePlantBlock {
    public static final MapCodec<SnowyDoublePlantBlock> CODEC = simpleCodec(SnowyDoublePlantBlock::new);

    public SnowyDoublePlantBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull MapCodec<? extends DoublePlantBlock> codec() {
        return CODEC;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (!level.isClientSide) return;
        if (blockState.getValue(HALF) != DoubleBlockHalf.UPPER) return;
        if (!level.getBlockState(blockPos.below(2)).isAir()) return;

        long t = level.getDayTime() % 24000L;
        int chance = 0;
        if (t < 4000L) chance = 200;
        else if (t < 8000L) chance = 120;
        else if (t < 12000L) chance = 80;

        if (chance > 0 && randomSource.nextInt(chance) == 0) {
            double x = blockPos.getX() + randomSource.nextDouble();
            double y = blockPos.getY() + 0.9;
            double z = blockPos.getZ() + randomSource.nextDouble();
            level.addParticle(ParticleTypes.SNOWFLAKE, x, y, z, 0.0, -0.01, 0.0);
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, net.minecraft.world.level.BlockGetter world, BlockPos pos) {
        return state.is(Blocks.SNOW) || state.is(Blocks.SNOW_BLOCK) || super.mayPlaceOn(state, world, pos);
    }
}