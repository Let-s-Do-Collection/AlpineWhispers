package net.satisfy.alpinewhispers.neoforge.core.world;

import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.satisfy.alpinewhispers.AlpineWhispers;

public final class AlpineWhispersBiomeModifiers {
    private static final DeferredRegister<MapCodec<? extends BiomeModifier>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, AlpineWhispers.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ConfigurableSpawnsBiomeModifier>> CONFIGURABLE_SPAWNS = SERIALIZERS.register("configurable_spawns", () -> ConfigurableSpawnsBiomeModifier.CODEC);

    private AlpineWhispersBiomeModifiers() {
    }

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
    }
}
