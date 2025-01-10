package moe.kotori.explorernature;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(ExplorerNature.MODID)
public class ExplorerNature {

    public static final String MODID = "explorernature";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExplorerNature() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
