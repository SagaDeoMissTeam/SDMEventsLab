package net.sixik.sdmeventslab.events.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.sixik.sdmeventslab.events.conditions.EventCondition;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@Document("mods/eventslab/builder/EventConditionBuilder")
@ZenCodeType.Name("mods.eventslab.builder.EventConditionBuilder")
public class EventConditionBuilder {

    private Function<MinecraftServer, Boolean> canExecuteGlobal;
    private Function<ServerPlayer, Boolean> canExecuteLocal;

    @ZenCodeType.Constructor
    public EventConditionBuilder() {}

    @ZenCodeType.Method
    public EventConditionBuilder addGlobal(Function<MinecraftServer, Boolean> consumer) {
        this.canExecuteGlobal = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventConditionBuilder addLocal(Function<ServerPlayer, Boolean> consumer) {
        this.canExecuteLocal = consumer;
        return this;
    }


    public EventCondition create() {
        return new EventCondition() {
            @Override
            public boolean canExecuteGlobal(MinecraftServer server) {
                return canExecuteGlobal!= null && canExecuteGlobal.apply(server);
            }

            @Override
            public boolean canExecuteLocal(ServerPlayer player) {
                return canExecuteLocal!= null && canExecuteLocal.apply(player);
            }
        };
    }
}
