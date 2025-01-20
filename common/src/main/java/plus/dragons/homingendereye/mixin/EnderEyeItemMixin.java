package plus.dragons.homingendereye.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.homingendereye.HomingEnderEye;
import plus.dragons.homingendereye.Configuration;
import plus.dragons.homingendereye.misc.EnderEyeDestroyData;

import java.util.UUID;

@Mixin(EnderEyeItem.class)
public class EnderEyeItemMixin {

    @Inject(method ="use",
            at= @At(value ="INVOKE", target = "Lnet/minecraft/entity/EyeOfEnderEntity;initTargetPos(Lnet/minecraft/util/math/BlockPos;)V", shift = At.Shift.AFTER))
    public void captureThrowSource(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        // Only Signal Cache to Remember Who just throw an ender eye
        // Only works if the mode is running on Individual Mode
        if(world instanceof ServerWorld && world.getRegistryKey().equals(World.OVERWORLD) ){
            if(Configuration.isIndividualMode()){
                HomingEnderEye.EYE_THROW_CACHE.putThrowRecord(user.getUuid());
            }
        }

    }

    @ModifyArg(method="use",
            at=@At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), index = 0)
    public Entity captureEnderEyeBreak(Entity entity){
        // Pre Handling Ender Eye Break
        if(entity.getWorld() instanceof ServerWorld && entity.getWorld().getRegistryKey().equals(World.OVERWORLD)){
            if(!((EyeOfEnderEntityAccessor) entity).getDropsItem()){
                if(Math.random()<Configuration.getWarpingProbability()){
                    EnderEyeDestroyData data = EnderEyeDestroyData.get(entity.getWorld());
                    if(Configuration.isIndividualMode()){
                        UUID throwerUUID = HomingEnderEye.EYE_THROW_CACHE.peek();
                        if(throwerUUID!=null){
                            data.increaseCount(throwerUUID);
                        }
                    } else {
                        data.increaseCount(null);
                    }
                }
            }
            // Once a eye is thrown, remove a record
            if(Configuration.isIndividualMode()){
                HomingEnderEye.EYE_THROW_CACHE.retrieveThrowerRecord();
            }
        }
        return entity;
    }

}
