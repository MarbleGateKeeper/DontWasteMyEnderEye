package love.marblegate.homing_ender_eye.event;

import love.marblegate.homing_ender_eye.HomingEnderEye;
import love.marblegate.homing_ender_eye.misc.EyeThrowCache;
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
