package net.satisfy.hearth_and_haven.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.satisfy.hearth_and_haven.client.HearthAndHavenClient;

public class HearthAndHavenClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HearthAndHavenClient.onInitializeClient();
        HearthAndHavenClient.preInitClient();
    }
}
