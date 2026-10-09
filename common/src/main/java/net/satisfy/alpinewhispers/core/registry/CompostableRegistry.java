package net.satisfy.alpinewhispers.core.registry;

import net.satisfy.foundation.compostable.FoundationCompostables;

public class CompostableRegistry {
    public static void init() {
        FoundationCompostables.register(0.3F, ObjectRegistry.AROLLA_PINE_LEAVES);
        FoundationCompostables.register(0.65F, ObjectRegistry.SNOW_GENTIAN);
        FoundationCompostables.register(0.65F, ObjectRegistry.CHRISTMAS_ROSE);
        FoundationCompostables.register(0.3F, ObjectRegistry.HOARFROST_GRASS);
        FoundationCompostables.register(0.5F, ObjectRegistry.TALL_HOARFROST_GRASS);
        FoundationCompostables.register(0.65F, ObjectRegistry.CANDLE_WREATH);
        FoundationCompostables.register(0.65F, ObjectRegistry.WALL_WREATH);
        FoundationCompostables.register(0.65F, ObjectRegistry.GARLAND);
    }
}
