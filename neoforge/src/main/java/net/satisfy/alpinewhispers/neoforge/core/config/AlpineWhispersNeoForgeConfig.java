package net.satisfy.alpinewhispers.neoforge.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.satisfy.alpinewhispers.core.config.AlpineWhispersConfig;

public final class AlpineWhispersNeoForgeConfig {
    private static final ModConfigSpec.BooleanValue FOG_ENABLED;
    private static final ModConfigSpec.IntValue FOG_STRENGTH;
    private static final ModConfigSpec.IntValue FOG_START;
    private static final ModConfigSpec.IntValue FOG_HAZE_END;
    private static final ModConfigSpec.BooleanValue FOG_MORNING;
    private static final ModConfigSpec.IntValue FOG_MORNING_END;
    private static final ModConfigSpec.BooleanValue FOG_NIGHT;
    private static final ModConfigSpec.IntValue FOG_NIGHT_END;
    private static final ModConfigSpec.BooleanValue FOG_SNOWFALL;
    private static final ModConfigSpec.IntValue FOG_SNOWFALL_END;
    private static final ModConfigSpec.IntValue FOG_BLIZZARD_END;
    private static final ModConfigSpec.BooleanValue FOG_HOLLOWS;
    private static final ModConfigSpec.IntValue FOG_HOLLOW_END;
    private static final ModConfigSpec.IntValue FOG_GROVE_THICKENING;
    private static final ModConfigSpec.BooleanValue GROVE_SNOW;
    private static final ModConfigSpec.BooleanValue AVALANCHES;
    private static final ModConfigSpec.BooleanValue GROVE_AMBIENCE;
    private static final ModConfigSpec.IntValue BATHTUB_FILL_SECONDS;
    private static final ModConfigSpec.BooleanValue BATHTUB_CLEANSES_EFFECTS;
    private static final ModConfigSpec.BooleanValue BATHTUB_DROWNS_ZOMBIES;
    private static final ModConfigSpec.BooleanValue BATHTUB_STEAM;
    private static final ModConfigSpec.BooleanValue STAR_TREE_LIGHTS;
    private static final ModConfigSpec.BooleanValue STAR_CHIME;
    private static final ModConfigSpec.BooleanValue SHOW_PRIVY_INFO;
    public static final ModConfigSpec COMMON;

    static {
        ModConfigSpec.Builder common = new ModConfigSpec.Builder();
        common.push("fog");
        FOG_ENABLED = common.comment("Mist in snowy biomes, thicker in the morning, at night, in snowfall and in valleys").define("enabled", true);
        FOG_STRENGTH = common.comment("Overall fog strength in percent, 100 is the default, 200 doubles the thickening").defineInRange("strengthPercent", 100, 0, 200);
        FOG_START = common.comment("Where the fog starts, in % of the render distance").defineInRange("startPercent", 0, 0, 100);
        FOG_HAZE_END = common.comment("How far you can see in the light haze that always hangs over snowy biomes, in % of the render distance").defineInRange("hazeEndPercent", 70, 1, 100);
        FOG_MORNING = common.comment("Mist from late night until shortly after sunrise").define("morning", true);
        FOG_MORNING_END = common.comment("How far you can see in morning mist, in % of the render distance").defineInRange("morningEndPercent", 40, 1, 100);
        FOG_NIGHT = common.comment("Cold mist settles in after dusk").define("night", true);
        FOG_NIGHT_END = common.comment("How far you can see at night, in % of the render distance").defineInRange("nightEndPercent", 35, 1, 100);
        FOG_SNOWFALL = common.comment("Snowfall and blizzards swallow the view").define("snowfall", true);
        FOG_SNOWFALL_END = common.comment("How far you can see in snowfall, in % of the render distance").defineInRange("snowfallEndPercent", 30, 1, 100);
        FOG_BLIZZARD_END = common.comment("How far you can see in a blizzard during thunderstorms, in % of the render distance").defineInRange("blizzardEndPercent", 12, 1, 100);
        FOG_HOLLOWS = common.comment("Thick mist gathers in valleys and hollows at night and in the morning").define("hollows", true);
        FOG_HOLLOW_END = common.comment("How far you can see in a misty valley, in % of the render distance").defineInRange("hollowEndPercent", 20, 1, 100);
        FOG_GROVE_THICKENING = common.comment("How much thicker the fog is in Groves, in percent").defineInRange("groveThickeningPercent", 30, 0, 90);
        common.pop();
        common.push("weather");
        GROVE_SNOW = common.comment("Gentle snow drifts through Groves in clear weather").define("groveSnow", true);
        AVALANCHES = common.comment("Snow slides down steep slopes in Groves").define("avalanches", true);
        GROVE_AMBIENCE = common.comment("Quiet wind and distant avalanche sounds in Groves").define("groveAmbience", true);
        common.pop();
        common.push("bathtub");
        BATHTUB_FILL_SECONDS = common.comment("How long a bathtub takes to fill, in seconds").defineInRange("fillSeconds", 45, 1, 300);
        BATHTUB_CLEANSES_EFFECTS = common.comment("A full bathtub washes off harmful effects").define("cleansesEffects", true);
        BATHTUB_DROWNS_ZOMBIES = common.comment("Zombies in a full bathtub turn into Drowned").define("drownsZombies", true);
        BATHTUB_STEAM = common.comment("Warm bath water steams").define("steam", true);
        common.pop();
        common.push("starTopper");
        STAR_TREE_LIGHTS = common.comment("A star on top of a decorated tree sends waves of sparkles through its lights at night").define("treeLights", true);
        STAR_CHIME = common.comment("A soft chime plays with the light waves").define("chime", true);
        common.pop();
        common.push("infoOverlay");
        SHOW_PRIVY_INFO = common.comment("Info overlay when looking at a privy").define("showPrivyInfo", true);
        common.pop();
        COMMON = common.build();
    }

