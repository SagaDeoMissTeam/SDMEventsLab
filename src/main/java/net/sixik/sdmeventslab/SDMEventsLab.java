package net.sixik.sdmeventslab;

import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerLifecycleEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.EventFunctionManager;
import net.sixik.sdmeventslab.events.EventManager;
import net.sixik.sdmeventslab.events.builder.EventBuilder;
import net.sixik.sdmeventslab.events.builder.EventRenderBuilder;
import net.sixik.sdmeventslab.events.builder.EventFunctionBuilder;
import net.sixik.sdmeventslab.network.SDMEventsLabNetwork;
import net.sixik.sdmeventslab.register.ItemRegister;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SDMEventsLab.MODID)
public class SDMEventsLab {
    public static final String MODID = "sdmeventslab";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static boolean debugMessages = true;

    public SDMEventsLab() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(EventFunctionManager.INSTANCE);

        ItemRegister.register(modEventBus);

        init();

        EventBuilder builder = EventBuilder.createEventID(create("my_test_event"), EventBase.EventSide.GLOBAL);
        builder.addCustomFunction(
                new EventFunctionBuilder().addEventStart(server -> {
                    for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                        player.sendSystemMessage(Component.literal("Event Started!"));
                    }
                }).addEventEnd(event -> {
                    for (ServerPlayer player : event.getPlayerList().getPlayers()) {
                        player.sendSystemMessage(Component.literal("Event ended!"));
                    }
                })
        );


        builder.addRender(
                new EventRenderBuilder()
                        .addLogoRender()
                        .addMoonColor(0.2f,0.3f,0.4f,1f)
                        .addMoonSize(100f)
                        .addSkyColor(0f, 1f, 0f, 1f)
                        .addSunSize(200f)
                        .addSunColor(0,0,1f, 1f)
                        .addWaterColor(0,1f,0)
        );

        builder.addWaterDamageFunction(5f).addSpawnReplaceFunction(EntityType.ZOMBIE, new EntityType[]{EntityType.SLIME});

        EnvExecutor.runInEnv(Env.CLIENT, () -> SDMEventsLabClient::init);
        modEvents();
    }

    private void init() {
        SDMEventsLabNetwork.init();


        LifecycleEvent.SERVER_STARTED.register(s -> {
            Path path = s.getWorldPath(LevelResource.ROOT).resolve("sdmeventslab.data");
            if(!path.toFile().exists()) return;

            try {
                CompoundTag nbt = NbtIo.read(path.toFile());
                if(nbt == null) return;
                EventManager.INSTANCE.deserializeNBT(nbt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        LifecycleEvent.SERVER_LEVEL_SAVE.register(s -> {
            Path path = s.getServer().getWorldPath(LevelResource.ROOT).resolve("sdmeventslab.data");
            try {
                NbtIo.write(EventManager.INSTANCE.serializeNBT(), path.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static ResourceLocation create(String id) {
        return new ResourceLocation(SDMEventsLab.MODID, id);
    }

    private void modEvents() {
        LOGGER.info("Initializing SDMEventsLab events...");

    }


    public static int rgbToInt(float r, float g, float b) {
        // Нормализуем значения (0f–1f → 0–255)
        int red = (int) (r * 255);
        int green = (int) (g * 255);
        int blue = (int) (b * 255);

        // Комбинируем компоненты в формат 0xRRGGBB
        return (red << 16) | (green << 8) | blue;
    }

    public static int rgbToInt(int red, int green, int blue) {
        return (red << 16) | (green << 8) | blue;
    }
}
