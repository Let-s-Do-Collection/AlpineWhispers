package net.satisfy.alpinewhispers.neoforge.core.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Unique
    private static float alpinewhispers_cachedFogEnd = -1.0F;
    @Unique
    private static float alpinewhispers_biomeFogFactor = 0.0F;

    @Inject(method = "setupFog", at = @At("TAIL"))
    private static void alpinewhispers_applySnowFog(Camera camera, FogRenderer.FogMode fogMode, float viewDistance, boolean thickFog, float partialTick, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            return;
        }

        BlockPos cameraPos = camera.getBlockPosition();
        Biome biome = level.getBiome(cameraPos).value();

        boolean isColdBiome = biome.coldEnoughToSnow(cameraPos);
        float biomeTransitionSpeed = 0.05F;

        if (isColdBiome) {
            alpinewhispers_biomeFogFactor = Mth.lerp(biomeTransitionSpeed, alpinewhispers_biomeFogFactor, 1.0F);
        } else {
            alpinewhispers_biomeFogFactor = Mth.lerp(biomeTransitionSpeed, alpinewhispers_biomeFogFactor, 0.0F);
            if (alpinewhispers_biomeFogFactor < 0.01F) {
                alpinewhispers_cachedFogEnd = -1.0F;
                alpinewhispers_biomeFogFactor = 0.0F;
                return;
            }
        }

        boolean isGrove = level.getBiome(cameraPos).is(Biomes.GROVE);

        float dayTime = level.getTimeOfDay(partialTick);
        float sunHeight = 1.0F - Math.abs(dayTime - 0.5F) * 2.0F;

        boolean isSnowing = level.isRaining();
        boolean isThundering = level.isThundering();

        final var clamped = alpineWhispers$getClamped(sunHeight, isSnowing, isThundering);

        float start = 0.0F;

        float timeOfDay = level.getDayTime() % 24000L;

        float minEnd;
        float maxEnd;

        if (isThundering) {
            minEnd = 3.0F;
            maxEnd = viewDistance * 0.125F;
        } else if (isSnowing) {
            minEnd = 5.5F;
            maxEnd = viewDistance * 0.45F;
        } else if (timeOfDay >= 13000L && timeOfDay <= 23000L) {
            minEnd = 3.0F;
            maxEnd = viewDistance * 0.125F;
        } else if (timeOfDay >= 6000L) {
            minEnd = 4.5F;
            maxEnd = viewDistance * 0.3F;
        } else {
            minEnd = 6.0F;
            maxEnd = viewDistance * 0.5F;
        }

        float endFactor = 1.0F - clamped;
        float targetEnd = Mth.lerp(endFactor, minEnd, maxEnd);

        if (isGrove) {
            targetEnd *= 0.7F;
        }

        if (alpinewhispers_cachedFogEnd < 0.0F) {
            alpinewhispers_cachedFogEnd = targetEnd;
        } else {
            float transitionSpeed = 0.1F;
            alpinewhispers_cachedFogEnd = Mth.lerp(transitionSpeed, alpinewhispers_cachedFogEnd, targetEnd);
        }

        float blendedEnd = Mth.lerp(alpinewhispers_biomeFogFactor, viewDistance, alpinewhispers_cachedFogEnd);

        if (thickFog) {
            blendedEnd *= 0.7F;
        }

        RenderSystem.setShaderFogStart(start);
        RenderSystem.setShaderFogEnd(blendedEnd);
    }

    @Unique
    private static float alpineWhispers$getClamped(float sunHeight, boolean isSnowing, boolean isThundering) {
        float baseStrength = 0.075F;
        float dawnDuskStrength = 0.25F;
        float nightStrength = 0.40F;
        float weatherStrength = 0.5225F;

        float timeStrength;
        if (sunHeight > 0.65F) {
            timeStrength = baseStrength;
        } else if (sunHeight > 0.25F) {
            timeStrength = dawnDuskStrength;
        } else {
            timeStrength = nightStrength;
        }

        float fogStrength = timeStrength;
        if (isSnowing || isThundering) {
            fogStrength = weatherStrength;
        }

        return Mth.clamp(fogStrength, 0.01F, 0.95F);
    }
}
