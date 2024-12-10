package net.sixik.sdmeventslab.events.conditions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public abstract class AbstractEventCondition {


    protected final ResourceLocation conditionID;

    protected AbstractEventCondition(ResourceLocation conditionID) {
        this.conditionID = conditionID;
    }

    public ResourceLocation getConditionID() {
        return conditionID;
    }

    public abstract boolean canExecuteGlobal(MinecraftServer server);

    public abstract boolean canExecuteLocal(ServerPlayer player);

}
