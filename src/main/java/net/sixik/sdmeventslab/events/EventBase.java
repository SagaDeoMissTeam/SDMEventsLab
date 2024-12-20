package net.sixik.sdmeventslab.events;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.sixik.sdmeventslab.events.conditions.EventCondition;
import net.sixik.sdmeventslab.events.endConditions.DayEndCondition;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import net.sixik.sdmeventslab.events.function.CustomFunction;
import net.sixik.sdmeventslab.events.function.EventFunction;
import net.sixik.sdmeventslab.events.renders.EventRender;
import net.sixik.sdmeventslab.network.client.SendEndEventS2C;
import net.sixik.sdmeventslab.network.client.SendStartEventS2C;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;

@ZenRegister
@Document("mods/eventslab/EventBase")
@ZenCodeType.Name("mods.eventslab.EventBase")
public class EventBase {

    protected final EventSide eventSide;
    protected final ResourceLocation eventID;
    public EventProperty properties = new EventProperty();
    public EventRenderProperty renderProperty = new EventRenderProperty();
    protected final List<EventCondition> conditions = new ArrayList<>();
    protected final List<EventFunction> functions = new ArrayList<>();
    private final List<EventEndCondition> endConditions = new ArrayList<>();
    private final List<EventRender> eventRenders = new ArrayList<>();

    public final Map<String, ResourceLocation> eventsIcons = new HashMap<>();

    public long dayStart = 0;

    public EventBase(ResourceLocation eventID) {
        this.eventID = eventID;
        this.eventSide = EventSide.GLOBAL; // По умолчанию событие глобальное
    }

    public EventBase(ResourceLocation eventID, EventSide eventSide) {
        this.eventID = eventID;
        this.eventSide = eventSide;
    }

    public ResourceLocation getEventID() {
        return eventID;
    }

    public EventSide getEventSide() {
        return eventSide;
    }

    public List<EventCondition> getConditions() {
        return conditions;
    }

    @Nullable
    public CustomFunction getCustomFunction(String id) {
        for(EventFunction function : functions) {
            if(function instanceof CustomFunction customFunction && Objects.equals(customFunction.getFunctionID(), id))
                return customFunction;
        }
        return null;
    }

    public List<EventFunction> getFunctions() {
        return functions;
    }

    public List<EventRender> getEventRenders() {
        return eventRenders;
    }

    public List<EventEndCondition> getEndConditions() {
        if(endConditions.isEmpty())
            return List.of(new DayEndCondition(1).setEventBase(this));
        return endConditions;
    }

    public void onEventStart(MinecraftServer server) {
        dayStart = server.overworld().getDayTime() / 24000;

        for (EventFunction function : getFunctions()) {
            function.onEventStart(server);

            if(eventSide == EventSide.GLOBAL)
                server.getPlayerList().getPlayers().forEach(function::applyEffectPlayer);
        }


        switch (eventSide) {
            case LOCAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    sendNotify(player);
                }
            }
            case GLOBAL -> new SendStartEventS2C(this.eventID.toString()).sendToAll(server);
        }

    }

    public void sendNotify(ServerPlayer player) {
        new SendStartEventS2C(this.eventID.toString()).sendTo(player);
    }

    public void onEventEnd(MinecraftServer server) {

        for (EventFunction function : getFunctions()) {
            function.onEventEnd(server);

            if(eventSide == EventSide.GLOBAL)
                server.getPlayerList().getPlayers().forEach(function::resetEffectFromPlayers);
        }

        switch (eventSide) {
            case LOCAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    new SendEndEventS2C(this.eventID.toString()).sendTo(player);
                }
            }
            case GLOBAL -> new SendEndEventS2C(this.eventID.toString()).sendToAll(server);
        }
    }
    public void onEventTick(MinecraftServer server) {
        for (EventFunction function : getFunctions())
            function.onEventTick(server);
    }

    @ZenRegister
    @Document("mods/eventslab/EventSide")
    @ZenCodeType.Name("mods.eventslab.EventSide")
    @BracketEnum("eventslab:side")
    public enum EventSide {
        GLOBAL, // Происходит в мире для всех игроков
        LOCAL   // Происходит в мире локально для игрока
    }

    protected static class EventPropertyFunction extends EventFunction {


    }
}
