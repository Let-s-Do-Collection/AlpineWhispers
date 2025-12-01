package net.satisfy.alpinewhispers.fabric.datagen.model;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;


public class AWModelGen extends FabricModelProvider {
    public AWModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators modelGen) {
        ArollaPineModelGen.generateAll(modelGen);
        AlpineGneissModelGen.generateAll(modelGen);

        modelGen.createTrivialCube(ObjectRegistry.FROZEN_DIRT.get());
        modelGen.createTrivialCube(ObjectRegistry.HOMESPUN_WOOL.get());
        modelGen.createCrossBlock(ObjectRegistry.CHRISTMAS_ROSE.get(), BlockModelGenerators.TintState.NOT_TINTED);
        modelGen.createCrossBlock(ObjectRegistry.HOARFROST_GRASS.get(), BlockModelGenerators.TintState.NOT_TINTED);
        modelGen.createDoublePlant(ObjectRegistry.TALL_HOARFROST_GRASS.get(), BlockModelGenerators.TintState.NOT_TINTED);
    }


    @Override
    public void generateItemModels(ItemModelGenerators modelGen) {

    }
}
