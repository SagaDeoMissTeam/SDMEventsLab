package net.sixik.sdmeventslab.events.builder.property;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.sixik.sdmeventslab.events.builder.EventCustomFunctionBuilder;
import net.sixik.sdmeventslab.events.builder.function.EventFunctionBuilder;
import net.sixik.sdmeventslab.events.property.EventStructureProperty;

import java.util.ArrayList;
import java.util.List;

public class EventStructurePropertyBuilder {
    
    public static EventStructurePropertyBuilder create(ResourceLocation[] structures) {
        return new EventStructurePropertyBuilder(structures);
    }

    private EventStructureProperty builder;
    private List<EventFunctionBuilder> functionBuilders = new ArrayList<>();

    public EventStructurePropertyBuilder(ResourceLocation[] structures) {
        this.builder = new EventStructureProperty(structures);
    }

    public EventStructurePropertyBuilder addFunction(EventFunctionBuilder functionBuilder) {
        functionBuilders.add(functionBuilder);
        return this;
    }

//    public EventStructurePropertyBuilder addBlackListDestroyBlock(Block block) {
//        builder.addBlackListDestroyBlock(block);
//        return this;
//    }

    public EventStructurePropertyBuilder addBlackListDestroyBlock(Block... block) {
        builder.addBlackListDestroyBlock(block);
        return this;
    }

//    public EventStructurePropertyBuilder addBlackListPlaceBlocks(Block block) {
//        builder.addBlackListPlaceBlocks(block);
//        return this;
//    }

    public EventStructurePropertyBuilder addBlackListPlaceBlocks(Block... block) {
        builder.addBlackListPlaceBlocks(block);
        return this;
    }

//    public EventStructurePropertyBuilder addBlackListUsableBlocks(Block block) {
//        builder.addBlackListUsableBlocks(block);
//        return this;
//    }

    public EventStructurePropertyBuilder addBlackListUsableBlocks(Block... block) {
        builder.addBlackListUsableBlocks(block);
        return this;
    }

    public EventStructurePropertyBuilder setDisablePvP(boolean disablePvP) {
        builder.disablePvP = disablePvP;
        return this;
    }

    public EventStructurePropertyBuilder setDisableMobDamage(boolean disableMobDamage) {
        builder.disableMobDamage = disableMobDamage;
        return this;
    }

    public EventStructurePropertyBuilder setDisableEntityInteract(boolean disableEntityInteract) {
        builder.disableEntityInteract = disableEntityInteract;
        return this;
    }

    public EventStructureProperty create() {
        for (EventFunctionBuilder functionBuilder : functionBuilders) {
            functionBuilder.getEventFunctions().stream().forEach(builder.getFunctions()::add);
            functionBuilder.getEventCustomFunctions().stream().map(EventCustomFunctionBuilder::create).forEach(builder.getFunctions()::add);
        }

        return builder;
    }

    public List<EventFunctionBuilder> getFunctionBuilders() {
        return functionBuilders;
    }
}
