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
            .icon(() -> new ItemStack(ObjectRegistry.WALL_WREATH.get()))
            .title(Component.translatable("creativetab.alpinewhispers.tab"))
            .displayItems((parameters, output) -> {
                output.accept(ObjectRegistry.FROZEN_DIRT.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_STAIRS.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_SLAB.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_WALL.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_BRICKS.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_BRICK_SLAB.get());
                output.accept(ObjectRegistry.ALPINE_GNEISS_BRICK_WALL.get());
                output.accept(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICKS.get());
                output.accept(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_SLAB.get());
                output.accept(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_WALL.get());
                output.accept(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICKS.get());
                output.accept(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_SLAB.get());
                output.accept(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_WALL.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICKS.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_SLAB.get());
                output.accept(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_WALL.get());
                output.accept(ObjectRegistry.AROLLA_PINE_LOG.get());
                output.accept(ObjectRegistry.STRIPPED_AROLLA_PINE_LOG.get());
                output.accept(ObjectRegistry.AROLLA_PINE_WOOD.get());
                output.accept(ObjectRegistry.STRIPPED_AROLLA_PINE_WOOD.get());
                output.accept(ObjectRegistry.AROLLA_PINE_PLANKS.get());
                output.accept(ObjectRegistry.AROLLA_PINE_STAIRS.get());
                output.accept(ObjectRegistry.AROLLA_PINE_SLAB.get());
                output.accept(ObjectRegistry.AROLLA_PINE_FENCE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_FENCE_GATE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_DOOR.get());
                output.accept(ObjectRegistry.AROLLA_PINE_TRAPDOOR.get());
                output.accept(ObjectRegistry.AROLLA_PINE_PRESSURE_PLATE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_BUTTON.get());
                output.accept(ObjectRegistry.AROLLA_PINE_WINDOW.get());
                output.accept(ObjectRegistry.AROLLA_PINE_WINDOW_PANE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_SAPLING.get());
                output.accept(ObjectRegistry.AROLLA_PINE_LEAVES.get());
                output.accept(ObjectRegistry.CHRISTMAS_ROSE.get());
                output.accept(ObjectRegistry.SNOW_GENTIAN.get());
                output.accept(ObjectRegistry.HOARFROST_GRASS.get());
                output.accept(ObjectRegistry.TALL_HOARFROST_GRASS.get());
                output.accept(ObjectRegistry.HOMESPUN_WOOL.get());
                output.accept(ObjectRegistry.HOMESPUN_CARPET.get());
                output.accept(ObjectRegistry.AROLLA_PINE_BED.get());
                output.accept(ObjectRegistry.AROLLA_PINE_SOFA.get());
                output.accept(ObjectRegistry.AROLLA_PINE_CHAIR.get());
                output.accept(ObjectRegistry.AROLLA_PINE_TABLE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_WARDROBE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_DRESSER.get());
                output.accept(ObjectRegistry.AROLLA_PINE_CABINET.get());
                output.accept(ObjectRegistry.AROLLA_PINE_WALL_CABINET.get());
                output.accept(ObjectRegistry.AROLLA_PINE_SINK.get());
                output.accept(ObjectRegistry.AROLLA_PINE_WASHBASIN.get());
                output.accept(ObjectRegistry.AROLLA_PINE_BATHTUB.get());
                output.accept(ObjectRegistry.AROLLA_PINE_PRIVY.get());
                output.accept(ObjectRegistry.AROLLA_PINE_COOKING_AISLE.get());
                output.accept(ObjectRegistry.AROLLA_PINE_SMOKER.get());
                output.accept(ObjectRegistry.FIREPLACE_CORNICE.get());
                output.accept(ObjectRegistry.ICICLES.get());
                output.accept(ObjectRegistry.CANDLE_WREATH.get());
                output.accept(ObjectRegistry.WALL_WREATH.get());
                output.accept(ObjectRegistry.GARLAND.get());
                output.accept(ObjectRegistry.FAIRY_LIGHTS.get());
                output.accept(ObjectRegistry.STAR_TOPPER.get());
                output.accept(ObjectRegistry.TREE_BAUBLES_ITEM.get());
                output.accept(ObjectRegistry.SNOW_GLOBE.get());
                output.accept(ObjectRegistry.WINTER_HAT.get());
                output.accept(ObjectRegistry.BREAD_ON_A_STICK.get());
                output.accept(ObjectRegistry.REINDEER.get());
                output.accept(ObjectRegistry.COOKED_REINDEER.get());
                output.accept(ObjectRegistry.COOKED_REINDEER_DISH.get());
                output.accept(ObjectRegistry.WINTER_MAGIC_MUSIC_DISC.get());
                output.accept(ObjectRegistry.REINDEER_SPAWN_EGG.get());
                output.accept(ObjectRegistry.ALPINE_SHEEP_SPAWN_EGG.get());
            })
            .build());

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
