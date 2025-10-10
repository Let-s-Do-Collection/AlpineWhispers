package net.satisfy.hearth_and_haven.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.satisfy.hearth_and_haven.HearthAndHaven;
import net.satisfy.hearth_and_haven.client.HearthAndHavenClient;

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

}
