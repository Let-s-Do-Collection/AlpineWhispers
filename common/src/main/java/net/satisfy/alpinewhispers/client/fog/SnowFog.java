package net.satisfy.alpinewhispers.client.fog;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.config.AlpineWhispersConfig;
import net.satisfy.foundation.client.fog.BiomeFog;

public final class SnowFog {
    private static final long DAY_LENGTH = 24000L;
    private static final long MORNING_RISE_START = 20000L;
    private static final long MORNING_FULL_START = 22500L;
    private static final long MORNING_FULL_END = 500L;
    private static final long MORNING_FADE_END = 3500L;
    private static final long NIGHT_START = 12000L;
    private static final long NIGHT_FULL_START = 14000L;
    private static final int MIN_SKY_LIGHT = 4;
    private static final int FULL_SKY_LIGHT = 12;
    private static final int HOLLOW_SAMPLES = 8;
    private static final int HOLLOW_RADIUS = 16;
    private static final int HOLLOW_MAX_HEIGHT_ABOVE_GROUND = 8;
    private static final float HOLLOW_MIN_DEPTH = 2.0F;
    private static final float HOLLOW_FULL_DEPTH = 8.0F;
    private static final float MIN_END = 2.0F;

    private SnowFog() {
    }

    public static void init() {
        BiomeFog.register(AlpineWhispers.identifier("snow"), SnowFog::fog);
    }

    private static BiomeFog.Fog fog(ClientLevel level, BlockPos cameraPos, float renderDistance) {
        if (!AlpineWhispersConfig.fogEnabled || !level.getBiome(cameraPos).value().coldEnoughToSnow(cameraPos)) {
            return null;
        }
        long time = level.getDayTime() % DAY_LENGTH;
        float morning = AlpineWhispersConfig.fogMorning ? morningStrength(time) : 0.0F;
        float night = AlpineWhispersConfig.fogNight ? nightStrength(time) : 0.0F;

        float end = percent(renderDistance, AlpineWhispersConfig.fogHazeEndPercent);
        end = thicken(end, renderDistance, morning, AlpineWhispersConfig.fogMorningEndPercent);
        end = thicken(end, renderDistance, night, AlpineWhispersConfig.fogNightEndPercent);
        if (AlpineWhispersConfig.fogSnowfall) {
            end = thicken(end, renderDistance, level.getRainLevel(1.0F), AlpineWhispersConfig.fogSnowfallEndPercent);
            end = thicken(end, renderDistance, level.getThunderLevel(1.0F), AlpineWhispersConfig.fogBlizzardEndPercent);
        }
        if (AlpineWhispersConfig.fogHollows) {
            end = thicken(end, renderDistance, hollowDepth(level, cameraPos) * Math.max(morning, night), AlpineWhispersConfig.fogHollowEndPercent);
        }
        if (level.getBiome(cameraPos).is(Biomes.GROVE)) {
            end *= 1.0F - AlpineWhispersConfig.fogGroveThickeningPercent / 100.0F;
        }

        end = renderDistance - (renderDistance - end) * AlpineWhispersConfig.fogStrengthPercent / 100.0F;
        // Fade the fog out under roofs and in caves instead of filling interiors.
        end = Math.max(MIN_END, Mth.lerp(skyExposure(level, cameraPos), renderDistance, end));
        if (end >= renderDistance - 1.0F) {
            return null;
        }
        float start = percent(renderDistance, AlpineWhispersConfig.fogStartPercent);
        return new BiomeFog.Fog(Math.min(start, end - 1.0F), end);
    }

    private static float thicken(float end, float renderDistance, float weight, int endPercent) {
        return weight <= 0.0F ? end : Math.min(end, Mth.lerp(weight, renderDistance, percent(renderDistance, endPercent)));
    }

    private static float percent(float renderDistance, int percent) {
        return renderDistance * percent / 100.0F;
    }

    private static float hollowDepth(ClientLevel level, BlockPos camera) {
        int ground = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, camera.getX(), camera.getZ());
        if (camera.getY() - ground > HOLLOW_MAX_HEIGHT_ABOVE_GROUND) {
            return 0.0F;
        }
        int total = 0;
        for (int i = 0; i < HOLLOW_SAMPLES; i++) {
            float angle = i * Mth.TWO_PI / HOLLOW_SAMPLES;
            int x = camera.getX() + Mth.floor(Mth.cos(angle) * HOLLOW_RADIUS);
            int z = camera.getZ() + Mth.floor(Mth.sin(angle) * HOLLOW_RADIUS);
            total += level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        }
        float depth = total / (float) HOLLOW_SAMPLES - ground;
        return smooth((depth - HOLLOW_MIN_DEPTH) / (HOLLOW_FULL_DEPTH - HOLLOW_MIN_DEPTH));
    }

    private static float morningStrength(long time) {
        if (time >= MORNING_FULL_START || time <= MORNING_FULL_END) {
            return 1.0F;
        }
        if (time >= MORNING_RISE_START) {
            return smooth((time - MORNING_RISE_START) / (float) (MORNING_FULL_START - MORNING_RISE_START));
        }
        if (time < MORNING_FADE_END) {
            return smooth(1.0F - (time - MORNING_FULL_END) / (float) (MORNING_FADE_END - MORNING_FULL_END));
        }
        return 0.0F;
    }

    private static float nightStrength(long time) {
        if (time >= NIGHT_FULL_START && time < MORNING_FULL_START) {
            return 1.0F;
        }
        if (time >= NIGHT_START && time < NIGHT_FULL_START) {
            return smooth((time - NIGHT_START) / (float) (NIGHT_FULL_START - NIGHT_START));
        }
        return 0.0F;
    }

    private static float skyExposure(ClientLevel level, BlockPos pos) {
        return smooth((level.getBrightness(LightLayer.SKY, pos) - MIN_SKY_LIGHT) / (float) (FULL_SKY_LIGHT - MIN_SKY_LIGHT));
    }

    private static float smooth(float value) {
        float clamped = Mth.clamp(value, 0.0F, 1.0F);
        return clamped * clamped * (3.0F - 2.0F * clamped);
    }
}
