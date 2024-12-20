package net.sixik.sdmeventslab.register;

import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.conditions.AbstractEventCondition;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class EventsRegisters {

    public static final HashMap<String, EventEndCondition> END_CONDITION_HASH_MAP = new HashMap<>();
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

    public static EventEndCondition registerEndCondition(EventEndCondition endCondition) {
        return registerEndCondition(endCondition, false);
    }

    public static EventEndCondition registerEndCondition(EventEndCondition endCondition, boolean runtime) {
        if(END_CONDITION_HASH_MAP.containsKey(endCondition.getID()))
            if(runtime)
                throw new RuntimeException("End Condition " + endCondition.getID() + " already registered!");

        END_CONDITION_HASH_MAP.put(endCondition.getID(), endCondition);
        return endCondition;
    }


    public static HashMap<ResourceLocation, EventBase> getEvents(){

        return EVENTS_MAP;
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
