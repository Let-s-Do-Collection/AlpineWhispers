package net.satisfy.alpinewhispers.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.satisfy.alpinewhispers.client.AlpineWhispersClient;
import net.satisfy.alpinewhispers.client.particle.SnowGlobeFlakeParticle;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;
import net.satisfy.alpinewhispers.core.registry.ParticleTypeRegistry;
import net.satisfy.alpinewhispers.fabric.client.renderer.WinterHatRenderer;

public class AlpineWhispersClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AlpineWhispersClient.onInitializeClient();
        AlpineWhispersClient.preInitClient();
        ParticleFactoryRegistry.getInstance().register(ParticleTypeRegistry.SNOW_GLOBE_FLAKE.get(), SnowGlobeFlakeParticle.Provider::new);
        ArmorRenderer.register(new WinterHatRenderer(), ObjectRegistry.WINTER_HAT.get());
    }
}
