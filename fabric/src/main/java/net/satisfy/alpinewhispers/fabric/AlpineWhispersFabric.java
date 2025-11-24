package net.satisfy.alpinewhispers.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.satisfy.alpinewhispers.AlpineWhispers;

import java.util.Optional;

public class AlpineWhispersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AlpineWhispers.init();
    }
}
