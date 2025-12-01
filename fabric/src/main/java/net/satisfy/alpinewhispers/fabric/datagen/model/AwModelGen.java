package net.satisfy.alpinewhispers.fabric.datagen.model;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.TexturedModel;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;


public class AwModelGen extends FabricModelProvider {
    public AwModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators modelGen) {
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

        modelGen.createTrivialBlock(ObjectRegistry.AROLLA_PINE_LOG.get(), TexturedModel.COLUMN_ALT);

        // PLANKS are random, not yet done
        /*modelGen.family(ObjectRegistry.AROLLA_PINE_PLANKS.get())
                .stairs(ObjectRegistry.AROLLA_PINE_STAIRS.get())
                .slab(ObjectRegistry.AROLLA_PINE_SLAB.get())
                .button(ObjectRegistry.AROLLA_PINE_BUTTON.get());*/

        modelGen.createCrossBlock(ObjectRegistry.CHRISTMAS_ROSE.get(), BlockModelGenerators.TintState.NOT_TINTED);
        modelGen.createCrossBlock(ObjectRegistry.HOARFROST_GRASS.get(), BlockModelGenerators.TintState.NOT_TINTED);
        modelGen.createDoublePlant(ObjectRegistry.TALL_HOARFROST_GRASS.get(), BlockModelGenerators.TintState.NOT_TINTED);
    }


    @Override
    public void generateItemModels(ItemModelGenerators modelGen) {

    }
}
