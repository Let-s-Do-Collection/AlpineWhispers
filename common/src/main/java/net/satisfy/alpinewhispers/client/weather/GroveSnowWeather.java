package net.satisfy.alpinewhispers.client.weather;

import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.satisfy.alpinewhispers.core.config.AlpineWhispersConfig;
import net.satisfy.alpinewhispers.core.registry.SoundEventRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroveSnowWeather {
    private static float snowfallLevel = 0.0F;
    private static float previousSnowfallLevel = 0.0F;
    private static float avalancheIntensity = 0.0F;
    private static int avalancheCooldown = 200;
    private static final List<Avalanche> ACTIVE_AVALANCHES = new ArrayList<>();
    private static int groveAmbientTicks = 0;

    public static void init() {
        ClientTickEvent.CLIENT_POST.register(GroveSnowWeather::tickClient);
        ClientCommandRegistrationEvent.EVENT.register((dispatcher, context) -> dispatcher.register(
                ClientCommandRegistrationEvent.literal("avalanche").executes(ctx -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    if (minecraft.level == null || minecraft.player == null) {
                        return 0;
                    }
                    String result = tryStartAvalanche(minecraft.level, minecraft.player, minecraft.level.random);
                    ctx.getSource().arch$sendSuccess(() -> Component.literal("[Avalanche] " + result), false);
                    return 1;
                })));
    }

    private static void tickClient(Minecraft minecraft) {
        if (minecraft.isPaused()) {
            return;
        }

        ClientLevel level = minecraft.level;
        LocalPlayer player = minecraft.player;
        if (level == null || player == null) {
            avalancheIntensity = 0.0F;
            ACTIVE_AVALANCHES.clear();
            groveAmbientTicks = 0;
            snowfallLevel = 0.0F;
            previousSnowfallLevel = 0.0F;
            return;
        }

        BlockPos playerPos = player.blockPosition();
        boolean isGrove = level.getBiome(playerPos).is(Biomes.GROVE);
        boolean isWeatherActive = level.isRaining() || level.isThundering();
        updateSnowfall(isGrove && !isWeatherActive && AlpineWhispersConfig.groveSnowEnabled);

        if (!isGrove || isWeatherActive) {
            avalancheIntensity = Mth.lerp(0.05F, avalancheIntensity, 0.0F);
            ACTIVE_AVALANCHES.clear();
            groveAmbientTicks = 0;
            return;
        }

        RandomSource random = level.random;

        avalancheIntensity = Mth.clamp(avalancheIntensity + 0.002F, 0.0F, 1.0F);


        if (AlpineWhispersConfig.avalanchesEnabled) {
            tickAvalanches(level, random);
            if (--avalancheCooldown <= 0 && avalancheIntensity > 0.35F && ACTIVE_AVALANCHES.size() < 2) {
                tryStartAvalanche(level, player, random);
                avalancheCooldown = 600 + random.nextInt(1800);
            }
        } else {
            ACTIVE_AVALANCHES.clear();
        }

        groveAmbientTicks++;

        if (groveAmbientTicks >= 600 && AlpineWhispersConfig.groveAmbienceEnabled) {
            playGroveAmbientSound(minecraft, random);
            groveAmbientTicks = 0;
        }

    }

    private static void playGroveAmbientSound(Minecraft minecraft, RandomSource random) {
        float baseVolume = 0.005F;
        float volume = baseVolume * (0.6F + random.nextFloat() * 0.4F);
        float pitch = 0.9F + random.nextFloat() * 0.1F;
        minecraft.getSoundManager().play(SimpleSoundInstance.forLocalAmbience(SoundEventRegistry.GROVE_AMBIENT.get(), volume, pitch));
    }

    public static float getSnowfallLevel(float partialTick) {
        return Mth.lerp(partialTick, previousSnowfallLevel, snowfallLevel);
    }

    private static void updateSnowfall(boolean active) {
        previousSnowfallLevel = snowfallLevel;
        float target = active ? AlpineWhispersConfig.groveSnowStrength / 100.0F : 0.0F;
        snowfallLevel = Mth.approach(snowfallLevel, target, 0.005F);
    }

    private static String tryStartAvalanche(ClientLevel level, LocalPlayer player, RandomSource random) {
        int noSnow = 0;
        int noSlope = 0;
        for (int attempt = 0; attempt < 32; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2.0;
            double distance = 8.0 + random.nextDouble() * 24.0;
            BlockPos samplePos = BlockPos.containing(player.getX() + Math.cos(angle) * distance, player.getY(), player.getZ() + Math.sin(angle) * distance);
            BlockPos surfacePos = groundAt(level, samplePos);

            if (!isSnowSurface(level, surfacePos)) {
                noSnow++;
                continue;
            }

            Vec3 downhill = findDownhill(level, surfacePos);
            if (downhill == null) {
                noSlope++;
                continue;
            }

            Vec3 start = new Vec3(surfacePos.getX() + 0.5, surfacePos.getY(), surfacePos.getZ() + 0.5);
            ACTIVE_AVALANCHES.add(new Avalanche(start, downhill, 60 + random.nextInt(60), 1.5F + random.nextFloat()));

            if (AlpineWhispersConfig.groveAmbienceEnabled) {
                float pitch = 0.8F + random.nextFloat() * 0.15F;
                Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(SoundEventRegistry.FALLING_SNOW.get(), SoundSource.AMBIENT, 1.0F, pitch, random, start.x, start.y, start.z));
            }
            return "started at " + surfacePos.toShortString();
        }
        return "no spot found (no snow: " + noSnow + ", not steep: " + noSlope + ")";
    }

    private static void tickAvalanches(ClientLevel level, RandomSource random) {
        Iterator<Avalanche> iterator = ACTIVE_AVALANCHES.iterator();
        while (iterator.hasNext()) {
            Avalanche avalanche = iterator.next();
            avalanche.age++;
            avalanche.speed = Math.min(avalanche.speed + 0.01, 0.45);

            Vec3 next = avalanche.position.add(avalanche.direction.scale(avalanche.speed));
            BlockPos surfacePos = groundAt(level, BlockPos.containing(next));

            if (surfacePos.getY() > avalanche.position.y + 1.0 || avalanche.age > avalanche.maxAge) {
                iterator.remove();
                continue;
            }

            avalanche.position = new Vec3(next.x, surfacePos.getY(), next.z);

            Vec3 downhill = findDownhill(level, surfacePos);
            if (downhill != null) {
                avalanche.direction = avalanche.direction.lerp(downhill, 0.2).normalize();
            } else if (++avalanche.flatTicks > 10) {
                iterator.remove();
                continue;
            }

            spawnAvalancheFront(level, avalanche, random);
        }
    }

    private static void spawnAvalancheFront(ClientLevel level, Avalanche avalanche, RandomSource random) {
        Vec3 side = new Vec3(-avalanche.direction.z, 0.0, avalanche.direction.x);
        double fade = 1.0 - Math.max(0.0, (avalanche.age - avalanche.maxAge * 0.7) / (avalanche.maxAge * 0.3));
        int count = (int) (14 * fade) + 4;
        BlockParticleOption snowBlock = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState());

        for (int i = 0; i < count; i++) {
            double spread = (random.nextDouble() - 0.5) * 2.0 * avalanche.width;
            double x = avalanche.position.x + side.x * spread;
            double y = avalanche.position.y + 0.1 + random.nextDouble() * 0.6;
            double z = avalanche.position.z + side.z * spread;
            double vx = avalanche.direction.x * avalanche.speed * 0.8;
            double vz = avalanche.direction.z * avalanche.speed * 0.8;

            level.addAlwaysVisibleParticle(random.nextBoolean() ? snowBlock : ParticleTypes.SNOWFLAKE, x, y, z, vx, 0.02 + random.nextDouble() * 0.05, vz);
            if (random.nextFloat() < 0.15F) {
                level.addAlwaysVisibleParticle(ParticleTypes.CLOUD, x, y + 0.3, z, vx * 0.3, 0.01, vz * 0.3);
            }
        }
    }

    private static BlockPos groundAt(ClientLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).mutable();
        for (int i = 0; i < 48 && ground.getY() > level.getMinBuildHeight(); i++) {
            BlockState below = level.getBlockState(ground.below());
            boolean passable = below.isAir() || below.is(BlockTags.LEAVES) || below.is(BlockTags.LOGS) || (below.canBeReplaced() && !below.is(Blocks.SNOW))
                    || (below.is(Blocks.SNOW) && level.getBlockState(ground.below(2)).is(BlockTags.LEAVES));
            if (!passable) {
                break;
            }
            ground.move(0, -1, 0);
        }
        return ground.immutable();
    }

    private static boolean isSnowSurface(ClientLevel level, BlockPos surfacePos) {
        BlockState at = level.getBlockState(surfacePos);
        BlockState below = level.getBlockState(surfacePos.below());
        return at.is(Blocks.SNOW) || below.is(Blocks.SNOW) || below.is(Blocks.SNOW_BLOCK) || below.is(Blocks.POWDER_SNOW);
    }

    private static Vec3 findDownhill(ClientLevel level, BlockPos surfacePos) {
        int bestDrop = 1;
        Vec3 best = null;
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4.0;
            int dx = (int) Math.round(Math.cos(angle) * 3.0);
            int dz = (int) Math.round(Math.sin(angle) * 3.0);
            BlockPos neighbor = groundAt(level, surfacePos.offset(dx, 0, dz));
            int drop = surfacePos.getY() - neighbor.getY();
            if (drop > bestDrop) {
                bestDrop = drop;
                best = new Vec3(dx, 0.0, dz).normalize();
            }
        }
        return best;
    }

    private static class Avalanche {
        Vec3 position;
        Vec3 direction;
        final int maxAge;
        final float width;
        int age;
        int flatTicks;
        double speed = 0.15;

        Avalanche(Vec3 position, Vec3 direction, int maxAge, float width) {
            this.position = position;
            this.direction = direction;
            this.maxAge = maxAge;
            this.width = width;
        }
    }
}
