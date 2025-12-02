package net.satisfy.alpinewhispers.fabric.datagen.model;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.satisfy.alpinewhispers.AlpineWhispers;

import java.util.List;
import java.util.stream.IntStream;

public interface ModelGenHelpers {

    static ResourceLocation blockID(String path) {
        return AlpineWhispers.identifier("block/" + path);
    }

    static ResourceLocation itemID(String path) {
        return AlpineWhispers.identifier("item/" + path);
    }

    static List<ResourceLocation> variantIDs(String path, int amount) {
        return IntStream.range(0, amount)
                .mapToObj(i -> blockID(String.format(path, i)))
                .toList();
    }

    static List<Variant> variantsL(List<ResourceLocation> models) {
        return models.stream().map(id -> Variant.variant().with(VariantProperties.MODEL, id))
                .toList();
    }

    static Variant[] variantsA(List<ResourceLocation> models) {
        return models.stream().map(id -> Variant.variant().with(VariantProperties.MODEL, id))
                .toArray(Variant[]::new);
    }

    static void slabFromTex(BlockModelGenerators modelGen, RegistrySupplier<Block> slabBlock, ResourceLocation texPath) {
        var bottomID = ModelLocationUtils.getModelLocation(slabBlock.get(), "_bottom");
        var topID = ModelLocationUtils.getModelLocation(slabBlock.get(), "_top");

        TextureMapping texMap = TextureMapping.cube(texPath);
        ResourceLocation sBottom = ModelTemplates.SLAB_BOTTOM.create(bottomID, texMap, modelGen.modelOutput);
        ResourceLocation sTop = ModelTemplates.SLAB_TOP.create(topID, texMap, modelGen.modelOutput);
        modelGen.blockStateOutput.accept(BlockModelGenerators.createSlab(
                slabBlock.get(), sBottom, sTop, texPath
        ));

        modelGen.delegateItemModel(slabBlock.get().asItem(), bottomID);
    }
}
