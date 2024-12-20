package net.sixik.sdmeventslab.events.conditions;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/conditions/AbstractEventCondition")
@ZenCodeType.Name("mods.eventslab.conditions.AbstractEventCondition")
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
