package net.satisfy.alpinewhispers.fabric.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;

import java.util.concurrent.CompletableFuture;

public class AWEnglishLangGen extends FabricLanguageProvider {
    public AWEnglishLangGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder texts) {
        translateAdditionals(texts);
        translateAlpineGneiss(texts);
        translateArollaPine(texts);
        translateDecor(texts);
    }

    public static void translateAdditionals(TranslationBuilder texts) {
        String tt = "tooltip.alpinewhispers.";
        texts.add("creativetab.alpinewhispers.tab", "[Let's Do] AlpineWhispers");
        texts.add(tt + "tooltip_information.hold", "Hold %s for more Information");
        texts.add(tt + "fireplace_cornice.info_0", "Can be filled with any full Block.");
        texts.add(tt + "fireplace_cornice.info_1", "Right-Click with a Pickaxe to remove the inserted Block.");
        texts.add(tt + "splitstone.info_0", "Can be transformed into a Path Block with a Pickaxe.");
        texts.add(tt + "sink.info_0", "Right-click to fill with Water.");
        texts.add(tt + "sink.info_1", "When filled, use a Bucket to collect Water.");
        texts.add(tt + "privy.info_0", "Eww... whose idea was this?!");
        texts.add(tt + "privy.info_1", "Right-Click with spare edible Items to grind them into Rotten Flesh. Has a small chance to produce Bone Meal.");
        texts.add(tt + "wardrobe.info_0", "Right-click to open or close.");
        texts.add(tt + "wardrobe.info_1", "Use to store Armor.");
    }

    public static void translateAlpineGneiss(TranslationBuilder texts) {
        texts.add(ObjectRegistry.ALPINE_GNEISS.get(), "Alpine Gneiss");
        texts.add(ObjectRegistry.ALPINE_GNEISS_STAIRS.get(), "Alpine Gneiss Stairs");
        texts.add(ObjectRegistry.ALPINE_GNEISS_SLAB.get(), "Alpine Gneiss Slab");
        texts.add(ObjectRegistry.ALPINE_GNEISS_WALL.get(), "Alpine Gneiss Wall");

        texts.add(ObjectRegistry.ALPINE_GNEISS_BRICKS.get(), "Alpine Gneiss Bricks");
        texts.add(ObjectRegistry.ALPINE_GNEISS_BRICK_STAIRS.get(), "Alpine Gneiss Brick Stairs");
        texts.add(ObjectRegistry.ALPINE_GNEISS_BRICK_SLAB.get(), "Alpine Gneiss Brick Slab");
        texts.add(ObjectRegistry.ALPINE_GNEISS_BRICK_WALL.get(), "Alpine Gneiss Brick Wall");

        texts.add(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICKS.get(), "Mossy Alpine Gneiss Bricks");
        texts.add(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_STAIRS.get(), "Mossy Alpine Gneiss Brick Stairs");
        texts.add(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_SLAB.get(), "Mossy Alpine Gneiss Brick Slab");
        texts.add(ObjectRegistry.MOSSY_ALPINE_GNEISS_BRICK_WALL.get(), "Mossy Alpine Gneiss Brick Wall");

        texts.add(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICKS.get(), "Layered Alpine Gneiss Bricks");
        texts.add(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get(), "Layered Alpine Gneiss Brick Stairs");
        texts.add(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_SLAB.get(), "Layered Alpine Gneiss Brick Slab");
        texts.add(ObjectRegistry.LAYERED_ALPINE_GNEISS_BRICK_WALL.get(), "Layered Alpine Gneiss Brick Wall");

        texts.add(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICKS.get(), "Mossy Layered Alpine Gneiss Bricks");
        texts.add(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_STAIRS.get(), "Mossy Layered Alpine Gneiss Brick Stairs");
        texts.add(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_SLAB.get(), "Mossy Layered Alpine Gneiss Brick Slab");
        texts.add(ObjectRegistry.MOSSY_LAYERED_ALPINE_GNEISS_BRICK_WALL.get(), "Mossy Layered Alpine Gneiss Brick Wall");
    }

    public static void translateArollaPine(TranslationBuilder texts) {
        texts.add(ObjectRegistry.AROLLA_PINE_LEAVES.get(), "Arolla Pine Leaves");
        texts.add(ObjectRegistry.AROLLA_PINE_SAPLING.get(), "Arolla Pine Sapling");
        texts.add(ObjectRegistry.AROLLA_PINE_LOG.get(), "Arolla Pine Log");
        texts.add(ObjectRegistry.AROLLA_PINE_WOOD.get(), "Arolla Pine Wood");
        texts.add(ObjectRegistry.STRIPPED_AROLLA_PINE_LOG.get(), "Stripped Arolla Pine Log");
        texts.add(ObjectRegistry.STRIPPED_AROLLA_PINE_WOOD.get(), "Stripped Arolla Pine Wood");

        texts.add(ObjectRegistry.AROLLA_PINE_PLANKS.get(), "Arolla Pine Planks");
        texts.add(ObjectRegistry.AROLLA_PINE_STAIRS.get(), "Arolla Pine Stairs");
        texts.add(ObjectRegistry.AROLLA_PINE_PRESSURE_PLATE.get(), "Arolla Pine Pressure Plate");
        texts.add(ObjectRegistry.AROLLA_PINE_DOOR.get(), "Arolla Pine Door");
        texts.add(ObjectRegistry.AROLLA_PINE_FENCE.get(), "Arolla Pine Fence");
        texts.add(ObjectRegistry.AROLLA_PINE_FENCE_GATE.get(), "Arolla Pine Fence Gate");
        texts.add(ObjectRegistry.AROLLA_PINE_BUTTON.get(), "Arolla Pine Button");
        texts.add(ObjectRegistry.AROLLA_PINE_SLAB.get(), "Arolla Pine Slab");
        texts.add(ObjectRegistry.AROLLA_PINE_TRAPDOOR.get(), "Arolla Pine Trapdoor");
        texts.add(ObjectRegistry.AROLLA_PINE_WINDOW_PANE.get(), "Arolla Pine Window Pane");
        texts.add(ObjectRegistry.AROLLA_PINE_WINDOW.get(), "Arolla Pine Window");
    }

    public static void translateDecor(TranslationBuilder texts) {
        texts.add(ObjectRegistry.CANDLE_WREATH.get(), "Candle Wreath");
        texts.add(ObjectRegistry.FAIRY_LIGHTS.get(), "Fairy Lights");
        texts.add(ObjectRegistry.GARLAND.get(), "Garland");
    }
}
