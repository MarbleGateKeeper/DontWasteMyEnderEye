package plus.dragons.homingendereye.forge.event;

import plus.dragons.homingendereye.HomingEnderEye;
import plus.dragons.homingendereye.misc.EyeThrowCache;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CreateCacheHandler {
    @SubscribeEvent
    public static void load(LevelEvent.Load event){
        if(HomingEnderEye.EYE_THROW_CACHE == null){
            HomingEnderEye.EYE_THROW_CACHE = new EyeThrowCache();
        }

    }
}
