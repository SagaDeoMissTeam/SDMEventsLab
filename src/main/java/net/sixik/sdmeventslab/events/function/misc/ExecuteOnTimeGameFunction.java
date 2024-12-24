package net.sixik.sdmeventslab.events.function.misc;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.sixik.sdmeventslab.events.function.EventFunction;

import java.util.function.BiConsumer;

public class ExecuteOnTimeGameFunction extends EventFunction {

    protected final long time;
    protected final BiConsumer<ServerPlayer, MinecraftServer> consumer;

    public ExecuteOnTimeGameFunction(long time, BiConsumer<ServerPlayer, MinecraftServer> consumer) {
        this.time = time;
        this.consumer = consumer;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if(event.player.level().getDayTime() == time && event.player instanceof ServerPlayer player) {
            consumer.accept(player, player.server);
        }
    }
}
