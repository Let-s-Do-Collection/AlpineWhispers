package net.satisfy.alpinewhispers.fabric.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class AWEnglishLangGen extends FabricLanguageProvider {
    public AWEnglishLangGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder texts) {
        texts.add("creativetab.alpinewhispers.tab", "[Let's Do] Alpine Whispers");
        translateTooltips(texts);
        translateSounds(texts);
        translateAlpineGneiss(texts);
        translateArollaPine(texts);
        translateDecor(texts);
        translateArollaPineFurniture(texts);
        translatePlants(texts);
        translateItems(texts);
        translateEntities(texts);
    }

    public static void translateTooltips(TranslationBuilder texts) {
        BiConsumer<String, String> tooltips = (key, value) -> texts.add(
                "tooltip.alpinewhispers." + key, value
        );
        tooltips.accept("tooltip_information.hold", "Hold %s for more Information");
        tooltips.accept("fireplace_cornice.info_0", "Can be filled with any full Block.");
        tooltips.accept("fireplace_cornice.info_1", "Right-Click with a Pickaxe to remove the inserted Block.");
        tooltips.accept("splitstone.info_0", "Can be transformed into a Path Block with a Pickaxe.");
        tooltips.accept("sink.info_0", "Right-click to fill with Water.");
        tooltips.accept("sink.info_1", "When filled, use a Bucket to collect Water.");
        tooltips.accept("privy.info_0", "Eww... whose idea was this?!");
        tooltips.accept("privy.info_1", "Right-Click with spare edible Items to grind them into Rotten Flesh. Has a small chance to produce Bone Meal.");
        tooltips.accept("wardrobe.info_0", "Right-click to open or close.");
        tooltips.accept("wardrobe.info_1", "Use to store Armor.");
        tooltips.accept("canbeplaced", "Can be placed.");
        tooltips.accept("canbeplacedonwalls", "Can be placed on Walls.");
        tooltips.accept("canbeplacedonbottomface", "Can be placed underneath other Blocks");
        tooltips.accept("tree_baubles.info_0", "Right-Click while holding it to place it on Leaves.");
        tooltips.accept("tree_baubles.info_1", "Shift Right-Click to remove it.");
        tooltips.accept("snowy_leaves.info_0", "Right-Click with a Snowball to cover the Leaves in Snow.");
        tooltips.accept("snowy_leaves.info_1", "Right-Click to clear the Snow.");
        tooltips.accept("frosty_block.info_0", "Right-Click with a Snowball to Cover the Bricks in Snow.");
        tooltips.accept("frosty_block.info_1", "Right-Click while holding a Brush to clear the Snow.");
        tooltips.accept("bread_on_a_stick.info_0", "Use it to ride Alpine Sheep like a true mountaineer!");
        tooltips.accept("bread_on_a_stick.info_1", "Sheep love bread, even if it’s not great for them. What a mean way to travel!");
    }
    
    public static void translateSounds(TranslationBuilder texts) {
        BiConsumer<String, String> subtitles = (key, value) -> texts.add(
                "subtitles.alpinewhispers." + key, value
        );
        
        subtitles.accept("winter_magic", "Winter Magic plays");
        subtitles.accept("winter_magic_sound_event", "Winter Magic plays");
        subtitles.accept("reindeer_ambient", "Reindeer snorts");
        subtitles.accept("reindeer_hurt", "Reindeer hurt");
        subtitles.accept("reindeer_death", "Reindeer dies");
        texts.add("jukebox_song.alpinewhispers.winter_magic", "Marco's Favorites - Winter Magic");
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
        texts.add(ObjectRegistry.FROZEN_DIRT.get(), "Frozen Dirt");
        texts.add(ObjectRegistry.CANDLE_WREATH.get(), "Candle Wreath");
        texts.add(ObjectRegistry.FAIRY_LIGHTS.get(), "Fairy Lights");
        texts.add(ObjectRegistry.GARLAND.get(), "Garland");
        texts.add(ObjectRegistry.WALL_WREATH.get(), "Wall Wreath");
        texts.add(ObjectRegistry.STAR_TOPPER.get(), "Star Topper");
        texts.add(ObjectRegistry.TREE_BAUBLES.get(), "Tree Baubles");
        texts.add(ObjectRegistry.SNOW_GLOBE.get(), "Snow Globe");
        texts.add(ObjectRegistry.HOMESPUN_WOOL.get(), "Homespun Wool");
        texts.add(ObjectRegistry.HOMESPUN_CARPET.get(), "Homespun Carpet");
    }

    public static void translateArollaPineFurniture(TranslationBuilder texts) {
        texts.add(ObjectRegistry.AROLLA_PINE_BED.get(), "Arolla Pine Bed");
        texts.add(ObjectRegistry.AROLLA_PINE_SOFA.get(), "Arolla Pine Sofa");
        texts.add(ObjectRegistry.AROLLA_PINE_DRESSER.get(), "Arolla Pine Dresser");
        texts.add(ObjectRegistry.AROLLA_PINE_SINK.get(), "Arolla Pine Sink");
        texts.add(ObjectRegistry.AROLLA_PINE_WASHBASIN.get(), "Arolla Pine Washbasin");
        texts.add(ObjectRegistry.AROLLA_PINE_CABINET.get(), "Arolla Pine Cabinet");
        texts.add(ObjectRegistry.AROLLA_PINE_COOKING_AISLE.get(), "Arolla Pine Cooking Aisle");
        texts.add(ObjectRegistry.AROLLA_PINE_WALL_CABINET.get(), "Arolla Pine Wall Cabinet");
        texts.add(ObjectRegistry.AROLLA_PINE_SMOKER.get(), "Arolla Pine Smoker");
        texts.add(ObjectRegistry.AROLLA_PINE_WARDROBE.get(), "Arolla Pine Wardrobe");
        texts.add(ObjectRegistry.AROLLA_PINE_BATHTUB.get(), "Arolla Pine Bathtub");
        texts.add(ObjectRegistry.AROLLA_PINE_PRIVY.get(), "Arolla Pine Privy");
        texts.add(ObjectRegistry.AROLLA_PINE_TABLE.get(), "Arolla Pine Table");
        texts.add(ObjectRegistry.AROLLA_PINE_CHAIR.get(), "Arolla Pine Chair");
        texts.add(ObjectRegistry.FIREPLACE_CORNICE.get(), "Fireplace Cornice");
    }

    public static void translatePlants(TranslationBuilder texts) {
        texts.add(ObjectRegistry.SNOW_GENTIAN.get(), "Snow Gentian");
        texts.add(ObjectRegistry.POTTED_SNOW_GENTIAN.get(), "Potted Snow Gentian");
        texts.add(ObjectRegistry.CHRISTMAS_ROSE.get(), "Christmas Rose");
        texts.add(ObjectRegistry.POTTED_CHRISTMAS_ROSE.get(), "Potted Christmas Rose");
        texts.add(ObjectRegistry.HOARFROST_GRASS.get(), "Hoarfrost Grass");
        texts.add(ObjectRegistry.TALL_HOARFROST_GRASS.get(), "Tall Hoarfrost Grass");
        texts.add(ObjectRegistry.ICICLES.get(), "Icicles");
    }

    public static void translateItems(TranslationBuilder texts) {
        texts.add(ObjectRegistry.WINTER_MAGIC_MUSIC_DISC.get(), "Music Disc");
        texts.add(ObjectRegistry.REINDEER_SPAWN_EGG.get(), "Reindeer Spawn Egg");
        texts.add(ObjectRegistry.ALPINE_SHEEP_SPAWN_EGG.get(), "Alpine Sheep Spawn Egg");
        texts.add(ObjectRegistry.REINDEER.get(), "Reindeer");
        texts.add(ObjectRegistry.COOKED_REINDEER.get(), "Cooked Reindeer");
        texts.add(ObjectRegistry.COOKED_REINDEER_DISH.get(), "Cooked Reindeer Dish");
        texts.add(ObjectRegistry.WINTER_HAT.get(), "Winter Hat");
        texts.add(ObjectRegistry.BREAD_ON_A_STICK.get(), "Bread on a Stick");
    }

    public static void translateEntities(TranslationBuilder texts) {
        texts.add(EntityTypeRegistry.ALPINE_SHEEP_ENTITY.get(), "Alpine Sheep");
        texts.add(EntityTypeRegistry.REINDEER_ENTITY.get(), "Reindeer");
    }

}
