package net.satisfy.alpinewhispers.core.world.feature.configured.tree.rock;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;

public class RockPileFeature extends Feature<RockPileFeatureConfig> {
    public RockPileFeature() {
        super(RockPileFeatureConfig.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<RockPileFeatureConfig> context) {
        var level = context.level();
        var origin = context.origin();
        var random = context.random();
        var config = context.config();

        int span = Math.max(0, config.maxCount() - config.minCount());
        int pileCount = config.minCount() + (span == 0 ? 0 : random.nextInt(span + 1));

        boolean placedAny = false;

        for (int n = 0; n < pileCount; n++) {
            if (config.rocks().isEmpty()) break;
            var specification = config.rocks().get(random.nextInt(config.rocks().size()));

            int sizeX = Math.max(1, specification.pickSizeX(random));
            int sizeY = Math.max(1, specification.pickSizeY(random));
            int sizeZ = Math.max(1, specification.pickSizeZ(random));
            int bury = Math.max(0, Math.min(specification.pickBury(random), sizeY));
            float roughness = Math.max(0f, specification.pickRoughness(random));

            int offsetX = random.nextInt(config.spreadX() * 2 + 1) - config.spreadX();
            int offsetZ = random.nextInt(config.spreadZ() * 2 + 1) - config.spreadZ();

            int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, origin.getX() + offsetX, origin.getZ() + offsetZ) - 1;
            var basePosition = new BlockPos(origin.getX() + offsetX, surfaceY, origin.getZ() + offsetZ);

            var baseState = level.getBlockState(basePosition);
            if (!baseState.getFluidState().isEmpty()) continue;
            if (!baseState.isCollisionShapeFullBlock(level, basePosition)) continue;
            if (baseState.is(BlockTags.LEAVES)) continue;
            if (baseState.is(BlockTags.LOGS)) continue;
            if (baseState.is(BlockTags.FENCES)) continue;

            var belowPosition = basePosition.below();
            var belowState = level.getBlockState(belowPosition);
            if (belowState.isAir()) continue;
            if (!belowState.getFluidState().isEmpty()) continue;

            if (placeOne(context, basePosition, sizeX, sizeY, sizeZ, bury, roughness, specification)) {
                placedAny = true;
            }
        }

        return placedAny;
    }

    private boolean placeOne(FeaturePlaceContext<RockPileFeatureConfig> ctx, BlockPos base, int sizeX, int sizeY, int sizeZ, int bury, float roughness, RockPileFeatureConfig.RockSpec spec) {
        var level = ctx.level();
        var random = ctx.random();

        int rx = Math.max(1, sizeX / 2);
        int ry = Math.max(1, sizeY / 2);
        int rz = Math.max(1, sizeZ / 2);
        int topLayer = sizeY - bury - 1;

        var topCorner = new ArrayList<BlockPos>();
        boolean placed = false;

        for (int dy = -bury; dy < sizeY - bury; dy++) {
            for (int dx = -rx; dx <= rx; dx++) {
                for (int dz = -rz; dz <= rz; dz++) {
                    double nx = (double) dx / rx;
                    double ny = (double) dy / ry;
                    double nz = (double) dz / rz;
                    double shape = nx * nx + ny * ny + nz * nz;
                    double jitter = random.nextDouble() * roughness;
                    if (shape + jitter > 1.0) continue;

                    var pos = base.offset(dx, dy, dz);
                    var stateAt = level.getBlockState(pos);

                    boolean belowSurface = dy < 0;
                    boolean canReplaceBelow = belowSurface && stateAt.getFluidState().isEmpty();
                    boolean canReplaceAbove = !belowSurface && (stateAt.isAir() || stateAt.is(BlockTags.REPLACEABLE) || stateAt.is(BlockTags.SNOW));
                    if (!(canReplaceBelow || canReplaceAbove)) continue;

                    level.setBlock(pos, spec.state(random, pos), 2);
                    placed = true;

                    boolean nearTop = dy >= topLayer - 1;
                    boolean nearCorner = Math.abs(dx) >= rx - 1 && Math.abs(dz) >= rz - 1;
                    if (nearTop && nearCorner) topCorner.add(pos.immutable());
                }
            }
        }

        if (!topCorner.isEmpty()) {
            int carveCount = Math.min(topCorner.size(), 1 + random.nextInt(3));
            for (int i = 0; i < carveCount; i++) {
                int idx = random.nextInt(topCorner.size());
                var pos = topCorner.remove(idx);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }

        return placed;
    }
}