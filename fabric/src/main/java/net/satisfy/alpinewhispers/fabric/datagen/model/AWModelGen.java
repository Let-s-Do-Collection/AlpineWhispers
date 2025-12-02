package net.satisfy.alpinewhispers.fabric.datagen.model;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
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
        modelGen.createPlant(ObjectRegistry.CHRISTMAS_ROSE.get(), ObjectRegistry.POTTED_CHRISTMAS_ROSE.get(), BlockModelGenerators.TintState.NOT_TINTED);
        modelGen.createPlant(ObjectRegistry.SNOW_GENTIAN.get(), ObjectRegistry.POTTED_SNOW_GENTIAN.get(), BlockModelGenerators.TintState.NOT_TINTED);
        genHoarfrostGrass(modelGen);
        modelGen.createDoublePlant(ObjectRegistry.TALL_HOARFROST_GRASS.get(), BlockModelGenerators.TintState.NOT_TINTED);
    }

    public void genHoarfrostGrass(BlockModelGenerators modelGen) {
        var grassVariants = ModelGenHelpers.variantIDs("hoarfrost_grass_%d", 3);

        modelGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
                ObjectRegistry.HOARFROST_GRASS.get(), ModelGenHelpers.variantsA(grassVariants)
        ));

        grassVariants.forEach(variant -> ModelTemplates.CROSS.create(
                variant, TextureMapping.cross(variant), modelGen.modelOutput
        ));
        modelGen.createSimpleFlatItemModel(ObjectRegistry.HOARFROST_GRASS.get(), "_0");
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}
