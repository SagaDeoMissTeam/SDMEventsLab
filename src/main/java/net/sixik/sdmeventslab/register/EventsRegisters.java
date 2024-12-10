package net.sixik.sdmeventslab.register;

import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.conditions.AbstractEventCondition;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class EventsRegisters {

    private static final HashMap<ResourceLocation, AbstractEventCondition> CONDITION_HASH_MAP = new HashMap<>();
    private static final HashMap<ResourceLocation, EventBase> EVENTS_MAP = new HashMap<>();

    public static EventBase registerEvent(EventBase eventBase) {
        if(EVENTS_MAP.containsKey(eventBase.getEventID()))
            throw new RuntimeException("Event " + eventBase.getEventID() + " already registered!");

        EVENTS_MAP.put(eventBase.getEventID(), eventBase);
        return eventBase;
    }

    public static AbstractEventCondition registerCondition(AbstractEventCondition condition) {
        if(CONDITION_HASH_MAP.containsKey(condition.getConditionID()))
            throw new RuntimeException("Condition " + condition.getConditionID() + " already registered!");

        CONDITION_HASH_MAP.put(condition.getConditionID(), condition);
        return condition;
    }

    @Nullable
    public static AbstractEventCondition getCondition(ResourceLocation conditionID) {
        return CONDITION_HASH_MAP.get(conditionID);
    }

    @Nullable
    public static EventBase getEvent(ResourceLocation eventID) {
        return EVENTS_MAP.get(eventID);
    }

}
