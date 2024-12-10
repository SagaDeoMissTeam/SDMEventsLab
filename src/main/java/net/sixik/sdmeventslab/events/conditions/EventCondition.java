package net.sixik.sdmeventslab.events.conditions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class EventCondition {

    public boolean canExecuteGlobal(MinecraftServer server) {
        return true;
    }

    public boolean canExecuteLocal(ServerPlayer player) {
        return true;
    }

    public final long getDay(MinecraftServer server) {
        return server.overworld().getDayTime() / 24000;
    }

    public enum ConditionType {
        EQUALS,
        LESS,
        EQUALS_LESS,
        GREATER,
        EQUALS_GREATER
    }
}
