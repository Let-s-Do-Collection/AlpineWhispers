package net.satisfy.alpinewhispers.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.satisfy.alpinewhispers.core.config.AlpineWhispersConfig;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;
import net.satisfy.alpinewhispers.fabric.core.config.AlpineWhispersFabricConfig;
import net.satisfy.alpinewhispers.fabric.core.world.AlpineWhispersFabricWorldgen;

import java.util.Optional;

public class AlpineWhispersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigHolder<AlpineWhispersFabricConfig> config = AutoConfig.register(AlpineWhispersFabricConfig.class, GsonConfigSerializer::new);
        config.getConfig().apply();
        config.registerSaveListener((holder, data) -> {
            data.apply();
            return InteractionResult.SUCCESS;
        });
        AlpineWhispers.init();
        AlpineWhispers.commonInit();
        AlpineWhispersFabricWorldgen.init();
        addSpawns();
    }

    private static final TagKey<Biome> REINDEER_HABITAT = TagKey.create(Registries.BIOME, AlpineWhispers.identifier("reindeer_habitat"));

    private void addSpawns() {
        if (AlpineWhispersConfig.reindeerSpawnWeight > 0) {
            BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.hasTag(REINDEER_HABITAT), MobCategory.CREATURE, EntityTypeRegistry.REINDEER_ENTITY.get(), AlpineWhispersConfig.reindeerSpawnWeight, AlpineWhispersConfig.reindeerMinGroup, AlpineWhispersConfig.reindeerMaxGroup);
        }
        if (AlpineWhispersConfig.alpineSheepSpawnWeight > 0) {
            BiomeModifications.addSpawn(biomeSelectionContext -> biomeSelectionContext.hasTag(REINDEER_HABITAT), MobCategory.CREATURE, EntityTypeRegistry.ALPINE_SHEEP_ENTITY.get(), AlpineWhispersConfig.alpineSheepSpawnWeight, AlpineWhispersConfig.alpineSheepMinGroup, AlpineWhispersConfig.alpineSheepMaxGroup);
        }
        if (AlpineWhispersConfig.replaceVanillaFarmAnimals) {
            BiomeModifications.create(AlpineWhispers.identifier("remove_farm_animals_from_reindeer_habitat"))
                    .add(ModificationPhase.REMOVALS, biomeSelectionContext -> biomeSelectionContext.hasTag(REINDEER_HABITAT), context -> context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.SHEEP))
                    .add(ModificationPhase.REMOVALS, biomeSelectionContext -> biomeSelectionContext.hasTag(REINDEER_HABITAT), context -> context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW));
        }

        SpawnPlacements.register(EntityTypeRegistry.REINDEER_ENTITY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AmbientCreature::checkMobSpawnRules);
        SpawnPlacements.register(EntityTypeRegistry.ALPINE_SHEEP_ENTITY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AmbientCreature::checkMobSpawnRules);
    }
}
