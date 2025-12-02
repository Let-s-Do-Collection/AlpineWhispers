package net.satisfy.alpinewhispers.fabric.datagen.model;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.alpinewhispers.core.block.SnowyLeavesBlock;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;

import java.util.List;

public interface ArollaPineModelGen {
    List<ResourceLocation> plankVariants = ModelGenHelpers
            .variantIDs("arolla_pine_planks_%d", 4);

    static void generateAll(BlockModelGenerators modelGen) {
        generatePlanks(modelGen);
        generateSlabStairFence(modelGen);
        generateLogs(modelGen);
        generateLeaves(modelGen);
    }

    static void generateLeaves(BlockModelGenerators modelGen) {
        var leafVariants = List.of(
                ModelGenHelpers.blockID("arolla_pine_leaves"),
                ModelGenHelpers.blockID("snowy_arolla_pine_leaves_0"),
                ModelGenHelpers.blockID("snowy_arolla_pine_leaves_1")
        );

        modelGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(ObjectRegistry.AROLLA_PINE_LEAVES.get())
                .with(PropertyDispatch.property(SnowyLeavesBlock.SNOWY)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, leafVariants.getFirst()))
                        .select(true, ModelGenHelpers.variantsL(leafVariants.subList(1, 3)))
                ));

        leafVariants.forEach(variant -> ModelTemplates.CUBE_ALL.create(
                variant, TextureMapping.cube(variant), modelGen.modelOutput
        ));
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
                ObjectRegistry.AROLLA_PINE_PLANKS.get(), ModelGenHelpers.variantsA(plankVariants)
        ));

        plankVariants.forEach(variant -> ModelTemplates.CUBE_ALL.create(
                variant, TextureMapping.cube(variant), modelGen.modelOutput
        ));

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

        ModelGenHelpers.slabFromTex(modelGen, ObjectRegistry.AROLLA_PINE_SLAB, plankVariants.getFirst());

        modelGen.createOrientableTrapdoor(ObjectRegistry.AROLLA_PINE_TRAPDOOR.get());
    }
}