package net.sixik.sdmeventslab.events.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.CompoundTag;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import net.sixik.sdmeventslab.register.EventsRegisters;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@Document("mods/eventslab/builder/EventEndConditionBuilder")
@ZenCodeType.Name("mods.eventslab.builder.EventEndConditionBuilder")
public class EventEndConditionBuilder {

    private String id;
    private Consumer<CompoundTag> deserializer;
    private Consumer<CompoundTag> serializer;

    @ZenCodeType.Constructor
    public EventEndConditionBuilder(String id, Consumer<CompoundTag> serializer, Consumer<CompoundTag> deserializer) {
        this.id = id;
        this.serializer = serializer;
        this.deserializer = deserializer;
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
        };
        EventsRegisters.registerEndCondition(eventEndCondition);
        return eventEndCondition;
    }
}
