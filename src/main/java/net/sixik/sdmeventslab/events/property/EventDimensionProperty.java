package net.sixik.sdmeventslab.events.property;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.function.EventFunction;

import java.util.ArrayList;
import java.util.List;

public class EventDimensionProperty {

    public final ResourceLocation[] dimensions;
    protected final List<EventFunction> functions = new ArrayList<>();

    public EventBase eventBase;

    public List<EventFunction> getFunctions() {
        return functions;
    }

    public EventDimensionProperty(ResourceLocation[] dimensions) {
        this.dimensions = dimensions;
    }

    public EventDimensionProperty setEventBase(EventBase eventBase) {
        this.eventBase = eventBase;

        getFunctions().stream().map(s -> s.setEvent(eventBase));

        return this;
    }

    public boolean inDimension(BlockPos pos, ServerLevel level) {

        if(dimensions.length == 0) return true;

        for (ResourceLocation resourceLocation : dimensions) {
            if(level.dimension().location().equals(resourceLocation)) return true;
        }

        return false;
    }
}
