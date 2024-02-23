package plus.dragons.homingendereye.neoforge;

import plus.dragons.homingendereye.neoforge.misc.ConfigurationForge;

public class ConfigurationImpl {
    public static boolean isIndividualMode() {
        return ConfigurationForge.INDIVIDUAL_MODE.get();
    }

    public static int getScanningRate() {
        return ConfigurationForge.SCANNING_RATE.get();
    }

    public static int getScanningRadius() {
        return ConfigurationForge.SCANNING_RADIUS.get();
    }

    public static double getWarpingProbability() {
        return ConfigurationForge.WARPING_PROBABILITY.get();
    }
}
