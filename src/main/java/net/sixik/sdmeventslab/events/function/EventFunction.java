package net.sixik.sdmeventslab.events.function;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.sixik.sdmeventslab.events.EventBase;

public class EventFunction {

    public EventBase eventBase;
    public FunctionStage functionStage = FunctionStage.TICK;

    public final EventFunction setEvent(EventBase event) {
        this.eventBase = event;
        return this;
    }

    public void onEventStart(MinecraftServer server) {}
    public void onEventEnd(MinecraftServer server) {}
    public void onEventTick(MinecraftServer server) {}

    public void onEntitySpawnEvent(MobSpawnEvent.FinalizeSpawn event) {}
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {}
    public void onLivingDeathEvent(LivingDeathEvent event) {}
    public void onEntityInteractEvent(PlayerInteractEvent.EntityInteract event) {}
    public void applyEffectPlayer(ServerPlayer player) {}

    public void resetEffectFromPlayers(ServerPlayer player) {}


    public enum FunctionStage {
        START,
        END,
        TICK
    }
}
