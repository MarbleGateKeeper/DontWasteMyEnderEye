package plus.dragons.homingendereye.forge;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import plus.dragons.homingendereye.HomingEnderEye;
import net.minecraftforge.fml.common.Mod;
import plus.dragons.homingendereye.forge.misc.ConfigurationForge;

@Mod(HomingEnderEye.MOD_ID)
public class HomingEnderEyeForge {

    public HomingEnderEyeForge() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigurationForge.MOD_CONFIG);

    }

}
