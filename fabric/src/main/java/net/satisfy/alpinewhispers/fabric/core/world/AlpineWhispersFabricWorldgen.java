package net.satisfy.alpinewhispers.fabric.core.world;

import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.satisfy.alpinewhispers.AlpineWhispers;

import java.util.function.Predicate;

public final class AlpineWhispersFabricWorldgen {

    public static void init() {
        registerFeatureAdditions();
        registerFeatureRemovals();
    }

    static Predicate<BiomeSelectionContext> grove = BiomeSelectors.includeByKey(Biomes.GROVE);
    static Predicate<BiomeSelectionContext> snowyPlains = BiomeSelectors.includeByKey(Biomes.SNOWY_PLAINS);

    public static final ResourceKey<PlacedFeature> ALPINE_GNEISS_BOULDER_PLACED = registerPlacedFeature("alpine_gneiss_boulder_placed");
    public static final ResourceKey<PlacedFeature> ALPINE_GNEISS_ROCK_PLACED = registerPlacedFeature("alpine_gneiss_rock_placed");
    public static final ResourceKey<PlacedFeature> ALPINE_GNEISS_SLABS_PLACED = registerPlacedFeature("alpine_gneiss_slabs_placed");

    public static final ResourceKey<PlacedFeature> AROLLA_PINE_TREE_FALLEN_CHECKED = registerPlacedFeature("arolla_pine_tree_fallen_checked");

    public static final ResourceKey<PlacedFeature> COLD_FLOWERS_PATCH_PLACED = registerPlacedFeature("cold_flowers_patch_placed");
    public static final ResourceKey<PlacedFeature> FROZEN_DIRT_DISK_PLACED = registerPlacedFeature("frozen_dirt_disk_placed");
    public static final ResourceKey<PlacedFeature> HOARFROST_GRASS_PATCH_PLACED = registerPlacedFeature("hoarfrost_grass_patch_placed");
    public static final ResourceKey<PlacedFeature> ICICLES_PATCH_PLACED = registerPlacedFeature("icicles_patch_placed");
    public static final ResourceKey<PlacedFeature> SNOW_DRIFTS_PLACED = registerPlacedFeature("snow_drifts_placed");
    public static final ResourceKey<PlacedFeature> SNOW_UNDER_TREES_PLACED = registerPlacedFeature("snow_under_trees_placed");
    public static final ResourceKey<PlacedFeature> STONE_REPLACER_PLACED = registerPlacedFeature("stone_replacer_placed");

    public static final ResourceKey<PlacedFeature> TREES_GROVE_CHECKED = registerPlacedFeature("trees_grove_checked");
    public static final ResourceKey<PlacedFeature> TREES_SNOWY_PLAINS_CHECKED = registerPlacedFeature("trees_snowy_plains_checked");

    public static final ResourceKey<PlacedFeature> MINECRAFT_TREES_GROVE = registerMCKey("trees_grove");
    public static final ResourceKey<PlacedFeature> MINECRAFT_PATCH_PUMPKIN = registerMCKey("patch_pumpkin");
    public static final ResourceKey<PlacedFeature> MINECRAFT_TREES_SNOWY = registerMCKey("trees_snowy");
    public static final ResourceKey<PlacedFeature> MINECRAFT_FLOWER_DEFAULT = registerMCKey("flower_default");
    public static final ResourceKey<PlacedFeature> MINECRAFT_PATCH_GRASS_BADLANDS = registerMCKey("patch_grass_badlands");
    public static final ResourceKey<PlacedFeature> MINECRAFT_BROWN_MUSHROOM_NORMAL = registerMCKey("brown_mushroom_normal");
    public static final ResourceKey<PlacedFeature> MINECRAFT_RED_MUSHROOM_NORMAL = registerMCKey("red_mushroom_normal");
    public static final ResourceKey<PlacedFeature> MINECRAFT_PATCH_SUGAR_CANE = registerMCKey("patch_sugar_cane");

    public static void registerFeatureAdditions() {
        BiomeModification world = BiomeModifications.create(ResourceLocation.fromNamespaceAndPath(AlpineWhispers.MOD_ID, "world_features"));

        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, SNOW_UNDER_TREES_PLACED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, SNOW_DRIFTS_PLACED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, FROZEN_DIRT_DISK_PLACED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, STONE_REPLACER_PLACED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HOARFROST_GRASS_PATCH_PLACED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TREES_GROVE_CHECKED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ALPINE_GNEISS_ROCK_PLACED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AROLLA_PINE_TREE_FALLEN_CHECKED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ALPINE_GNEISS_SLABS_PLACED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ALPINE_GNEISS_BOULDER_PLACED));
        world.add(ModificationPhase.ADDITIONS, grove, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ICICLES_PATCH_PLACED));

        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, SNOW_UNDER_TREES_PLACED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, SNOW_DRIFTS_PLACED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, FROZEN_DIRT_DISK_PLACED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HOARFROST_GRASS_PATCH_PLACED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TREES_SNOWY_PLAINS_CHECKED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ALPINE_GNEISS_ROCK_PLACED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AROLLA_PINE_TREE_FALLEN_CHECKED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, COLD_FLOWERS_PATCH_PLACED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ALPINE_GNEISS_SLABS_PLACED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ALPINE_GNEISS_BOULDER_PLACED));
        world.add(ModificationPhase.ADDITIONS, snowyPlains, context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ICICLES_PATCH_PLACED));}

    public static void registerFeatureRemovals() {
        BiomeModification world = BiomeModifications.create(ResourceLocation.fromNamespaceAndPath(AlpineWhispers.MOD_ID, "world_features_removals"));

        world.add(ModificationPhase.REMOVALS, grove, context -> context.getGenerationSettings().removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINECRAFT_PATCH_PUMPKIN));
        world.add(ModificationPhase.REMOVALS, grove, context -> context.getGenerationSettings().removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINECRAFT_TREES_GROVE));
        world.add(ModificationPhase.REMOVALS, snowyPlains, context -> context.getGenerationSettings().removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINECRAFT_TREES_SNOWY));
        world.add(ModificationPhase.REMOVALS, snowyPlains, context -> context.getGenerationSettings().removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINECRAFT_FLOWER_DEFAULT));
        world.add(ModificationPhase.REMOVALS, snowyPlains, context -> context.getGenerationSettings().removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINECRAFT_PATCH_GRASS_BADLANDS));
        world.add(ModificationPhase.REMOVALS, snowyPlains, context -> context.getGenerationSettings().removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINECRAFT_BROWN_MUSHROOM_NORMAL));
        world.add(ModificationPhase.REMOVALS, snowyPlains, context -> context.getGenerationSettings().removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINECRAFT_PATCH_SUGAR_CANE));
        world.add(ModificationPhase.REMOVALS, snowyPlains, context -> context.getGenerationSettings().removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MINECRAFT_RED_MUSHROOM_NORMAL));
    }


    public static ResourceKey<PlacedFeature> registerPlacedFeature(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, AlpineWhispers.identifier(name));
    }


    public static ResourceKey<PlacedFeature> registerMCKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.withDefaultNamespace(name));
    }
}