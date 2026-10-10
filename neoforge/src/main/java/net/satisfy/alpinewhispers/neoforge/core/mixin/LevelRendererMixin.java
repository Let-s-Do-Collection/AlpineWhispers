package net.satisfy.alpinewhispers.neoforge.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.biome.Biome;
import net.satisfy.alpinewhispers.client.weather.GroveSnowWeather;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Unique
    private boolean alpinewhispers$groveSnowfall;

    @ModifyExpressionValue(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getRainLevel(F)F"))
    private float alpinewhispers$addGroveSnowfall(float rainLevel, @Local(argsOnly = true) float partialTick) {
        float snowfall = GroveSnowWeather.getSnowfallLevel(partialTick);
        alpinewhispers$groveSnowfall = rainLevel <= 0.0F && snowfall > 0.0F;
        return alpinewhispers$groveSnowfall ? snowfall : rainLevel;
    }

    @ModifyExpressionValue(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    private Biome.Precipitation alpinewhispers$snowOnly(Biome.Precipitation precipitation) {
        return alpinewhispers$groveSnowfall && precipitation != Biome.Precipitation.SNOW ? Biome.Precipitation.NONE : precipitation;
    }
}
