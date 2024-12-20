package net.sixik.sdmeventslab.api;

import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;

import java.util.List;
import java.util.Map;

public interface IEventHistory {

    Map<ResourceLocation, Integer> sdm$getEventHistory();
    void sdm$addEventHistory(ResourceLocation id);
    List<ActiveEventData> sdm$getActivesEvents();
    void sdm$addActiveEvent(ResourceLocation id, List<EventEndCondition> endConditions);
    void sdm$removeActiveEvent(ResourceLocation id);
}
