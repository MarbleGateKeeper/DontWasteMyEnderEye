package love.marblegate.homingendereye.mixin;

import love.marblegate.homingendereye.HomingEnderEye;
import love.marblegate.homingendereye.misc.Configuration;
import love.marblegate.homingendereye.misc.EnderEyeDestroyData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(EnderEyeItem.class)
public class MixinEnderEyeItem {

    @Inject(method ="use",
            at= @At(value ="INVOKE", target = "Lnet/minecraft/world/entity/projectile/EyeOfEnder;signalTo(Lnet/minecraft/core/BlockPos;)V", shift = At.Shift.AFTER))
    public void captureThrowSource(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
        // Only Signal Cache to Remember Who just throw an ender eye
        // Only works if the mode is running on Individual Mode
        if(p_77659_1_ instanceof ServerLevel && p_77659_1_.dimension().equals(Level.OVERWORLD) ){
            if(Configuration.INDIVIDUAL_MODE.get()){
                HomingEnderEye.EYE_THROW_CACHE.putThrowRecord(p_77659_2_.getUUID());
            }
        }

    }

    @ModifyArg(method="use",
            at=@At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), index = 0)
    public Entity captureEnderEyeBreak(Entity entity){
        // Pre Handling Ender Eye Break
        if(entity.level instanceof ServerLevel && entity.level.dimension().equals(Level.OVERWORLD)){
            if(!((AccessorEyeOfEnderEntity) (Object) (EyeOfEnder) entity).getSurviveAfterDeath()){
                if(Math.random()<Configuration.WARPING_PROBABILITY.get()){
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
            }
            // Once a eye is thrown, remove a record
            if(Configuration.INDIVIDUAL_MODE.get()){
                HomingEnderEye.EYE_THROW_CACHE.retrieveThrowerRecord();
            }
        }
        return entity;
    }

}
