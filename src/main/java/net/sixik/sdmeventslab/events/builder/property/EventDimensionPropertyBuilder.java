package net.sixik.sdmeventslab.events.builder.property;

import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.builder.EventCustomFunctionBuilder;
import net.sixik.sdmeventslab.events.builder.function.EventFunctionBuilder;
import net.sixik.sdmeventslab.events.property.EventDimensionProperty;

import java.util.ArrayList;
import java.util.List;

public class EventDimensionPropertyBuilder {

    public static EventDimensionPropertyBuilder create(ResourceLocation[] dimensnions) {
        return new EventDimensionPropertyBuilder(dimensnions);
    }

    private EventDimensionProperty builder;
    private List<EventFunctionBuilder> functionBuilders = new ArrayList<>();


    public EventDimensionPropertyBuilder(ResourceLocation[] dimensions) {
        builder = new EventDimensionProperty(dimensions);
    }

    public EventDimensionPropertyBuilder addFunction(EventFunctionBuilder functionBuilder) {
        functionBuilders.add(functionBuilder);
        return this;
    }

    public EventDimensionProperty create() {
        for (EventFunctionBuilder functionBuilder : functionBuilders) {
            functionBuilder.getEventFunctions().stream().forEach(builder.getFunctions()::add);
            functionBuilder.getEventCustomFunctions().stream().map(EventCustomFunctionBuilder::create).forEach(builder.getFunctions()::add);
        }

        return builder;
    }
}
