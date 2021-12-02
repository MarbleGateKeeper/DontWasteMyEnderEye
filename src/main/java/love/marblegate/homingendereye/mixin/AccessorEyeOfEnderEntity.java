package love.marblegate.homingendereye.mixin;

import net.minecraft.world.entity.projectile.EyeOfEnder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EyeOfEnder.class)
public interface AccessorEyeOfEnderEntity {
    @Accessor
    boolean getSurviveAfterDeath();
}
