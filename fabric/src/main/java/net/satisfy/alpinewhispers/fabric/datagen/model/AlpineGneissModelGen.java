package net.satisfy.alpinewhispers.fabric.datagen.model;

import net.minecraft.data.models.BlockModelGenerators;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;

public interface AlpineGneissModelGen {

    static void generateAll(BlockModelGenerators modelGen) {
        modelGen.family(ObjectRegistry.ALPINE_GNEISS.get())
                .stairs(ObjectRegistry.ALPINE_GNEISS_STAIRS.get())
                .slab(ObjectRegistry.ALPINE_GNEISS_SLAB.get())
                .wall(ObjectRegistry.ALPINE_GNEISS_WALL.get());

        modelGen.family(ObjectRegistry.ALPINE_GNEISS_BRICKS.get())
                .stairs(ObjectRegistry.ALPINE_GNEISS_BRICK_STAIRS.get())
                .slab(ObjectRegistry.ALPINE_GNEISS_BRICK_SLAB.get())
                .wall(ObjectRegistry.ALPINE_GNEISS_BRICK_WALL.get());


        modelGen.family(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICKS.get())
                .stairs(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_STAIRS.get())
                .slab(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_SLAB.get())
                .wall(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_WALL.get());

        modelGen.family(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICKS.get())
                .stairs(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get())
                .slab(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_SLAB.get())
                .wall(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_WALL.get());

        modelGen.family(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICKS.get())
                .stairs(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get())
                .slab(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_SLAB.get())
                .wall(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_WALL.get());
    }
}
