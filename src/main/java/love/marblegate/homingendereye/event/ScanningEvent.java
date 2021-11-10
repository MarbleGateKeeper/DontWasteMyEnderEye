package love.marblegate.homingendereye.event;

import love.marblegate.homingendereye.misc.Configuration;
import love.marblegate.homingendereye.misc.EnderEyeDestroyData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ScanningEvent {
    @SubscribeEvent
    public static void scanningForFrame(TickEvent.PlayerTickEvent event){
        if(event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.player.level.dimension().equals(World.OVERWORLD)){
            if(event.player.level.getDayTime() % Configuration.SCANNING_RATE.get() == 0){
                EnderEyeDestroyData data = EnderEyeDestroyData.get(event.player.level);
                if(data.getCount(event.player.getUUID()) > 0){
                    PlayerEntity player = event.player;
                    World world = player.level;
                    BlockPos center = player.blockPosition();
                    int offSet = Configuration.SCANNING_RADIUS.get();
                    for(BlockPos blockpos : BlockPos.betweenClosed(center.offset(offSet,offSet,offSet),center.offset(-offSet,-offSet,-offSet))){
                        BlockState blockstate = world.getBlockState(blockpos);

                        // Scanning for Frame and Filling
                        if (blockstate.is(Blocks.END_PORTAL_FRAME) && !blockstate.getValue(EndPortalFrameBlock.HAS_EYE)){
                            data.decreaseCount(player.getUUID());
                            BlockState newBlockState = blockstate.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.TRUE);
                            world.setBlockAndUpdate(blockpos, newBlockState);
                            world.updateNeighbourForOutputSignal(blockpos, Blocks.END_PORTAL_FRAME);
                            // Copy from Item Usage, Seems like it's for playing sound
                            // We do not need this
                            // world.levelEvent(1503, blockpos, 0);

                            // Check if portal is qualified or not
                            BlockPattern.PatternHelper patternhelper = EndPortalFrameBlock.getOrCreatePortalShape().find(world, blockpos);
                            if (patternhelper != null) {
                                BlockPos blockpos1 = patternhelper.getFrontTopLeft().offset(-3, 0, -3);
                                for(int i = 0; i < 3; ++i) {
                                    for(int j = 0; j < 3; ++j) {
                                        world.setBlock(blockpos1.offset(i, 0, j), Blocks.END_PORTAL.defaultBlockState(), 2);
                                    }
                                }
                                break;
                            }

                        }

                        // Check for remaining ender eye
                        if (data.getCount(player.getUUID()) == 0)
                            break;
                    }
                }
            }
        }
    }
}
