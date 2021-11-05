package love.marblegate.dontwastemyendereye.misc;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configuration {
    public static final ForgeConfigSpec MOD_CONFIG;

    public static ForgeConfigSpec.BooleanValue INDIVIDUAL_MODE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("general");
        INDIVIDUAL_MODE = builder.comment("Should Ender Eye Destroy Event Only Belongs to Specific Player?").define("INDIVIDUAL_MODE", false);
        builder.pop();

        MOD_CONFIG = builder.build();
    }
}