    private AlpineWhispersNeoForgeConfig() {
    }

    public static void applyCommon() {
        int start = FOG_START.get();
        AlpineWhispersConfig.fogEnabled = FOG_ENABLED.get();
        AlpineWhispersConfig.fogStrengthPercent = FOG_STRENGTH.get();
        AlpineWhispersConfig.fogStartPercent = start;
        AlpineWhispersConfig.fogHazeEndPercent = Math.max(FOG_HAZE_END.get(), start + 1);
        AlpineWhispersConfig.fogMorning = FOG_MORNING.get();
        AlpineWhispersConfig.fogMorningEndPercent = Math.max(FOG_MORNING_END.get(), start + 1);
        AlpineWhispersConfig.fogNight = FOG_NIGHT.get();
        AlpineWhispersConfig.fogNightEndPercent = Math.max(FOG_NIGHT_END.get(), start + 1);
        AlpineWhispersConfig.fogSnowfall = FOG_SNOWFALL.get();
        AlpineWhispersConfig.fogSnowfallEndPercent = Math.max(FOG_SNOWFALL_END.get(), start + 1);
        AlpineWhispersConfig.fogBlizzardEndPercent = Math.max(FOG_BLIZZARD_END.get(), start + 1);
        AlpineWhispersConfig.fogHollows = FOG_HOLLOWS.get();
        AlpineWhispersConfig.fogHollowEndPercent = Math.max(FOG_HOLLOW_END.get(), start + 1);
        AlpineWhispersConfig.fogGroveThickeningPercent = FOG_GROVE_THICKENING.get();
        AlpineWhispersConfig.groveSnowEnabled = GROVE_SNOW.get();
        AlpineWhispersConfig.avalanchesEnabled = AVALANCHES.get();
        AlpineWhispersConfig.groveAmbienceEnabled = GROVE_AMBIENCE.get();
        AlpineWhispersConfig.bathtubFillSeconds = BATHTUB_FILL_SECONDS.get();
        AlpineWhispersConfig.bathtubCleansesEffects = BATHTUB_CLEANSES_EFFECTS.get();
        AlpineWhispersConfig.bathtubDrownsZombies = BATHTUB_DROWNS_ZOMBIES.get();
        AlpineWhispersConfig.bathtubSteam = BATHTUB_STEAM.get();
        AlpineWhispersConfig.starTreeLights = STAR_TREE_LIGHTS.get();
        AlpineWhispersConfig.starChime = STAR_CHIME.get();
        AlpineWhispersConfig.showPrivyInfo = SHOW_PRIVY_INFO.get();
    }
}
