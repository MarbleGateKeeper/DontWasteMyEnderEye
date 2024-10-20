package plus.dragons.homingendereye.neoforge.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;
import plus.dragons.homingendereye.HomingEnderEye;
import plus.dragons.homingendereye.misc.EyeThrowCache;

@EventBusSubscriber
public class CreateCacheHandler {
    @SubscribeEvent
    public static void load(LevelEvent.Load e){
        if(HomingEnderEye.EYE_THROW_CACHE == null){
            HomingEnderEye.EYE_THROW_CACHE = new EyeThrowCache();
        }

    }
}
