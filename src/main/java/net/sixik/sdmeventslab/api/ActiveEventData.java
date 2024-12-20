package net.sixik.sdmeventslab.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import net.sixik.sdmeventslab.register.EventsRegisters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActiveEventData {

    public ResourceLocation eventID;
    public List<EventEndCondition> endConditions;

    public ActiveEventData(ResourceLocation eventID) {
        this.eventID = eventID;
        this.endConditions = new ArrayList<>();
    }

    public ActiveEventData addEndCondition(EventEndCondition endCondition) {
        endConditions.add(endCondition);
        return this;
    }

    public ActiveEventData addEndCondition(Collection<EventEndCondition> endCondition) {
        endConditions.addAll(endCondition);
        return this;
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putString("eventID", eventID.toString());

        ListTag l = new ListTag();
        for (EventEndCondition endCondition : endConditions) {
            l.add(endCondition.serializeNBT());
        }
        tag.put("endConditions", l);
        return tag;
    }

    public static ActiveEventData deserialize(CompoundTag tag) {
        ActiveEventData data = new ActiveEventData(new ResourceLocation(tag.getString("eventID")));
        ListTag l = tag.getList("endConditions", 10);
        for (int i = 0; i < l.size(); i++) {
            CompoundTag nbt = l.getCompound(i);
            if(!nbt.contains("id")) continue;
            EventEndCondition condition = EventsRegisters.END_CONDITION_HASH_MAP.getOrDefault(nbt.getString("id"), null);
            if(condition == null) continue;
            condition.deserializeNBT(nbt);
            data.addEndCondition(condition);
        }
        return data;
    }
}
