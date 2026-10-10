package net.satisfy.alpinewhispers.core.config;

public final class AlpineWhispersConfig {
    public static boolean fogEnabled = true;
    public static int fogStrengthPercent = 100;
    public static int fogStartPercent = 0;
    public static int fogHazeEndPercent = 70;
    public static boolean fogMorning = true;
    public static int fogMorningEndPercent = 40;
    public static boolean fogNight = true;
    public static int fogNightEndPercent = 35;
    public static boolean fogSnowfall = true;
    public static int fogSnowfallEndPercent = 30;
    public static int fogBlizzardEndPercent = 12;
    public static boolean fogHollows = true;
    public static int fogHollowEndPercent = 20;
    public static int fogGroveThickeningPercent = 30;

    public static boolean groveSnowEnabled = true;
    public static boolean avalanchesEnabled = true;
    public static boolean groveAmbienceEnabled = true;

    public static int bathtubFillSeconds = 45;
    public static boolean bathtubCleansesEffects = true;
    public static boolean bathtubDrownsZombies = true;
    public static boolean bathtubSteam = true;

    public static boolean starTreeLights = true;
    public static boolean starChime = true;

    public static boolean showPrivyInfo = true;

    private AlpineWhispersConfig() {
    }
}
