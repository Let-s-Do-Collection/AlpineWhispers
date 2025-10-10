package net.satisfy.hearth_and_haven.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.satisfy.hearth_and_haven.HearthAndHaven;

import java.util.Optional;

public class HearthAndHavenFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HearthAndHaven.init();
    }
}
