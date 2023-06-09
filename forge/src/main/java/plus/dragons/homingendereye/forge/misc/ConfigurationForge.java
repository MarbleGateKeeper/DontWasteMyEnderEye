package plus.dragons.homingendereye.forge.misc;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigurationForge {
    public static final ForgeConfigSpec MOD_CONFIG;

    public static ForgeConfigSpec.BooleanValue INDIVIDUAL_MODE;
    public static ForgeConfigSpec.IntValue SCANNING_RATE;
    public static ForgeConfigSpec.IntValue SCANNING_RADIUS;
    public static ForgeConfigSpec.DoubleValue WARPING_PROBABILITY;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("general");
        INDIVIDUAL_MODE = builder.comment("Should Ender Eye Destroy Event Only Belongs to Specific Player?").define("INDIVIDUAL_MODE", false);
        SCANNING_RATE = builder.comment("Scanning interval for nearby End Portal Frame. The unit is tick.").defineInRange("SCANNING_RATE", 300, 1, Integer.MAX_VALUE);
        SCANNING_RADIUS = builder.comment("Scanning Radius for nearby End Portal Frame.").defineInRange("SCANNING_RATE", 30, 1, 160);
        WARPING_PROBABILITY = builder.comment("The Probability of broken ender eye warping").defineInRange("WARPING_PROBABILITY", 1.0, 0, 1.0);
        builder.pop();

        MOD_CONFIG = builder.build();
    }
}
