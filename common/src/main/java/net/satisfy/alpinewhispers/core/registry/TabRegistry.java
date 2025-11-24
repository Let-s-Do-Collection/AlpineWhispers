package net.satisfy.alpinewhispers.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.satisfy.alpinewhispers.AlpineWhispers;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.CREATIVE_MODE_TAB);

    @SuppressWarnings("unused")
    public static final RegistrySupplier<CreativeModeTab> ALPINEWHISPERS_TAB = CREATIVE_MODE_TABS.register("alpinewhispers", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(ObjectRegistry.AROLLA_PINE_BATHTUB.get()))
            .title(Component.translatable("creativetab.alpinewhispers.tab"))
            .displayItems((parameters, output) -> {
                output.accept(ObjectRegistry.ALPINE_GNEISS_BRICKS.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_BRICK_SLAB.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_BRICK_WALL.get());
                output.accept(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICKS.get());
                output.accept(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_SLAB.get());
                output.accept(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_WALL.get());
                output.accept(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICKS.get());
                output.accept(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_SLAB.get());
                output.accept(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_WALL.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICKS.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_SLAB.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_WALL.get());
                output.accept(ObjectRegistry.AROLLA_PINE_TIMBER_FLOOR.get());
                output.accept(ObjectRegistry.AROLLA_PINE_BED.get());
                output.accept(ObjectRegistry.AROLLA_PINE_SOFA.get());
                output.accept(ObjectRegistry.AROLLA_PINE_CHAIR.get());
                output.accept(ObjectRegistry.AROLLA_PINE_TABLE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_WARDROBE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_SINK.get());
                output.accept(ObjectRegistry.AROLLA_PINE_SMOKER.get());
                output.accept(ObjectRegistry.AROLLA_PINE_COOKING_AISLE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_CABINET.get());
                output.accept(ObjectRegistry.AROLLA_PINE_DRESSER.get());
                output.accept(ObjectRegistry.AROLLA_PINE_WALL_CABINET.get());
                output.accept(ObjectRegistry.AROLLA_PINE_PRIVY.get());
                output.accept(ObjectRegistry.AROLLA_PINE_WASHBASIN.get());
                output.accept(ObjectRegistry.AROLLA_PINE_BATHTUB.get());
                output.accept(ObjectRegistry.FIREPLACE_CORNICE.get());
            })
            .build());

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
