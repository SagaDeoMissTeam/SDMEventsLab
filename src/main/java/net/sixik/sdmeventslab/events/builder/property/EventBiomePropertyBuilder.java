package net.sixik.sdmeventslab.events.builder.property;

import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.builder.EventCustomFunctionBuilder;
import net.sixik.sdmeventslab.events.builder.function.EventFunctionBuilder;
import net.sixik.sdmeventslab.events.property.EventBiomeProperty;

import java.util.ArrayList;
import java.util.List;

public class EventBiomePropertyBuilder {

    public static EventBiomePropertyBuilder create(ResourceLocation[] biomes) {
        return new EventBiomePropertyBuilder(biomes);
    }

    private EventBiomeProperty builder;
    private List<EventFunctionBuilder> functionBuilders = new ArrayList<>();


    public EventBiomePropertyBuilder(ResourceLocation[] biome) {
        builder = new EventBiomeProperty(biome);
    }

    public EventBiomePropertyBuilder addFunction(EventFunctionBuilder functionBuilder) {
        functionBuilders.add(functionBuilder);
        return this;
    }

    public EventBiomeProperty create() {
        for (EventFunctionBuilder functionBuilder : functionBuilders) {
            functionBuilder.getEventFunctions().stream().forEach(builder.getFunctions()::add);
            functionBuilder.getEventCustomFunctions().stream().map(EventCustomFunctionBuilder::create).forEach(builder.getFunctions()::add);
        }

        return builder;
    }
}
