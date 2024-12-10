package net.sixik.sdmeventslab.events.conditions;

import net.minecraft.resources.ResourceLocation;

public abstract class AbstractEventCondition {


    protected final ResourceLocation conditionID;

    protected AbstractEventCondition(ResourceLocation conditionID) {
        this.conditionID = conditionID;
    }

    public ResourceLocation getConditionID() {
        return conditionID;
    }
}
