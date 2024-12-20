package net.sixik.sdmeventslab.events.conditions;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/conditions/DayCondition")
@ZenCodeType.Name("mods.eventslab.conditions.DayCondition")
public class DayCondition extends EventCondition{

    public long day;
    public ConditionType conditionType;

    @ZenCodeType.Constructor
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
