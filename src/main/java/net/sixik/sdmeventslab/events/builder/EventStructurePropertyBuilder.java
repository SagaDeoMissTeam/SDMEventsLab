package net.sixik.sdmeventslab.events.builder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.sixik.sdmeventslab.events.property.EventStructureProperty;

import java.util.ArrayList;
import java.util.List;

public class EventStructurePropertyBuilder {

    private List<EventStructureProperty> structureProperties = new ArrayList<>();

    public static Builder create(ResourceLocation[] structures) {
        return new Builder(structures);
    }

    public static class Builder {
        private EventStructureProperty builder;
        private List<EventFunctionBuilder> functionBuilders = new ArrayList<>();

        public Builder(ResourceLocation[] structures) {
            this.builder = new EventStructureProperty(structures);
        }

        public Builder addFunction(EventFunctionBuilder functionBuilder) {
            functionBuilders.add(functionBuilder);
            return this;
        }

        public Builder addBlackListDestroyBlock(Block block) {
            builder.addBlackListDestroyBlock(block);
            return this;
        }

        public Builder addBlackListDestroyBlock(Block... block) {
            builder.addBlackListDestroyBlock(block);
            return this;
        }

        public Builder addBlackListPlaceBlocks(Block block) {
            builder.addBlackListPlaceBlocks(block);
            return this;
        }

        public Builder addBlackListPlaceBlocks(Block... block) {
            builder.addBlackListPlaceBlocks(block);
            return this;
        }

        public Builder addBlackListUsableBlocks(Block block) {
            builder.addBlackListUsableBlocks(block);
            return this;
        }

        public Builder addBlackListUsableBlocks(Block... block) {
            builder.addBlackListUsableBlocks(block);
            return this;
        }

        public Builder setDisablePvP(boolean disablePvP) {
            builder.disablePvP = disablePvP;
            return this;
        }

        public Builder setDisableMobDamage(boolean disableMobDamage) {
            builder.disableMobDamage = disableMobDamage;
            return this;
        }

        public Builder setDisableEntityInteract(boolean disableEntityInteract) {
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
}
