package plus.dragons.homingendereye.fabric;

import plus.dragons.homingendereye.fabric.misc.ConfigurationFabric;

public class ConfigurationImpl {
    public static boolean isIndividualMode() {
        return ConfigurationFabric.getRealTimeConfig().INDIVIDUAL_MODE;
    }

    public static int getScanningRate() {
        return ConfigurationFabric.getRealTimeConfig().SCANNING_RATE;
    }

    public static int getScanningRadius() {
        return ConfigurationFabric.getRealTimeConfig().SCANNING_RADIUS;
    }

    public static double getWarpingProbability() {
        return ConfigurationFabric.getRealTimeConfig().WARPING_PROBABILITY;
    }
}
