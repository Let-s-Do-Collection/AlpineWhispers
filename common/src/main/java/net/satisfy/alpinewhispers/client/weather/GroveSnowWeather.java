package net.satisfy.alpinewhispers.client.weather;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.satisfy.alpinewhispers.core.registry.SoundEventRegistry;

public class GroveSnowWeather {
    private static float avalancheIntensity = 0.0F;
    private static int avalancheSoundTicks = 0;
    private static int groveAmbientTicks = 0;

    public static void init() {
        ClientTickEvent.CLIENT_POST.register(GroveSnowWeather::tickClient);
    }

    private static void tickClient(Minecraft minecraft) {
        ClientLevel level = minecraft.level;
        LocalPlayer player = minecraft.player;
        if (level == null || player == null) {
            avalancheIntensity = 0.0F;
            avalancheSoundTicks = 0;
            groveAmbientTicks = 0;
            return;
        }

        BlockPos playerPos = player.blockPosition();
        boolean isGrove = level.getBiome(playerPos).is(Biomes.GROVE);
        boolean isWeatherActive = level.isRaining() || level.isThundering();

        if (!isGrove || isWeatherActive) {
            avalancheIntensity = Mth.lerp(0.05F, avalancheIntensity, 0.0F);
            avalancheSoundTicks = 0;
            groveAmbientTicks = 0;
            return;
        }

        RandomSource random = level.random;

        avalancheIntensity = Mth.clamp(avalancheIntensity + 0.002F, 0.0F, 1.0F);

        if (random.nextFloat() < 0.25F) {
            spawnGentleSnow(level, player, random);
        }

        if (random.nextFloat() < avalancheIntensity * 0.15F) {
            spawnAvalancheParticles(level, player, random);
        }

        groveAmbientTicks++;
        avalancheSoundTicks++;

        if (groveAmbientTicks >= 600) {
            playGroveAmbientSound(minecraft, random);
            groveAmbientTicks = 0;
        }

        if (avalancheSoundTicks >= 1800 && avalancheIntensity > 0.35F) {
            if (random.nextFloat() < 0.6F) {
                playAvalancheSound(minecraft, random);
            }
            avalancheSoundTicks = 0;
        }
    }

    private static void playGroveAmbientSound(Minecraft minecraft, RandomSource random) {
        float baseVolume = 0.005F;
        float volume = baseVolume * (0.6F + random.nextFloat() * 0.4F);
        float pitch = 0.9F + random.nextFloat() * 0.1F;
        minecraft.getSoundManager().play(SimpleSoundInstance.forLocalAmbience(SoundEventRegistry.GROVE_AMBIENT.get(), volume, pitch));
    }

    private static void playAvalancheSound(Minecraft minecraft, RandomSource random) {
        float baseVolume = 0.001F;
        float volume = baseVolume * (0.5F + avalancheIntensity * 0.5F);
        float pitch = 0.85F + random.nextFloat() * 0.1F;
        minecraft.getSoundManager().play(SimpleSoundInstance.forLocalAmbience(SoundEventRegistry.FALLING_SNOW.get(), volume, pitch));
    }

    private static void spawnGentleSnow(ClientLevel level, LocalPlayer player, RandomSource random) {
        int radius = 6;
        double centerX = player.getX();
        double centerZ = player.getZ();

        for (int i = 0; i < 4; i++) {
            double offsetX = (random.nextDouble() - 0.5) * radius * 2.0;
            double offsetZ = (random.nextDouble() - 0.5) * radius * 2.0;

            BlockPos samplePos = BlockPos.containing(centerX + offsetX, player.getY(), centerZ + offsetZ);
            BlockPos surfacePos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, samplePos);
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
        int radius = 12;
        double centerX = player.getX();
        double centerZ = player.getZ();

        int clusterCount = 1 + (int) (avalancheIntensity * 3.0F);

        for (int clusterIndex = 0; clusterIndex < clusterCount; clusterIndex++) {
            double offsetX = (random.nextDouble() - 0.5) * radius * 2.0;
            double offsetZ = (random.nextDouble() - 0.5) * radius * 2.0;

            BlockPos samplePos = BlockPos.containing(centerX + offsetX, player.getY(), centerZ + offsetZ);
            BlockPos surfacePos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, samplePos);

            BlockPos belowPos = surfacePos.below();
            if (!level.getBlockState(belowPos).is(Blocks.SNOW) && !level.getBlockState(belowPos).is(Blocks.SNOW_BLOCK) && !level.getBlockState(belowPos).is(Blocks.POWDER_SNOW)) {
                continue;
            }

            if (surfacePos.getY() < player.getY() + 2.0) {
                continue;
            }

            int baseY = surfacePos.getY();
            int maxDiff = 0;
            int slopeSampleRadius = 3;

            for (int dx = -slopeSampleRadius; dx <= slopeSampleRadius; dx++) {
                for (int dz = -slopeSampleRadius; dz <= slopeSampleRadius; dz++) {
                    if (dx == 0 && dz == 0) {
                        continue;
                    }
                    BlockPos neighborSample = surfacePos.offset(dx, 0, dz);
                    BlockPos neighborSurface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, neighborSample);
                    int diff = Math.abs(neighborSurface.getY() - baseY);
                    if (diff > maxDiff) {
                        maxDiff = diff;
                    }
                }
            }

            if (maxDiff < 2) {
                continue;
            }

            double baseX = surfacePos.getX() + 0.5;
            double particleBaseY = surfacePos.getY() + 0.6;
            double baseZ = surfacePos.getZ() + 0.5;

            double slideAngle = random.nextFloat() * ((float) Math.PI * 2.0F);
            double slideDirectionX = Mth.cos((float) slideAngle) * 0.08;
            double slideDirectionZ = Mth.sin((float) slideAngle) * 0.08;

            int particlesInCluster = 8 + random.nextInt(6);

            for (int i = 0; i < particlesInCluster; i++) {
                double step = i * 0.12;
                double particleX = baseX + slideDirectionX * step;
                double particleY = particleBaseY - i * 0.03;
                double particleZ = baseZ + slideDirectionZ * step;

                double velocityX = slideDirectionX * 0.7;
                double velocityY = -0.04 - random.nextDouble() * 0.03;
                double velocityZ = slideDirectionZ * 0.7;

                level.addParticle(ParticleTypes.SNOWFLAKE, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
            }
        }
    }
}