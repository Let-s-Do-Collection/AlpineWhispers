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
import net.satisfy.alpinewhispers.client.model.armor.WinterHatModel;
import net.satisfy.alpinewhispers.client.model.entity.ReindeerModel;
import net.satisfy.alpinewhispers.client.renderer.block.*;
import net.satisfy.alpinewhispers.client.renderer.entity.ChairRenderer;
import net.satisfy.alpinewhispers.client.renderer.entity.ReindeerRenderer;
import net.satisfy.alpinewhispers.client.renderer.model.BathtubModel;
import net.satisfy.alpinewhispers.client.weather.GroveSnowWeather;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;

import static net.satisfy.alpinewhispers.core.registry.ObjectRegistry.*;

@Environment(EnvType.CLIENT)
public class AlpineWhispersClient {

    public static void onInitializeClient() {
        RenderTypeRegistry.register(RenderType.cutout(), HOMESPUN_CARPET.get(), TREE_BAUBLES.get(), SNOW_GLOBE.get(), ICICLES.get(), HOARFROST_GRASS.get(), TALL_HOARFROST_GRASS.get(), CHRISTMAS_ROSE.get(), POTTED_CHRISTMAS_ROSE.get() ,SNOW_GENTIAN.get(), POTTED_SNOW_GENTIAN.get() ,AROLLA_PINE_SAPLING.get(), STAR_TOPPER.get(), GARLAND.get(), CANDLE_WREATH.get(), WALL_WREATH.get(), FAIRY_LIGHTS.get(), AROLLA_PINE_WARDROBE.get(), AROLLA_PINE_SINK.get(), AROLLA_PINE_WASHBASIN.get(), AROLLA_PINE_BATHTUB.get(), AROLLA_PINE_TABLE.get(), AROLLA_PINE_CHAIR.get(), AROLLA_PINE_SOFA.get(), FIREPLACE_CORNICE.get(), AROLLA_PINE_WINDOW.get(), AROLLA_PINE_WINDOW_PANE.get(), AROLLA_PINE_DOOR.get(), AROLLA_PINE_TRAPDOOR.get());

        ColorHandlerRegistry.registerBlockColors((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return -1;
            }
            return BiomeColors.getAverageWaterColor(world, pos);
        }, AROLLA_PINE_SINK.get(), AROLLA_PINE_WASHBASIN.get());

        registerBlockEntityRenderer();
        GroveSnowWeather.init();
    }

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityTypeRegistry.CHAIR_ENTITY, ChairRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.REINDEER_ENTITY, ReindeerRenderer::new);
    }

    public static void preInitClient() {
        registerEntityRenderers();
        registerEntityModelLayer();
    }

    public static void registerEntityModelLayer() {
        EntityModelLayerRegistry.register(WinterHatModel.LAYER_LOCATION, WinterHatModel::createBodyLayer);
        EntityModelLayerRegistry.register(BathtubModel.LAYER_LOCATION, BathtubModel::getTexturedModelData);
        EntityModelLayerRegistry.register(ReindeerModel.LAYER_LOCATION, ReindeerModel::getTexturedModelData);
    }

    public static void registerBlockEntityRenderer() {
        BlockEntityRendererRegistry.register(EntityTypeRegistry.TREE_BAUBLES_BLOCK_ENTITY.get(), ctx -> new TreeBaublesRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.BATHTUB_BLOCK_ENTITY.get(), BathtubRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.WARDROBE_BLOCK_ENTITY.get(), WardrobeRenderer::new);
    }
}
