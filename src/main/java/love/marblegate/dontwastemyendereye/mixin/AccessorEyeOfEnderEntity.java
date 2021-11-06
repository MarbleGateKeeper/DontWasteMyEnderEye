package love.marblegate.dontwastemyendereye.mixin;

import net.minecraft.entity.projectile.EyeOfEnderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EyeOfEnderEntity.class)
public interface AccessorEyeOfEnderEntity {
    @Accessor
    boolean getSurviveAfterDeath();
}
