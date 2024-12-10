package net.sixik.sdmeventslab.events.endConditions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.sixik.sdmeventslab.events.EventBase;

public class EventEndCondition {

    protected EventBase eventBase;

    public EventEndCondition setEventBase(EventBase eventBase) {
        this.eventBase = eventBase;
        return this;
    }

    public boolean canEndGlobal(MinecraftServer server) {
        return false;
    }

    public boolean canEndLocal(ServerPlayer player) {
        return false;
    }


    protected final long getDay(MinecraftServer server) {
        return server.overworld().getDayTime() / 24000;
    }
}
