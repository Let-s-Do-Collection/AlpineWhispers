package net.satisfy.alpinewhispers;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.alpinewhispers.core.registry.*;

public class AlpineWhispers {
    public static final String MOD_ID = "alpinewhispers";

    public static ResourceLocation identifier(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init() {
        ObjectRegistry.init();
        EntityTypeRegistry.init();
        TabRegistry.init();
        SoundEventRegistry.init();
        PlacerTypeRegistry.init();
    }

    public static void commonInit() {
        FlammableBlockRegistry.init();
        AxeItemHooks.addStrippable(ObjectRegistry.AROLLA_PINE_LOG.get(), ObjectRegistry.STRIPPED_AROLLA_PINE_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.AROLLA_PINE_WOOD.get(), ObjectRegistry.STRIPPED_AROLLA_PINE_WOOD.get());
    }
}