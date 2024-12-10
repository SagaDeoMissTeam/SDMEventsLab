package net.sixik.sdmeventslab.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.sixik.sdmeventslab.events.conditions.AbstractEventCondition;
import net.sixik.sdmeventslab.events.conditions.EventCondition;
import net.sixik.sdmeventslab.events.endConditions.DayEndCondition;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import net.sixik.sdmeventslab.events.function.EventFunction;
import net.sixik.sdmeventslab.events.renders.EventRender;
import net.sixik.sdmeventslab.network.client.SendEndEventS2C;
import net.sixik.sdmeventslab.network.client.SendStartEventS2C;

import java.util.ArrayList;
import java.util.List;

public class EventBase {

    protected final EventSide eventSide;
    protected final ResourceLocation eventID;
    public final EventProperty properties = new EventProperty();
    public final EventRenderProperty renderProperty = new EventRenderProperty();
    public boolean isActive = false;
    //Список условий при которых ивент может произойти
    protected final List<AbstractEventCondition> conditions = new ArrayList<>();
    protected final List<EventCondition> conditions2 = new ArrayList<>();
    protected final List<EventFunction> functions = new ArrayList<>();
    private final List<EventEndCondition> endConditions = new ArrayList<>();
    private final List<EventRender> eventRenders = new ArrayList<>();

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

    public List<AbstractEventCondition> getConditions() {
        return conditions;
    }
  
    public List<EventCondition> getConditions2() {
        return conditions2;
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
        isActive = true;

        switch (eventSide) {
            case LOCAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    new SendStartEventS2C(this.eventID.toString()).sendTo(player);
                }
            }
            case GLOBAL -> new SendStartEventS2C(this.eventID.toString()).sendToAll(server);
        }

    }
    public void onEventEnd(MinecraftServer server) {

        isActive = false;

        switch (eventSide) {
            case LOCAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    new SendEndEventS2C(this.eventID.toString()).sendTo(player);
                }
            }
            case GLOBAL -> new SendEndEventS2C(this.eventID.toString()).sendToAll(server);
        }
    }
    public void onEventTick(MinecraftServer server) {}

    public enum EventSide {
        GLOBAL, // Происходит в мире для всех игроков
        LOCAL   // Происходит в мире локально для игрока
    }

    protected static class EventPropertyFunction extends EventFunction {


    }
}
