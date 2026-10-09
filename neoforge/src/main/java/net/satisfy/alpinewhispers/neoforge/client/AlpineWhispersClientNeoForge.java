package net.satisfy.alpinewhispers.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.client.AlpineWhispersClient;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;
import net.satisfy.alpinewhispers.neoforge.client.extensions.WinterHatExtensions;


@EventBusSubscriber(modid = AlpineWhispers.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class AlpineWhispersClientNeoForge {

    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
        AlpineWhispersClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        AlpineWhispersClient.onInitializeClient();
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new WinterHatExtensions(), ObjectRegistry.WINTER_HAT.get());
    }
}
