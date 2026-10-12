package net.satisfy.alpinewhispers.neoforge;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;
import net.satisfy.alpinewhispers.neoforge.core.config.AlpineWhispersNeoForgeConfig;
import net.satisfy.alpinewhispers.neoforge.core.world.AlpineWhispersBiomeModifiers;

import java.util.Objects;

@Mod(AlpineWhispers.MOD_ID)
public class AlpineWhispersNeoForge {

    public AlpineWhispersNeoForge(ModContainer modContainer, final IEventBus modEventBus) {
        modContainer.registerConfig(ModConfig.Type.STARTUP, AlpineWhispersNeoForgeConfig.STARTUP);
        AlpineWhispersNeoForgeConfig.applyStartup();
        AlpineWhispers.init();

        modContainer.registerConfig(ModConfig.Type.COMMON, AlpineWhispersNeoForgeConfig.COMMON);
        modEventBus.addListener((ModConfigEvent.Loading event) -> {
            if (event.getConfig().getSpec() == AlpineWhispersNeoForgeConfig.COMMON) AlpineWhispersNeoForgeConfig.applyCommon();
        });
        modEventBus.addListener((ModConfigEvent.Reloading event) -> {
            if (event.getConfig().getSpec() == AlpineWhispersNeoForgeConfig.COMMON) AlpineWhispersNeoForgeConfig.applyCommon();
        });

        AlpineWhispersBiomeModifiers.register(modEventBus);
        Objects.requireNonNull(modContainer.getEventBus()).addListener(this::onRegisterSpawnPlacement);
        modEventBus.addListener(this::commonSetup);
    }

    private void onRegisterSpawnPlacement(RegisterSpawnPlacementsEvent event) {
        event.register(EntityTypeRegistry.REINDEER_ENTITY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityTypeRegistry.ALPINE_SHEEP_ENTITY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AlpineWhispers.commonInit();
        });
    }
}