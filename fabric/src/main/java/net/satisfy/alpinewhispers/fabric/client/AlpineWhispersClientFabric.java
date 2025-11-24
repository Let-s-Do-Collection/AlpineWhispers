package net.satisfy.alpinewhispers.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.alpinewhispers.client.AlpineWhispersClient;
import net.satisfy.alpinewhispers.fabric.client.renderer.block.FireplaceCorniceTexturedModel;

public class AlpineWhispersClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AlpineWhispersClient.onInitializeClient();
        AlpineWhispersClient.preInitClient();
        registerFoundationModelHandler();
    }

    private static void registerFoundationModelHandler() {
        ModelLoadingPlugin.register(context ->
                context.modifyModelAfterBake().register((bakedModel, ctx) -> {
                    ResourceLocation id = ctx.resourceId();
                    if (id != null && isTexturableModel(id)) {
                        return bakedModel == null ? null : new FireplaceCorniceTexturedModel(bakedModel);
                    }
                    return bakedModel;
                })
        );
    }

    private static boolean isTexturableModel(ResourceLocation id) {
        String p = id.getPath();
        if (p.startsWith("block/fireplace_cornice_")) {
            if (p.endsWith("_bottom")) return false;
            return p.endsWith("_top") || p.endsWith("_left") || p.endsWith("_right");
        }
        return false;
    }
}
