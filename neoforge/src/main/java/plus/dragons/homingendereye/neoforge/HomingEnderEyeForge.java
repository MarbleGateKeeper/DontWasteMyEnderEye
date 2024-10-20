package plus.dragons.homingendereye.neoforge;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import plus.dragons.homingendereye.HomingEnderEye;
import plus.dragons.homingendereye.neoforge.misc.ConfigurationForge;

@Mod(HomingEnderEye.MOD_ID)
public class HomingEnderEyeForge {

    public HomingEnderEyeForge(ModContainer container) {
        container.registerConfig(ModConfig.Type.SERVER, ConfigurationForge.MOD_CONFIG);
    }

}
