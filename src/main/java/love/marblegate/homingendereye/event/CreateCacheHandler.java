package love.marblegate.homingendereye.event;

import love.marblegate.homingendereye.HomingEnderEye;
import love.marblegate.homingendereye.misc.EyeThrowCache;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CreateCacheHandler {
    @SubscribeEvent
    public static void load(WorldEvent.Load event){
        if(HomingEnderEye.EYE_THROW_CACHE == null){
            HomingEnderEye.EYE_THROW_CACHE = new EyeThrowCache();
        }

    }
}
