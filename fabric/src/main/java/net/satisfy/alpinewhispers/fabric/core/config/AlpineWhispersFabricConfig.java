package net.satisfy.alpinewhispers.fabric.core.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.satisfy.alpinewhispers.core.config.AlpineWhispersConfig;

@Config(name = "alpinewhispers")
@Config.Gui.Background("minecraft:textures/block/powder_snow.png")
public class AlpineWhispersFabricConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Fog fog = new Fog();

    @ConfigEntry.Gui.CollapsibleObject
    public Weather weather = new Weather();

    @ConfigEntry.Gui.CollapsibleObject
    public Bathtub bathtub = new Bathtub();

    @ConfigEntry.Gui.CollapsibleObject
    public StarTopper starTopper = new StarTopper();

    @ConfigEntry.Gui.CollapsibleObject
    public InfoOverlay infoOverlay = new InfoOverlay();

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public Spawning spawning = new Spawning();

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public Food food = new Food();

    public static class Fog {
        @ConfigEntry.Gui.Tooltip
        public boolean enabled = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int strengthPercent = 100;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int startPercent = 0;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int hazeEndPercent = 70;
        @ConfigEntry.Gui.Tooltip
        public boolean morning = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int morningEndPercent = 40;
        @ConfigEntry.Gui.Tooltip
        public boolean night = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int nightEndPercent = 35;
        @ConfigEntry.Gui.Tooltip
        public boolean snowfall = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int snowfallEndPercent = 30;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int blizzardEndPercent = 12;
        @ConfigEntry.Gui.Tooltip
        public boolean hollows = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int hollowEndPercent = 20;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 90)
        public int groveThickeningPercent = 30;
    }

    public static class Weather {
        @ConfigEntry.Gui.Tooltip
        public boolean groveSnow = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int groveSnowStrength = 15;
        @ConfigEntry.Gui.Tooltip
        public boolean avalanches = true;
        @ConfigEntry.Gui.Tooltip
        public boolean groveAmbience = true;
        @ConfigEntry.Gui.Tooltip
        public boolean fallingNeedles = true;
    }

    public static class Bathtub {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 300)
        public int fillSeconds = 45;
        @ConfigEntry.Gui.Tooltip
        public boolean cleansesEffects = true;
        @ConfigEntry.Gui.Tooltip
        public boolean drownsZombies = true;
        @ConfigEntry.Gui.Tooltip
        public boolean steam = true;
    }

    public static class StarTopper {
        @ConfigEntry.Gui.Tooltip
        public boolean treeLights = true;
        @ConfigEntry.Gui.Tooltip
        public boolean chime = true;
    }

    public static class InfoOverlay {
        @ConfigEntry.Gui.Tooltip
        public boolean showPrivyInfo = true;
    }

    public static class Spawning {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int reindeerWeight = 22;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 16)
        public int reindeerMinGroup = 3;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 16)
        public int reindeerMaxGroup = 5;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int alpineSheepWeight = 18;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 16)
        public int alpineSheepMinGroup = 3;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 16)
        public int alpineSheepMaxGroup = 5;
        @ConfigEntry.Gui.Tooltip
        public boolean replaceVanillaFarmAnimals = true;
    }

    public static class Food {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int reindeerDishNutrition = 6;
        @ConfigEntry.Gui.Tooltip
        public float reindeerDishSaturation = 0.8f;
    }

    @Override
    public void validatePostLoad() {
        food.reindeerDishSaturation = Math.max(0.0f, Math.min(2.0f, food.reindeerDishSaturation));
    }

    public void apply() {
        AlpineWhispersConfig.fogEnabled = fog.enabled;
        AlpineWhispersConfig.fogStrengthPercent = fog.strengthPercent;
        AlpineWhispersConfig.fogStartPercent = fog.startPercent;
        AlpineWhispersConfig.fogHazeEndPercent = Math.max(fog.hazeEndPercent, fog.startPercent + 1);
        AlpineWhispersConfig.fogMorning = fog.morning;
        AlpineWhispersConfig.fogMorningEndPercent = Math.max(fog.morningEndPercent, fog.startPercent + 1);
        AlpineWhispersConfig.fogNight = fog.night;
        AlpineWhispersConfig.fogNightEndPercent = Math.max(fog.nightEndPercent, fog.startPercent + 1);
        AlpineWhispersConfig.fogSnowfall = fog.snowfall;
        AlpineWhispersConfig.fogSnowfallEndPercent = Math.max(fog.snowfallEndPercent, fog.startPercent + 1);
        AlpineWhispersConfig.fogBlizzardEndPercent = Math.max(fog.blizzardEndPercent, fog.startPercent + 1);
        AlpineWhispersConfig.fogHollows = fog.hollows;
        AlpineWhispersConfig.fogHollowEndPercent = Math.max(fog.hollowEndPercent, fog.startPercent + 1);
        AlpineWhispersConfig.fogGroveThickeningPercent = fog.groveThickeningPercent;
        AlpineWhispersConfig.groveSnowEnabled = weather.groveSnow;
        AlpineWhispersConfig.groveSnowStrength = Math.clamp(weather.groveSnowStrength, 1, 100);
        AlpineWhispersConfig.avalanchesEnabled = weather.avalanches;
        AlpineWhispersConfig.groveAmbienceEnabled = weather.groveAmbience;
        AlpineWhispersConfig.fallingNeedlesEnabled = weather.fallingNeedles;
        AlpineWhispersConfig.bathtubFillSeconds = bathtub.fillSeconds;
        AlpineWhispersConfig.bathtubCleansesEffects = bathtub.cleansesEffects;
        AlpineWhispersConfig.bathtubDrownsZombies = bathtub.drownsZombies;
        AlpineWhispersConfig.bathtubSteam = bathtub.steam;
        AlpineWhispersConfig.starTreeLights = starTopper.treeLights;
        AlpineWhispersConfig.starChime = starTopper.chime;
        AlpineWhispersConfig.showPrivyInfo = infoOverlay.showPrivyInfo;
        AlpineWhispersConfig.reindeerSpawnWeight = spawning.reindeerWeight;
        AlpineWhispersConfig.reindeerMinGroup = spawning.reindeerMinGroup;
        AlpineWhispersConfig.reindeerMaxGroup = Math.max(spawning.reindeerMaxGroup, spawning.reindeerMinGroup);
        AlpineWhispersConfig.alpineSheepSpawnWeight = spawning.alpineSheepWeight;
        AlpineWhispersConfig.alpineSheepMinGroup = spawning.alpineSheepMinGroup;
        AlpineWhispersConfig.alpineSheepMaxGroup = Math.max(spawning.alpineSheepMaxGroup, spawning.alpineSheepMinGroup);
        AlpineWhispersConfig.replaceVanillaFarmAnimals = spawning.replaceVanillaFarmAnimals;
        AlpineWhispersConfig.reindeerDishNutrition = food.reindeerDishNutrition;
        AlpineWhispersConfig.reindeerDishSaturation = food.reindeerDishSaturation;
    }
}
