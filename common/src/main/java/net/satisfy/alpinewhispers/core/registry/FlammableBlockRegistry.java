package net.satisfy.alpinewhispers.core.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class FlammableBlockRegistry {

    public static void init() {
        addFlammable(5, 20,
                ObjectRegistry.AROLLA_PINE_PLANKS.get(),
                ObjectRegistry.AROLLA_PINE_SLAB.get(),
                ObjectRegistry.AROLLA_PINE_STAIRS.get(),
                ObjectRegistry.AROLLA_PINE_FENCE.get(),
                ObjectRegistry.AROLLA_PINE_FENCE_GATE.get(),
                ObjectRegistry.AROLLA_PINE_BUTTON.get(),
                ObjectRegistry.AROLLA_PINE_PRESSURE_PLATE.get(),
                ObjectRegistry.AROLLA_PINE_DOOR.get(),
                ObjectRegistry.AROLLA_PINE_TRAPDOOR.get(),
                ObjectRegistry.AROLLA_PINE_TABLE.get(),
                ObjectRegistry.AROLLA_PINE_CHAIR.get(),
                ObjectRegistry.AROLLA_PINE_CABINET.get(),
                ObjectRegistry.AROLLA_PINE_WALL_CABINET.get(),
                ObjectRegistry.AROLLA_PINE_COOKING_AISLE.get(),
                ObjectRegistry.AROLLA_PINE_DRESSER.get(),
                ObjectRegistry.AROLLA_PINE_WARDROBE.get(),
                ObjectRegistry.AROLLA_PINE_SOFA.get(),
                ObjectRegistry.AROLLA_PINE_BED.get(),
                ObjectRegistry.AROLLA_PINE_WASHBASIN.get()
        );

        addFlammable(5, 5,
                ObjectRegistry.AROLLA_PINE_LOG.get(),
                ObjectRegistry.AROLLA_PINE_WOOD.get(),
                ObjectRegistry.STRIPPED_AROLLA_PINE_LOG.get(),
                ObjectRegistry.STRIPPED_AROLLA_PINE_WOOD.get()
        );

        addFlammable(30, 60,
                ObjectRegistry.AROLLA_PINE_LEAVES.get(),
                ObjectRegistry.CANDLE_WREATH.get(),
                ObjectRegistry.WALL_WREATH.get(),
                ObjectRegistry.GARLAND.get()
        );

        addFlammable(30, 60,
            ObjectRegistry.HOMESPUN_WOOL.get(),
            ObjectRegistry.HOMESPUN_CARPET.get()
        );

        addFlammable(60, 100,
                ObjectRegistry.SNOW_GENTIAN.get(),
                ObjectRegistry.CHRISTMAS_ROSE.get(),
                ObjectRegistry.HOARFROST_GRASS.get(),
                ObjectRegistry.TALL_HOARFROST_GRASS.get()
        );
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        for (Block block : blocks) {
            fireBlock.setFlammable(block, burnOdd, igniteOdd);
        }
    }
}
