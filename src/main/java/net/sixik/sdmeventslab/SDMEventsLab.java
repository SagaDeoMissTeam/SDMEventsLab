package net.sixik.sdmeventslab;

import com.mojang.logging.LogUtils;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.EventManager;
import net.sixik.sdmeventslab.events.conditions.AbstractEventCondition;
import net.sixik.sdmeventslab.network.SDMEventsLabNetwork;
import net.sixik.sdmeventslab.register.EventsRegisters;
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
        EventBase test = new EventBase(new ResourceLocation("test"));
        EventBase test2 = new EventBase(new ResourceLocation("test2"));
        EventBase test3 = new EventBase(new ResourceLocation("test3"));
        test.properties.whiteList.add(test2.getEventID());
        test.properties.backList.add(test3.getEventID());
        EventsRegisters.registerEvent(test);
        EventsRegisters.registerEvent(test2);
        EventsRegisters.registerEvent(test3);

        PlayerEvent.DROP_ITEM.register((player, itemEntity) -> {
            test2.onEventStart(player.getServer());
            EventManager.INSTANCE.checkProperties(player.getServer());
            return EventResult.interrupt(true);
        });

        EnvExecutor.runInEnv(Env.CLIENT, () -> SDMEventsLabClient::init);
    }

    private void init() {
        SDMEventsLabNetwork.init();
    }


}
