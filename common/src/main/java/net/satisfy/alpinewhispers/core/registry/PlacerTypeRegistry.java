package net.satisfy.alpinewhispers.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.world.feature.configured.tree.foliage.ArollaPineFoliagePlacer;
import net.satisfy.alpinewhispers.core.world.feature.configured.tree.rock.RockPileFeature;
import net.satisfy.alpinewhispers.core.world.feature.configured.tree.rock.RockPileFeatureConfig;


public class PlacerTypeRegistry {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.FOLIAGE_PLACER_TYPE);
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.TRUNK_PLACER_TYPE);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.TREE_DECORATOR_TYPE);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.FEATURE);

    public static final RegistrySupplier<FoliagePlacerType<ArollaPineFoliagePlacer>> AROLLA_PINE_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("arolla_pine_foliage_placer", () -> new FoliagePlacerType<>(ArollaPineFoliagePlacer.CODEC));

    public static final RegistrySupplier<Feature<RockPileFeatureConfig>> ROCK_PILE_FEATURE = FEATURES.register("rock_pile", RockPileFeature::new);

    public static void init() {
        FOLIAGE_PLACER_TYPES.register();
        TRUNK_PLACER_TYPES.register();
        TREE_DECORATOR_TYPES.register();
        FEATURES.register();
    }
}