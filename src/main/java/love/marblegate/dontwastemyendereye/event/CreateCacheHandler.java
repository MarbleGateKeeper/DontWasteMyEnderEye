package love.marblegate.dontwastemyendereye.event;

import love.marblegate.dontwastemyendereye.DontWatseMyEnderEye;
import love.marblegate.dontwastemyendereye.misc.EyeThrowCache;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CreateCacheHandler {
    @SubscribeEvent
    public static void load(WorldEvent.Load event){
        if(DontWatseMyEnderEye.EYE_THROW_CACHE == null){
            DontWatseMyEnderEye.EYE_THROW_CACHE = new EyeThrowCache();

            DontWatseMyEnderEye.LOGGER.warn("EYE_THROW_CACHE has been created");
        }

    }
}
