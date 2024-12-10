package net.sixik.sdmeventslab.events;

import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.conditions.AbstractEventCondition;
import net.sixik.sdmeventslab.events.conditions.EventCondition;
import net.sixik.sdmeventslab.events.function.EventFunction;

import java.util.ArrayList;
import java.util.List;

public class EventBase {

    protected final EventSide eventSide;
    protected final ResourceLocation eventID;

    //Список условий при которых ивент может произойти
    protected final List<AbstractEventCondition> conditions = new ArrayList<>();
    protected final List<EventCondition> conditions2 = new ArrayList<>();
    protected final List<EventFunction> functions = new ArrayList<>();

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

    public enum EventSide {
        GLOBAL, // Происходит в мире для всех игроков
        LOCAL   // Происходит в мире локально для игрока
    }
}
