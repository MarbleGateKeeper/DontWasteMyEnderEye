package love.marblegate.homing_ender_eye.mixin;

import net.minecraft.entity.projectile.EyeOfEnderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EyeOfEnderEntity.class)
public interface AccessorEyeOfEnderEntity {
    @Accessor
    boolean getSurviveAfterDeath();
}
