package plus.dragons.homingendereye.fabric.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.homingendereye.misc.ScanningForEndPortal;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends PlayerEntity {

    @Shadow public abstract ServerWorld getServerWorld();

    public MixinServerPlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void init(CallbackInfo info) {
        ScanningForEndPortal.scan((ServerPlayerEntity) (Object)this,getServerWorld());
    }
}
