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
        translateHud(texts);
        translateConfig(texts);
        translateSounds(texts);
        translateAlpineGneiss(texts);
        translateArollaPine(texts);
        translateDecor(texts);
        translateArollaPineFurniture(texts);
        translatePlants(texts);
        translateItems(texts);
        translateEntities(texts);
        translateAdvancements(texts);
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
    
    public static void translateHud(TranslationBuilder texts) {
        texts.add("hud.alpinewhispers.privy.grinding", "Grinding");
        texts.add("hud.alpinewhispers.privy.items_left", "%s Items Left");
        texts.add("hud.alpinewhispers.privy.in_privy", "In the Privy");
        texts.add("hud.alpinewhispers.privy.hint", "§e[Right-Click]§r with Food: Grind");
        texts.add("hud.alpinewhispers.privy.hint_take", "§e[Shift Right-Click]§r with an Empty Hand: Take Out");
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

    public static void translateAdvancements(TranslationBuilder texts) {
        texts.add("advancement.alpinewhispers.root", "A Whiff of the Alps");
        texts.add("advancement.alpinewhispers.root.desc", "Your journey into the alpine wilderness begins with a simple seed.");
        texts.add("advancement.alpinewhispers.find_grove", "Among Snow and Pines");
        texts.add("advancement.alpinewhispers.find_grove.desc", "Discover the cold, quiet beauty of the alpine grove.");
        texts.add("advancement.alpinewhispers.get_arolla_pine_log", "Heart of the Stone Pine");
        texts.add("advancement.alpinewhispers.get_arolla_pine_log.desc", "Obtain the fragrant wood of the legendary Arolla Pine.");
        texts.add("advancement.alpinewhispers.arolla_pine_smoker", "Aroma of the Mountains");
        texts.add("advancement.alpinewhispers.arolla_pine_smoker.desc", "Build a smoker crafted from Arolla Pine and fill the air with alpine flavor.");
        texts.add("advancement.alpinewhispers.get_cooked_reindeer_dish", "Warmth on a Winter's Night");
        texts.add("advancement.alpinewhispers.get_cooked_reindeer_dish.desc", "A hearty alpine meal to keep the cold at bay.");
        texts.add("advancement.alpinewhispers.use_flint_and_steel_on_candle_wreath", "Light of Tradition");
        texts.add("advancement.alpinewhispers.use_flint_and_steel_on_candle_wreath.desc", "Ignite a candle wreath to celebrate alpine festivities.");
        texts.add("advancements.alpinewhispers.feed_alpine_sheep.title", "Mean Little Snack");
        texts.add("advancements.alpinewhispers.feed_alpine_sheep.description", "Sheep love bread, but it is not good for them at all. You give it to them anyway.");
        texts.add("advancements.alpinewhispers.shear_alpine_sheep.title", "Homespun Craft");
        texts.add("advancements.alpinewhispers.shear_alpine_sheep.description", "Collect homespun wool from an Alpine Sheep, with a gentle snip.");
        texts.add("advancements.alpinewhispers.spyglass_at_reindeer.title", "Majesty in the Snow");
        texts.add("advancements.alpinewhispers.spyglass_at_reindeer.description", "Observe a reindeer in its natural frosty habitat.");
    }

    private void translateConfig(TranslationBuilder texts) {
        texts.add("text.autoconfig.alpinewhispers.title", "Alpine Whispers");
        texts.add("alpinewhispers.configuration.title", "Alpine Whispers");
        texts.add("text.autoconfig.alpinewhispers.option.starTopper", "Star Topper");
        texts.add("alpinewhispers.configuration.starTopper", "Star Topper");
        texts.add("alpinewhispers.configuration.starTopper.button", "Star Topper...");
        texts.add("alpinewhispers.configuration.starTopper.tooltip", "Light waves and chimes of the star on top of a tree");
        texts.add("text.autoconfig.alpinewhispers.option.starTopper.treeLights", "Tree Lights");
        texts.add("text.autoconfig.alpinewhispers.option.starTopper.treeLights.@Tooltip", "A star on top of a decorated tree sends waves of sparkles through its lights at night");
        texts.add("alpinewhispers.configuration.treeLights", "Tree Lights");
        texts.add("alpinewhispers.configuration.treeLights.tooltip", "A star on top of a decorated tree sends waves of sparkles through its lights at night");
        texts.add("text.autoconfig.alpinewhispers.option.starTopper.chime", "Chime");
        texts.add("text.autoconfig.alpinewhispers.option.starTopper.chime.@Tooltip", "A soft chime plays with the light waves");
        texts.add("alpinewhispers.configuration.chime", "Chime");
        texts.add("alpinewhispers.configuration.chime.tooltip", "A soft chime plays with the light waves");
        texts.add("subtitles.alpinewhispers.star_topper_chime", "Star Topper chimes");
        texts.add("alpinewhispers.configuration.section.alpinewhispers.common.toml", "Alpine Whispers");
        texts.add("alpinewhispers.configuration.section.alpinewhispers.common.toml.title", "Alpine Whispers Settings");
        texts.add("alpinewhispers.configuration.fog.button", "Snow Fog...");
        texts.add("alpinewhispers.configuration.fog.tooltip", "Mist in snowy biomes");
        texts.add("alpinewhispers.configuration.weather.button", "Grove Weather...");
        texts.add("alpinewhispers.configuration.weather.tooltip", "Snow, avalanches and sounds in Groves");
        texts.add("alpinewhispers.configuration.bathtub.button", "Bathtub...");
        texts.add("alpinewhispers.configuration.bathtub.tooltip", "Filling, effects and steam");
        texts.add("alpinewhispers.configuration.infoOverlay.button", "Info Overlay...");
        texts.add("alpinewhispers.configuration.infoOverlay.tooltip", "Info overlays when looking at blocks");
        texts.add("text.autoconfig.alpinewhispers.option.fog", "Snow Fog");
        texts.add("alpinewhispers.configuration.fog", "Snow Fog");
        texts.add("text.autoconfig.alpinewhispers.option.fog.enabled", "Snow Fog");
        texts.add("text.autoconfig.alpinewhispers.option.fog.enabled.@Tooltip", "Mist in snowy biomes, thicker in the morning, at night, in snowfall and in valleys");
        texts.add("alpinewhispers.configuration.enabled", "Snow Fog");
        texts.add("alpinewhispers.configuration.enabled.tooltip", "Mist in snowy biomes, thicker in the morning, at night, in snowfall and in valleys");
        texts.add("text.autoconfig.alpinewhispers.option.fog.strengthPercent", "Fog Strength");
        texts.add("text.autoconfig.alpinewhispers.option.fog.strengthPercent.@Tooltip", "Overall fog strength in percent, 100 is the default, 200 doubles the thickening");
        texts.add("alpinewhispers.configuration.strengthPercent", "Fog Strength");
        texts.add("alpinewhispers.configuration.strengthPercent.tooltip", "Overall fog strength in percent, 100 is the default, 200 doubles the thickening");
        texts.add("text.autoconfig.alpinewhispers.option.fog.startPercent", "Fog Start");
        texts.add("text.autoconfig.alpinewhispers.option.fog.startPercent.@Tooltip", "Where the fog starts, in % of the render distance");
        texts.add("alpinewhispers.configuration.startPercent", "Fog Start");
        texts.add("alpinewhispers.configuration.startPercent.tooltip", "Where the fog starts, in % of the render distance");
        texts.add("text.autoconfig.alpinewhispers.option.fog.hazeEndPercent", "Mountain Haze");
        texts.add("text.autoconfig.alpinewhispers.option.fog.hazeEndPercent.@Tooltip", "How far you can see in the light haze that always hangs over snowy biomes, in % of the render distance");
        texts.add("alpinewhispers.configuration.hazeEndPercent", "Mountain Haze");
        texts.add("alpinewhispers.configuration.hazeEndPercent.tooltip", "How far you can see in the light haze that always hangs over snowy biomes, in % of the render distance");
        texts.add("text.autoconfig.alpinewhispers.option.fog.morning", "Morning Mist");
        texts.add("text.autoconfig.alpinewhispers.option.fog.morning.@Tooltip", "Mist from late night until shortly after sunrise");
        texts.add("alpinewhispers.configuration.morning", "Morning Mist");
        texts.add("alpinewhispers.configuration.morning.tooltip", "Mist from late night until shortly after sunrise");
        texts.add("text.autoconfig.alpinewhispers.option.fog.morningEndPercent", "Morning Mist View");
        texts.add("text.autoconfig.alpinewhispers.option.fog.morningEndPercent.@Tooltip", "How far you can see in morning mist, in % of the render distance");
        texts.add("alpinewhispers.configuration.morningEndPercent", "Morning Mist View");
        texts.add("alpinewhispers.configuration.morningEndPercent.tooltip", "How far you can see in morning mist, in % of the render distance");
        texts.add("text.autoconfig.alpinewhispers.option.fog.night", "Night Mist");
        texts.add("text.autoconfig.alpinewhispers.option.fog.night.@Tooltip", "Cold mist settles in after dusk");
        texts.add("alpinewhispers.configuration.night", "Night Mist");
        texts.add("alpinewhispers.configuration.night.tooltip", "Cold mist settles in after dusk");
        texts.add("text.autoconfig.alpinewhispers.option.fog.nightEndPercent", "Night Mist View");
        texts.add("text.autoconfig.alpinewhispers.option.fog.nightEndPercent.@Tooltip", "How far you can see at night, in % of the render distance");
        texts.add("alpinewhispers.configuration.nightEndPercent", "Night Mist View");
        texts.add("alpinewhispers.configuration.nightEndPercent.tooltip", "How far you can see at night, in % of the render distance");
        texts.add("text.autoconfig.alpinewhispers.option.fog.snowfall", "Snowfall Fog");
        texts.add("text.autoconfig.alpinewhispers.option.fog.snowfall.@Tooltip", "Snowfall and blizzards swallow the view");
        texts.add("alpinewhispers.configuration.snowfall", "Snowfall Fog");
        texts.add("alpinewhispers.configuration.snowfall.tooltip", "Snowfall and blizzards swallow the view");
        texts.add("text.autoconfig.alpinewhispers.option.fog.snowfallEndPercent", "Snowfall View");
        texts.add("text.autoconfig.alpinewhispers.option.fog.snowfallEndPercent.@Tooltip", "How far you can see in snowfall, in % of the render distance");
        texts.add("alpinewhispers.configuration.snowfallEndPercent", "Snowfall View");
        texts.add("alpinewhispers.configuration.snowfallEndPercent.tooltip", "How far you can see in snowfall, in % of the render distance");
        texts.add("text.autoconfig.alpinewhispers.option.fog.blizzardEndPercent", "Blizzard View");
        texts.add("text.autoconfig.alpinewhispers.option.fog.blizzardEndPercent.@Tooltip", "How far you can see in a blizzard during thunderstorms, in % of the render distance");
        texts.add("alpinewhispers.configuration.blizzardEndPercent", "Blizzard View");
        texts.add("alpinewhispers.configuration.blizzardEndPercent.tooltip", "How far you can see in a blizzard during thunderstorms, in % of the render distance");
        texts.add("text.autoconfig.alpinewhispers.option.fog.hollows", "Valley Fog");
        texts.add("text.autoconfig.alpinewhispers.option.fog.hollows.@Tooltip", "Thick mist gathers in valleys and hollows at night and in the morning");
        texts.add("alpinewhispers.configuration.hollows", "Valley Fog");
        texts.add("alpinewhispers.configuration.hollows.tooltip", "Thick mist gathers in valleys and hollows at night and in the morning");
        texts.add("text.autoconfig.alpinewhispers.option.fog.hollowEndPercent", "Valley Fog View");
        texts.add("text.autoconfig.alpinewhispers.option.fog.hollowEndPercent.@Tooltip", "How far you can see in a misty valley, in % of the render distance");
        texts.add("alpinewhispers.configuration.hollowEndPercent", "Valley Fog View");
        texts.add("alpinewhispers.configuration.hollowEndPercent.tooltip", "How far you can see in a misty valley, in % of the render distance");
        texts.add("text.autoconfig.alpinewhispers.option.fog.groveThickeningPercent", "Grove Thickening");
        texts.add("text.autoconfig.alpinewhispers.option.fog.groveThickeningPercent.@Tooltip", "How much thicker the fog is in Groves, in percent");
        texts.add("alpinewhispers.configuration.groveThickeningPercent", "Grove Thickening");
        texts.add("alpinewhispers.configuration.groveThickeningPercent.tooltip", "How much thicker the fog is in Groves, in percent");
        texts.add("text.autoconfig.alpinewhispers.option.weather", "Grove Weather");
        texts.add("alpinewhispers.configuration.weather", "Grove Weather");
        texts.add("text.autoconfig.alpinewhispers.option.weather.groveSnow", "Drifting Snow");
        texts.add("text.autoconfig.alpinewhispers.option.weather.groveSnow.@Tooltip", "Gentle snow drifts through Groves in clear weather");
        texts.add("alpinewhispers.configuration.groveSnow", "Drifting Snow");
        texts.add("alpinewhispers.configuration.groveSnow.tooltip", "Gentle snow drifts through Groves in clear weather");
        texts.add("text.autoconfig.alpinewhispers.option.weather.avalanches", "Avalanches");
        texts.add("text.autoconfig.alpinewhispers.option.weather.avalanches.@Tooltip", "Snow slides down steep slopes in Groves");
        texts.add("alpinewhispers.configuration.avalanches", "Avalanches");
        texts.add("alpinewhispers.configuration.avalanches.tooltip", "Snow slides down steep slopes in Groves");
        texts.add("text.autoconfig.alpinewhispers.option.weather.groveAmbience", "Grove Ambience");
        texts.add("text.autoconfig.alpinewhispers.option.weather.groveAmbience.@Tooltip", "Quiet wind and distant avalanche sounds in Groves");
        texts.add("alpinewhispers.configuration.groveAmbience", "Grove Ambience");
        texts.add("alpinewhispers.configuration.groveAmbience.tooltip", "Quiet wind and distant avalanche sounds in Groves");
        texts.add("text.autoconfig.alpinewhispers.option.bathtub", "Bathtub");
        texts.add("alpinewhispers.configuration.bathtub", "Bathtub");
        texts.add("text.autoconfig.alpinewhispers.option.bathtub.fillSeconds", "Fill Time");
        texts.add("text.autoconfig.alpinewhispers.option.bathtub.fillSeconds.@Tooltip", "How long a bathtub takes to fill, in seconds");
        texts.add("alpinewhispers.configuration.fillSeconds", "Fill Time");
        texts.add("alpinewhispers.configuration.fillSeconds.tooltip", "How long a bathtub takes to fill, in seconds");
        texts.add("text.autoconfig.alpinewhispers.option.bathtub.cleansesEffects", "Washes Off Effects");
        texts.add("text.autoconfig.alpinewhispers.option.bathtub.cleansesEffects.@Tooltip", "A full bathtub washes off harmful effects");
        texts.add("alpinewhispers.configuration.cleansesEffects", "Washes Off Effects");
        texts.add("alpinewhispers.configuration.cleansesEffects.tooltip", "A full bathtub washes off harmful effects");
        texts.add("text.autoconfig.alpinewhispers.option.bathtub.drownsZombies", "Zombies Turn Into Drowned");
        texts.add("text.autoconfig.alpinewhispers.option.bathtub.drownsZombies.@Tooltip", "Zombies in a full bathtub turn into Drowned");
        texts.add("alpinewhispers.configuration.drownsZombies", "Zombies Turn Into Drowned");
        texts.add("alpinewhispers.configuration.drownsZombies.tooltip", "Zombies in a full bathtub turn into Drowned");
        texts.add("text.autoconfig.alpinewhispers.option.bathtub.steam", "Steam");
        texts.add("text.autoconfig.alpinewhispers.option.bathtub.steam.@Tooltip", "Warm bath water steams");
        texts.add("alpinewhispers.configuration.steam", "Steam");
        texts.add("alpinewhispers.configuration.steam.tooltip", "Warm bath water steams");
        texts.add("text.autoconfig.alpinewhispers.option.infoOverlay", "Info Overlay");
        texts.add("alpinewhispers.configuration.infoOverlay", "Info Overlay");
        texts.add("text.autoconfig.alpinewhispers.option.infoOverlay.showPrivyInfo", "Privy Info");
        texts.add("text.autoconfig.alpinewhispers.option.infoOverlay.showPrivyInfo.@Tooltip", "Info overlay when looking at a privy");
        texts.add("alpinewhispers.configuration.showPrivyInfo", "Privy Info");
        texts.add("alpinewhispers.configuration.showPrivyInfo.tooltip", "Info overlay when looking at a privy");
    }
}
