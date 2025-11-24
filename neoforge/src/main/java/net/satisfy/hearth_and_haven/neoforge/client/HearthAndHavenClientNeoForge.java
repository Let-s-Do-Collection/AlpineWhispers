package net.satisfy.hearth_and_haven.neoforge.client;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.satisfy.hearth_and_haven.HearthAndHaven;
import net.satisfy.hearth_and_haven.client.HearthAndHavenClient;
import net.satisfy.hearth_and_haven.neoforge.client.renderer.block.FireplaceCorniceTexturedModel;

import java.util.Map;

@EventBusSubscriber(modid = HearthAndHaven.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class HearthAndHavenClientNeoForge {

    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
        HearthAndHavenClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        HearthAndHavenClient.onInitializeClient();
    }

    @SubscribeEvent
    public static void wrapFireplaceCornice(ModelEvent.ModifyBakingResult e) {
        Map<ModelResourceLocation, BakedModel> models = e.getModels();
        for (var entry : models.entrySet()) {
            ResourceLocation loc = entry.getKey().id();
            if (!HearthAndHaven.MOD_ID.equals(loc.getNamespace())) continue;
            if (loc.getPath().contains("fireplace_cornice")) {
                models.put(entry.getKey(), new FireplaceCorniceTexturedModel(entry.getValue(), (q, s) -> q.getTintIndex() == 1));
            }
        }
    }
}
