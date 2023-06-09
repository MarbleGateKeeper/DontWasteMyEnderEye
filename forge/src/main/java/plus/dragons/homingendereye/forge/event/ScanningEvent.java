package plus.dragons.homingendereye.forge.event;


import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
