package plus.dragons.homingendereye.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import plus.dragons.homingendereye.HomingEnderEye;
import net.fabricmc.api.ModInitializer;
import plus.dragons.homingendereye.fabric.misc.ConfigurationFabric;
import plus.dragons.homingendereye.misc.EyeThrowCache;

public class HomingEnderEyeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register((server -> {
            if(HomingEnderEye.EYE_THROW_CACHE == null){
                HomingEnderEye.EYE_THROW_CACHE = new EyeThrowCache();
            }
        }));

        AutoConfig.register(ConfigurationFabric.class, JanksonConfigSerializer::new);
    }
}
