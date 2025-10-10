package net.satisfy.hearth_and_haven;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.hearth_and_haven.core.registry.*;

public class HearthAndHaven {
    public static final String MOD_ID = "hearth_and_haven";

    public static ResourceLocation identifier(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init() {
        ObjectRegistry.init();
        EntityTypeRegistry.init();
        TabRegistry.init();
        SoundEventRegistry.init();
    }
}