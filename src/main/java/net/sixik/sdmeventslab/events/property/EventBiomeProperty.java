package net.sixik.sdmeventslab.events.property;

import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.function.EventFunction;

import java.util.ArrayList;
import java.util.List;

public class EventBiomeProperty {

    public final ResourceLocation[] biome;
    protected final List<EventFunction> functions = new ArrayList<>();

    public EventBase eventBase;

    public List<EventFunction> getFunctions() {
        return functions;
    }

    public EventBiomeProperty(ResourceLocation[] biome) {
        this.biome = biome;
    }

    public EventBiomeProperty setEventBase(EventBase eventBase) {
        this.eventBase = eventBase;

        getFunctions().stream().map(s -> s.setEvent(eventBase));

        return this;
    }
}
