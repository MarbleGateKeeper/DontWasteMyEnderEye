package love.marblegate.homingendereye.mixin;

import love.marblegate.homingendereye.HomingEnderEye;
import love.marblegate.homingendereye.misc.Configuration;
import love.marblegate.homingendereye.misc.EnderEyeDestroyData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(EnderEyeItem.class)
public class MixinEnderEyeItem {

    @Inject(method ="use",
            at= @At(value ="INVOKE", target = "Lnet/minecraft/entity/projectile/EyeOfEnderEntity;signalTo(Lnet/minecraft/util/math/BlockPos;)V", shift = At.Shift.AFTER))
    public void captureThrowSource(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_, CallbackInfoReturnable<ActionResult<ItemStack>> cir){
        // Only Signal Cache to Remember Who just throw an ender eye
        // Only works if the mode is running on Individual Mode
        if(p_77659_1_ instanceof ServerWorld && p_77659_1_.dimension().equals(World.OVERWORLD) ){
            if(Configuration.INDIVIDUAL_MODE.get()){
                HomingEnderEye.EYE_THROW_CACHE.putThrowRecord(p_77659_2_.getUUID());
            }
        }

    }

    @ModifyArg(method="use",
            at=@At(value = "INVOKE", target = "Lnet/minecraft/world/World;addFreshEntity(Lnet/minecraft/entity/Entity;)Z"), index = 0)
    public Entity captureEnderEyeBreak(Entity entity){
        // Pre Handling Ender Eye Break
        if(entity.level instanceof ServerWorld && entity.level.dimension().equals(World.OVERWORLD)){
            if(!((AccessorEyeOfEnderEntity) (Object) (EyeOfEnderEntity) entity).getSurviveAfterDeath()){
                EnderEyeDestroyData data = EnderEyeDestroyData.get(entity.level);
                if(Configuration.INDIVIDUAL_MODE.get()){
                    UUID throwerUUID = HomingEnderEye.EYE_THROW_CACHE.peek();
                    if(throwerUUID!=null){
                        data.increaseCount(throwerUUID);
                    }
                } else {
                    data.increaseCount(null);
                }
            }
            // Once a eye is thrown, remove a record
            if(Configuration.INDIVIDUAL_MODE.get()){
                HomingEnderEye.EYE_THROW_CACHE.retrieveThrowerRecord();
            }
        }
        return entity;
    }

}
