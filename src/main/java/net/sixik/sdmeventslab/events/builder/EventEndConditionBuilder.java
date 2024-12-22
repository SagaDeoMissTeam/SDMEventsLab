package net.sixik.sdmeventslab.events.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import net.sixik.sdmeventslab.register.EventsRegisters;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;
import java.util.function.Function;

@ZenRegister
@Document("mods/eventslab/builder/EventEndConditionBuilder")
@ZenCodeType.Name("mods.eventslab.builder.EventEndConditionBuilder")
public class EventEndConditionBuilder {

    private String id;
    private Consumer<CompoundTag> deserializer;
    private Consumer<CompoundTag> serializer;
    private Function<MinecraftServer, Boolean> consumer1;
    private Function<ServerPlayer, Boolean> consumer2;

    @ZenCodeType.Constructor
    public EventEndConditionBuilder(String id) {
        this.id = id;
        this.serializer = s -> {} ;
        this.deserializer = s -> {};
    }

    @ZenCodeType.Method
    public EventEndConditionBuilder addCanEndGlobal(Function<MinecraftServer, Boolean> consumer) {
        this.consumer1 = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventEndConditionBuilder addCanEndLocal(Function<ServerPlayer, Boolean> consumer) {
        this.consumer2 = consumer;
        return this;
    }

    public EventEndCondition create() {
        EventEndCondition eventEndCondition = new EventEndCondition() {
            @Override
            public CompoundTag serializeNBT() {
                CompoundTag nbt = super.serializeNBT();
                serializer.accept(nbt);
                return nbt;
            }

            @Override
            public void deserializeNBT(CompoundTag nbt) {
                deserializer.accept(nbt);
            }

            @Override
            public String getID() {
                return id;
            }

            @Override
            public boolean canEndGlobal(MinecraftServer server) {
                return consumer1.apply(server);
            }

            @Override
            public boolean canEndLocal(ServerPlayer player) {
                return consumer2.apply(player);
            }
        };
        EventsRegisters.registerEndCondition(eventEndCondition);
        return eventEndCondition;
    }
}
