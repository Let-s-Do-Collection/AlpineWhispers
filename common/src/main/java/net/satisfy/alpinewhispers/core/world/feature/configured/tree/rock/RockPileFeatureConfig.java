package net.satisfy.alpinewhispers.core.world.feature.configured.tree.rock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record RockPileFeatureConfig(int spreadX, int spreadZ, int minCount, int maxCount, List<RockSpec> rocks) implements FeatureConfiguration {
    public static final Codec<RockPileFeatureConfig> CODEC = RecordCodecBuilder.create(i ->
            i.group(
                    Codec.INT.fieldOf("spread_x").forGetter(RockPileFeatureConfig::spreadX),
                    Codec.INT.fieldOf("spread_z").forGetter(RockPileFeatureConfig::spreadZ),
                    Codec.INT.fieldOf("min_count").forGetter(RockPileFeatureConfig::minCount),
                    Codec.INT.fieldOf("max_count").forGetter(RockPileFeatureConfig::maxCount),
                    RockSpec.CODEC.listOf().fieldOf("rocks").forGetter(RockPileFeatureConfig::rocks)
            ).apply(i, RockPileFeatureConfig::new)
    );

    public record RockSpec(
            BlockStateProvider stateProvider,
            int minSizeX, int maxSizeX,
            int minSizeY, int maxSizeY,
            int minSizeZ, int maxSizeZ,
            int minBury, int maxBury,
            float minRoughness, float maxRoughness
    ) {
        public static final Codec<RockSpec> CODEC = RecordCodecBuilder.create(i ->
                i.group(
                        BlockStateProvider.CODEC.fieldOf("state_provider").forGetter(RockSpec::stateProvider),
                        Codec.INT.fieldOf("min_size_x").forGetter(RockSpec::minSizeX),
                        Codec.INT.fieldOf("max_size_x").forGetter(RockSpec::maxSizeX),
                        Codec.INT.fieldOf("min_size_y").forGetter(RockSpec::minSizeY),
                        Codec.INT.fieldOf("max_size_y").forGetter(RockSpec::maxSizeY),
                        Codec.INT.fieldOf("min_size_z").forGetter(RockSpec::minSizeZ),
                        Codec.INT.fieldOf("max_size_z").forGetter(RockSpec::maxSizeZ),
                        Codec.INT.fieldOf("min_bury").forGetter(RockSpec::minBury),
                        Codec.INT.fieldOf("max_bury").forGetter(RockSpec::maxBury),
                        Codec.FLOAT.fieldOf("min_roughness").forGetter(RockSpec::minRoughness),
                        Codec.FLOAT.fieldOf("max_roughness").forGetter(RockSpec::maxRoughness)
                ).apply(i, RockSpec::new)
        );

        private int pick(RandomSource r, int a, int b) {
            if (a >= b) return a;
            return a + r.nextInt(b - a + 1);
        }

        public int pickSizeX(RandomSource r) { return pick(r, minSizeX, maxSizeX); }
        public int pickSizeY(RandomSource r) { return pick(r, minSizeY, maxSizeY); }
        public int pickSizeZ(RandomSource r) { return pick(r, minSizeZ, maxSizeZ); }
        public int pickBury(RandomSource r) { return pick(r, minBury, maxBury); }
        public float pickRoughness(RandomSource r) {
            if (minRoughness >= maxRoughness) return minRoughness;
            return minRoughness + r.nextFloat() * (maxRoughness - minRoughness);
        }

        public BlockState state(RandomSource r, BlockPos pos) {
            return stateProvider.getState(r, pos);
        }
    }
}