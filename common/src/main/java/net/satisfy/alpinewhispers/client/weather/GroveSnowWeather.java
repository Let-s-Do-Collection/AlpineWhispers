package net.satisfy.alpinewhispers.client.weather;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class GroveSnowWeather {
    private static float avalancheIntensity = 0.0F;

    public static void init() {
        ClientTickEvent.CLIENT_POST.register(GroveSnowWeather::tickClient);
    }

    private static void tickClient(Minecraft minecraft) {
        ClientLevel level = minecraft.level;
        LocalPlayer player = minecraft.player;
        if (level == null || player == null) {
            return;
        }

        BlockPos playerPos = player.blockPosition();
        boolean isGrove = level.getBiome(playerPos).is(Biomes.GROVE);

        if (!isGrove) {
            avalancheIntensity = Mth.lerp(0.05F, avalancheIntensity, 0.0F);
            return;
        }

        if (level.isRaining() || level.isThundering()) {
            avalancheIntensity = 0.0F;
            return;
        }

        RandomSource random = level.random;

        avalancheIntensity = Mth.clamp(avalancheIntensity + 0.0015F, 0.0F, 1.0F);

        if (random.nextFloat() <= 0.25F) {
            spawnGentleSnow(level, player, random);
        }

        if (random.nextFloat() < avalancheIntensity * 0.15F) {
            spawnAvalancheParticles(level, player, random);
        }
    }

    private static void spawnGentleSnow(ClientLevel level, LocalPlayer player, RandomSource random) {
        int radius = 6;
        double centerX = player.getX();
        double centerZ = player.getZ();

        for (int i = 0; i < 4; i++) {
            double offsetX = (random.nextDouble() - 0.5) * radius * 2.0;
            double offsetZ = (random.nextDouble() - 0.5) * radius * 2.0;

            BlockPos heightPos = BlockPos.containing(centerX + offsetX, player.getY(), centerZ + offsetZ);
            BlockPos surfacePos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, heightPos);
            if (!level.canSeeSky(surfacePos)) {
                continue;
            }

            double particleX = surfacePos.getX() + 0.5;
            double particleY = surfacePos.getY() + 5.0 + random.nextDouble() * 4.0;
            double particleZ = surfacePos.getZ() + 0.5;
            double velocityX = 0.0;
            double velocityY = -0.02F - random.nextDouble() * 0.02F;
            double velocityZ = 0.0;

            level.addParticle(ParticleTypes.SNOWFLAKE, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
        }
    }

    private static void spawnAvalancheParticles(ClientLevel level, LocalPlayer player, RandomSource random) {
        int radius = 10;
        double centerX = player.getX();
        double centerZ = player.getZ();

        int avalancheClusterCount = 1 + (int) (avalancheIntensity * 3.0F);

        for (int clusterIndex = 0; clusterIndex < avalancheClusterCount; clusterIndex++) {
            double offsetX = (random.nextDouble() - 0.5) * radius * 2.0;
            double offsetZ = (random.nextDouble() - 0.5) * radius * 2.0;

            BlockPos heightPos = BlockPos.containing(centerX + offsetX, player.getY(), centerZ + offsetZ);
            BlockPos surfacePos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, heightPos);
            if (!level.canSeeSky(surfacePos)) {
                continue;
            }

            BlockPos belowPos = surfacePos.below();
            if (!level.getBlockState(belowPos).is(Blocks.SNOW) && !level.getBlockState(belowPos).is(Blocks.SNOW_BLOCK) && !level.getBlockState(belowPos).is(Blocks.POWDER_SNOW)) {
                continue;
            }

            double baseX = surfacePos.getX() + 0.5;
            double baseY = surfacePos.getY() + 0.5;
            double baseZ = surfacePos.getZ() + 0.5;

            double slideDirectionX = (random.nextDouble() - 0.5) * 0.08;
            double slideDirectionZ = (random.nextDouble() - 0.5) * 0.08;

            int particlesInCluster = 6 + random.nextInt(4);

            for (int i = 0; i < particlesInCluster; i++) {
                double offsetAlongSlide = i * 0.1;
                double particleX = baseX + slideDirectionX * offsetAlongSlide;
                double particleY = baseY - i * 0.02;
                double particleZ = baseZ + slideDirectionZ * offsetAlongSlide;

                double velocityX = slideDirectionX * 0.7;
                double velocityY = -0.03 - random.nextDouble() * 0.02;
                double velocityZ = slideDirectionZ * 0.7;

                level.addParticle(ParticleTypes.SNOWFLAKE, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
            }
        }
    }
}
