package net.satisfy.alpinewhispers.fabric.datagen.model;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.satisfy.alpinewhispers.core.block.FrostyBlock;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;

import java.util.List;

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

        modelGen.family(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICKS.get())
                .stairs(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get())
                .slab(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_SLAB.get())
                .wall(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_WALL.get());

        generateFrostVariants(modelGen);

        var mossyBricksProv = modelGen.new BlockFamilyProvider(
                TextureMapping.cube(ModelGenHelpers.blockID("mossy_alpine_gneiss_bricks"))
        );

        mossyBricksProv
                .stairs(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_STAIRS.get())
                .wall(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_WALL.get());

        mossyBricksProv = modelGen.new BlockFamilyProvider(
                TextureMapping.cube(ModelGenHelpers.blockID("mossy_layered_alpine_gneiss_bricks"))
        );

        mossyBricksProv
                .stairs(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get())
                .wall(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_WALL.get());
    }

    static void generateFrostVariants(BlockModelGenerators modelGen) {
        var frostVariants = List.of(
                ModelGenHelpers.blockID("mossy_alpine_gneiss_bricks"),
                ModelGenHelpers.blockID("mossy_layered_alpine_gneiss_bricks"),
                ModelGenHelpers.blockID("frosty_alpine_gneiss_bricks"),
                ModelGenHelpers.blockID("frosty_layered_alpine_gneiss_bricks")
        );

        frostVariants.forEach(variant -> ModelTemplates.CUBE_ALL.create(
                variant, TextureMapping.cube(variant), modelGen.modelOutput
        ));

        modelGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICKS.get())
                .with(PropertyDispatch.property(FrostyBlock.FROSTY)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, frostVariants.getFirst()))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, frostVariants.get(2)))
                ));

        modelGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICKS.get())
                .with(PropertyDispatch.property(FrostyBlock.FROSTY)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, frostVariants.get(1)))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, frostVariants.get(3)))
                ));

        ModelGenHelpers.slabFromTex(modelGen, ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_SLAB, frostVariants.getFirst());
        ModelGenHelpers.slabFromTex(modelGen, ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_SLAB, frostVariants.get(1));
    }
}
