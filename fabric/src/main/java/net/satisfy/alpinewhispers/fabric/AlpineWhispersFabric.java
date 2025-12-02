package net.satisfy.alpinewhispers.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;
import net.satisfy.alpinewhispers.fabric.core.world.AlpineWhispersFabricWorldgen;

import java.util.Optional;

public class AlpineWhispersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AlpineWhispers.init();
        AlpineWhispersFabricWorldgen.init();
        addSpawns();
    }

    private static final TagKey<Biome> REINDEER_HABITAT = TagKey.create(Registries.BIOME, AlpineWhispers.identifier("reindeer_habitat"));

    private void addSpawns() {
        BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.hasTag(REINDEER_HABITAT), MobCategory.CREATURE, EntityTypeRegistry.REINDEER_ENTITY.get(), 11, 3, 5);

        SpawnPlacements.register(EntityTypeRegistry.REINDEER_ENTITY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AmbientCreature::checkMobSpawnRules);
    }
}
