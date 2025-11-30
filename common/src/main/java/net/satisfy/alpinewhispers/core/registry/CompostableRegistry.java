package net.satisfy.alpinewhispers.core.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {
    public static void init() {
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.AROLLA_PINE_LEAVES.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SNOW_GENTIAN.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CHRISTMAS_ROSE.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.HOARFROST_GRASS.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TALL_HOARFROST_GRASS.get().asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CANDLE_WREATH.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.WALL_WREATH.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.GARLAND.get().asItem(), 0.65F);
    }
}
