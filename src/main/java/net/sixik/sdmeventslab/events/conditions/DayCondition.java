package net.sixik.sdmeventslab.events.conditions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class DayCondition extends EventCondition{

    public long day;
    public ConditionType conditionType;

    public DayCondition(long day, ConditionType conditionType) {
        this.day = day;
        this.conditionType = conditionType;
    }

    @Override
    public boolean canExecuteGlobal(MinecraftServer server) {
        switch (conditionType) {
            case LESS -> {
                return getDay(server) < day;
            }
            case GREATER -> {
                return getDay(server) > day;
            }
            case EQUALS -> {
                return getDay(server) == day;
            }
            case EQUALS_LESS -> {
                return getDay(server) <= day;
            }
            case EQUALS_GREATER -> {
                return getDay(server) >= day;
            }
            default -> throw new IllegalArgumentException("Unknown condition type: " + conditionType);
        }
    }

    @Override
    public boolean canExecuteLocal(ServerPlayer player) {
        return canExecuteGlobal(player.server);
    }
}
