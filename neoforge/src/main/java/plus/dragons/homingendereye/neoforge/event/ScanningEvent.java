package plus.dragons.homingendereye.neoforge.event;


import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import plus.dragons.homingendereye.misc.ScanningForEndPortal;

@Mod.EventBusSubscriber
public class ScanningEvent {
    @SubscribeEvent
    public static void scanningForFrame(TickEvent.PlayerTickEvent event){
        if(event.side.isServer() && event.phase.equals(TickEvent.Phase.START)){
            ScanningForEndPortal.scan((ServerPlayerEntity) event.player,(ServerWorld) event.player.getWorld());
        }
    }
}
