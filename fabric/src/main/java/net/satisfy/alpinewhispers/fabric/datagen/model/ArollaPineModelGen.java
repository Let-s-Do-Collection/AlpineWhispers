package net.satisfy.alpinewhispers.fabric.datagen.model;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;

import java.util.List;

public interface ArollaPineModelGen {
    List<ResourceLocation> plankVariants = List.of(
            AlpineWhispers.identifier("block/arolla_pine_planks_0"),
            AlpineWhispers.identifier("block/arolla_pine_planks_1"),
            AlpineWhispers.identifier("block/arolla_pine_planks_2"),
            AlpineWhispers.identifier("block/arolla_pine_planks_3")
    );

    static void generateAll(BlockModelGenerators modelGen) {
        generatePlanks(modelGen);
        generateSlabStairFence(modelGen);
        generateLogs(modelGen);

        modelGen.createTrivialBlock(ObjectRegistry.AROLLA_PINE_LEAVES.get(), TexturedModel.LEAVES);
    }

    static void generateLogs(BlockModelGenerators modelGen) {
        modelGen.woodProvider(ObjectRegistry.AROLLA_PINE_LOG.get())
                .wood(ObjectRegistry.AROLLA_PINE_WOOD.get())
                .log(ObjectRegistry.AROLLA_PINE_LOG.get());

        modelGen.woodProvider(ObjectRegistry.STRIPPED_AROLLA_PINE_LOG.get())
                .wood(ObjectRegistry.STRIPPED_AROLLA_PINE_WOOD.get())
                .log(ObjectRegistry.STRIPPED_AROLLA_PINE_LOG.get());
    }

    static void generatePlanks(BlockModelGenerators modelGen) {

        modelGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
                ObjectRegistry.AROLLA_PINE_PLANKS.get(), BlockModelGenerators.wrapModels(
                        plankVariants, m -> m
                ).toArray(Variant[]::new)
        ));

        plankVariants.forEach(variant -> ModelTemplates.CUBE_ALL.create(
                variant, TextureMapping.cube(variant), modelGen.modelOutput
        ));

        modelGen.skipAutoItemBlock(ObjectRegistry.AROLLA_PINE_PLANKS.get());
        modelGen.delegateItemModel(ObjectRegistry.AROLLA_PINE_PLANKS.get(), plankVariants.get(1));
    }

    static void generateSlabStairFence(BlockModelGenerators modelGen) {
        var familyProvider = modelGen.new BlockFamilyProvider(
                TextureMapping.cube(plankVariants.getFirst())
        );
        familyProvider.stairs(ObjectRegistry.AROLLA_PINE_STAIRS.get())
                .button(ObjectRegistry.AROLLA_PINE_BUTTON.get())
                .fence(ObjectRegistry.AROLLA_PINE_FENCE.get())
                .pressurePlate(ObjectRegistry.AROLLA_PINE_PRESSURE_PLATE.get())
                .fenceGate(ObjectRegistry.AROLLA_PINE_FENCE_GATE.get());

        // Slab muss manuell gemacht werden, weil holz nicht standard BSD benützen
        TextureMapping texMap = TextureMapping.cube(plankVariants.getFirst());
        ResourceLocation slabTex = ModelTemplates.SLAB_BOTTOM.create(ObjectRegistry.AROLLA_PINE_SLAB.get(), texMap, modelGen.modelOutput);
        modelGen.blockStateOutput.accept(BlockModelGenerators.createSlab(
                ObjectRegistry.AROLLA_PINE_SLAB.get(), slabTex, slabTex, slabTex
        ));

        modelGen.createOrientableTrapdoor(ObjectRegistry.AROLLA_PINE_TRAPDOOR.get());
    }
}
