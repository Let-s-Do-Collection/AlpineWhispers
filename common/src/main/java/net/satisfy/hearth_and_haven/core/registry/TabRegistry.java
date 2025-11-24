package net.satisfy.hearth_and_haven.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.satisfy.hearth_and_haven.HearthAndHaven;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(HearthAndHaven.MOD_ID, Registries.CREATIVE_MODE_TAB);

    @SuppressWarnings("unused")
    public static final RegistrySupplier<CreativeModeTab> HEARTH_AND_HAVEN_TAB = CREATIVE_MODE_TABS.register("hearth_and_haven", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(ObjectRegistry.RUSTIC_BATHTUB.get()))
            .title(Component.translatable("creativetab.hearth_and_haven.tab"))
            .displayItems((parameters, output) -> {
                output.accept(ObjectRegistry.alpine_gneiss_BRICKS.get());
                output.accept(ObjectRegistry.alpine_gneiss_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.alpine_gneiss_BRICK_SLAB.get());
                output.accept(ObjectRegistry.alpine_gneiss_BRICK_WALL.get());
                output.accept(ObjectRegistry.MOSSY_alpine_gneiss_BRICKS.get());
                output.accept(ObjectRegistry.MOSSY_alpine_gneiss_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.MOSSY_alpine_gneiss_BRICK_SLAB.get());
                output.accept(ObjectRegistry.MOSSY_alpine_gneiss_BRICK_WALL.get());
                output.accept(ObjectRegistry.LAYERED_alpine_gneiss_BRICKS.get());
                output.accept(ObjectRegistry.LAYERED_alpine_gneiss_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.LAYERED_alpine_gneiss_BRICK_SLAB.get());
                output.accept(ObjectRegistry.LAYERED_alpine_gneiss_BRICK_WALL.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_alpine_gneiss_BRICKS.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_alpine_gneiss_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_alpine_gneiss_BRICK_SLAB.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_alpine_gneiss_BRICK_WALL.get());
                output.accept(ObjectRegistry.RUSTIC_TIMBER_FLOOR.get());
                output.accept(ObjectRegistry.RUSTIC_BED.get());
                output.accept(ObjectRegistry.RUSTIC_SOFA.get());
                output.accept(ObjectRegistry.RUSTIC_CHAIR.get());
                output.accept(ObjectRegistry.RUSTIC_TABLE.get());
                output.accept(ObjectRegistry.RUSTIC_WARDROBE.get());
                output.accept(ObjectRegistry.RUSTIC_SINK.get());
                output.accept(ObjectRegistry.RUSTIC_SMOKER.get());
                output.accept(ObjectRegistry.RUSTIC_COOKING_AISLE.get());
                output.accept(ObjectRegistry.RUSTIC_CABINET.get());
                output.accept(ObjectRegistry.RUSTIC_DRESSER.get());
                output.accept(ObjectRegistry.RUSTIC_WALL_CABINET.get());
                output.accept(ObjectRegistry.RUSTIC_PRIVY.get());
                output.accept(ObjectRegistry.RUSTIC_WASHBASIN.get());
                output.accept(ObjectRegistry.RUSTIC_BATHTUB.get());
                output.accept(ObjectRegistry.FIREPLACE_CORNICE.get());
            })
            .build());

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
