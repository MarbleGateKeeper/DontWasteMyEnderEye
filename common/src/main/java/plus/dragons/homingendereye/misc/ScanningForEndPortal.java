package plus.dragons.homingendereye.misc;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import plus.dragons.homingendereye.Configuration;

public class ScanningForEndPortal {

    public static void scan(ServerPlayerEntity player, ServerWorld world){
        if(world!=null && world.getRegistryKey().equals(World.OVERWORLD)){
            if(world.getTimeOfDay() % Configuration.getScanningRate() == 0){
                EnderEyeDestroyData data = EnderEyeDestroyData.get(player.getWorld());
                if(data.getCount(player.getUuid()) > 0){
                    BlockPos center = player.getBlockPos();
                    int offSet = Configuration.getScanningRadius();
                    for(BlockPos blockpos : BlockPos.iterate(center.add(offSet,offSet,offSet),center.add(-offSet,-offSet,-offSet))){
                        BlockState blockstate = world.getBlockState(blockpos);

                        // Scanning for Frame and Filling
                        if (blockstate.isOf(Blocks.END_PORTAL_FRAME) && !blockstate.get(EndPortalFrameBlock.EYE)){
                            data.decreaseCount(player.getUuid());
                            BlockState newBlockState = blockstate.with(EndPortalFrameBlock.EYE, Boolean.TRUE);
                            world.setBlockState(blockpos, newBlockState, 2);
                            world.updateComparators(blockpos, Blocks.END_PORTAL_FRAME);

                            // Check if portal is qualified or not
                            BlockPattern.Result result = EndPortalFrameBlock.getCompletedFramePattern().searchAround(world, blockpos);
                            if (result != null) {
                                BlockPos blockPos2 = result.getFrontTopLeft().add(-3, 0, -3);
                                for(int i = 0; i < 3; ++i) {
                                    for(int j = 0; j < 3; ++j) {
                                        world.setBlockState(blockPos2.add(i, 0, j), Blocks.END_PORTAL.getDefaultState(), 2);
                                    }
                                }
                                break;
                            }

                        }

                        // Check for remaining ender eye
                        if (data.getCount(player.getUuid()) == 0)
                            break;
                    }
                }
            }
        }
    }
}
