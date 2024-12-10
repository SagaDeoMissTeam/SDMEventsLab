package net.sixik.sdmeventslab.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.conditions.AbstractEventCondition;

import java.util.ArrayList;
import java.util.List;

public class EventBase {

    protected final EventSide eventSide;
    protected final ResourceLocation eventID;

    //Список условий при которых ивент может произойти
    protected final List<AbstractEventCondition> conditions = new ArrayList<>();

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

    public enum EventSide {
        GLOBAL, // Происходит в мире для всех игроков
        LOCAL   // Происходит в мире локально для игрока
    }
}
