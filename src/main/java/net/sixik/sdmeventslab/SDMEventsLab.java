package net.sixik.sdmeventslab;

import com.mojang.logging.LogUtils;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.conditions.AbstractEventCondition;
import net.sixik.sdmeventslab.network.SDMEventsLabNetwork;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SDMEventsLab.MODID)
public class SDMEventsLab {
    public static final String MODID = "sdmeventslab";
    private static final Logger LOGGER = LogUtils.getLogger();


    public SDMEventsLab() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        init();

        EnvExecutor.runInEnv(Env.CLIENT, () -> SDMEventsLabClient::init);
    }

    private void init() {
        SDMEventsLabNetwork.init();
    }


}
