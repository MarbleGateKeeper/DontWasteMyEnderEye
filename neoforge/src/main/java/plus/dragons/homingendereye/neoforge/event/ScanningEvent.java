package plus.dragons.homingendereye.neoforge.event;


import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import plus.dragons.homingendereye.misc.ScanningForEndPortal;

@EventBusSubscriber
public class ScanningEvent {
    @SubscribeEvent
    public static void scanningForFrame(PlayerTickEvent.Pre event){
        if(!event.getEntity().getWorld().isClient){
            ScanningForEndPortal.scan((ServerPlayerEntity) event.getEntity(),(ServerWorld) event.getEntity().getWorld());
        }
    }
}
