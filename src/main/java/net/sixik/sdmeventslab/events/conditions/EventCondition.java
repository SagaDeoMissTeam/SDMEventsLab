package net.sixik.sdmeventslab.events.conditions;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.sixik.sdmeventslab.events.EventBase;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/conditions/EventCondition")
@ZenCodeType.Name("mods.eventslab.conditions.EventCondition")
public class EventCondition {
    public EventBase eventBase;

    public final EventCondition setEvent(EventBase event) {
        this.eventBase = event;
        return this;
    }

    public boolean canExecuteGlobal(MinecraftServer server) {
        return true;
    }

    public boolean canExecuteLocal(ServerPlayer player) {
        return true;
    }

    protected final long getDay(MinecraftServer server) {
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
