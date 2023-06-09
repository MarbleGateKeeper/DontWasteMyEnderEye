package plus.dragons.homingendereye;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class Configuration {

    @ExpectPlatform
    public static boolean isIndividualMode(){
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static int getScanningRate(){
        throw new RuntimeException();
    }

    @ExpectPlatform
    public static int getScanningRadius(){
        throw new RuntimeException();
    }
    @ExpectPlatform
    public static double getWarpingProbability(){
        throw new RuntimeException();
    }
}
