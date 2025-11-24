package net.satisfy.alpinewhispers.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.satisfy.alpinewhispers.client.renderer.block.*;
import net.satisfy.alpinewhispers.client.renderer.entity.ChairRenderer;
import net.satisfy.alpinewhispers.client.renderer.model.BathtubModel;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;

import static net.satisfy.alpinewhispers.core.registry.ObjectRegistry.*;

@Environment(EnvType.CLIENT)
public class AlpineWhispersClient {

    public static void onInitializeClient() {
        RenderTypeRegistry.register(RenderType.cutout(), AROLLA_PINE_WARDROBE.get(), AROLLA_PINE_SINK.get(), AROLLA_PINE_WASHBASIN.get(), AROLLA_PINE_BATHTUB.get(), AROLLA_PINE_TABLE.get(), AROLLA_PINE_CHAIR.get(), AROLLA_PINE_SOFA.get(), FIREPLACE_CORNICE.get());

        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return -1;
            }
            return BiomeColors.getAverageWaterColor(world, pos);
        }, AROLLA_PINE_SINK.get(), AROLLA_PINE_WASHBASIN.get());

        registerBlockEntityRenderer();
    }

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityTypeRegistry.CHAIR_ENTITY, ChairRenderer::new);
    }

    public static void preInitClient() {
        registerEntityRenderers();
        registerEntityModelLayer();
    }

    public static void registerEntityModelLayer() {
        EntityModelLayerRegistry.register(BathtubModel.LAYER_LOCATION, BathtubModel::getTexturedModelData);
    }

    public static void registerBlockEntityRenderer() {
        BlockEntityRendererRegistry.register(EntityTypeRegistry.BATHTUB_BLOCK_ENTITY.get(), BathtubRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.WARDROBE_BLOCK_ENTITY.get(), WardrobeRenderer::new);
    }
}
