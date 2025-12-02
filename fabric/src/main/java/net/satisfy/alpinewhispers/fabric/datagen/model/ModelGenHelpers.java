package net.satisfy.alpinewhispers.fabric.datagen.model;

import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.resources.ResourceLocation;
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
}
