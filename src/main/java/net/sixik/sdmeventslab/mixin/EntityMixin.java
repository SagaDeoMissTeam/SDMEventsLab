package net.sixik.sdmeventslab.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.sixik.sdmeventslab.api.ActiveEventData;
import net.sixik.sdmeventslab.api.IEventHistory;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(Entity.class)
public class EntityMixin implements IEventHistory {

    @Unique
    private final List<ActiveEventData> sdm$activeEvents = new ArrayList<ActiveEventData>(0);
    @Unique
    private final Map<ResourceLocation, Integer> sdm$eventsHistory = new HashMap<ResourceLocation, Integer>(0);

    @Override
    public Map<ResourceLocation, Integer> sdm$getEventHistory() {
        return sdm$eventsHistory;
    }

    @Override
    public void sdm$addEventHistory(ResourceLocation id) {
        sdm$eventsHistory.put(id, sdm$eventsHistory.getOrDefault(id, 0) + 1);
    }

    @Override
    public List<ActiveEventData> sdm$getActivesEvents() {
        return sdm$activeEvents;
    }

    @Override
    public void sdm$addActiveEvent(ResourceLocation id, List<EventEndCondition> endConditions) {
        sdm$activeEvents.add(new ActiveEventData(id).addEndCondition(endConditions));
        sdm$addEventHistory(id);
    }


    @Override
    public void sdm$removeActiveEvent(ResourceLocation id) {
        sdm$activeEvents.removeIf(s -> Objects.equals(s.eventID, id));
    }
    @Inject(method = "load", at = @At("RETURN"))
    public void sdm$load(CompoundTag compoundTag, CallbackInfo ci) {
        if(compoundTag.contains("activeEvents")) {
            ListTag a = compoundTag.getList("activeEvents", 8);
            for (Tag s : a) {
                sdm$activeEvents.add(ActiveEventData.deserialize((CompoundTag) s));
            }
        }

        if(compoundTag.contains("eventHistory")) {
            ListTag d = compoundTag.getList("eventHistory", 10);
            for (Tag j : d) {
                CompoundTag f = (CompoundTag) j;
                ResourceLocation id = new ResourceLocation(f.getString("id"));
                int count = f.getInt("count");
                sdm$eventsHistory.put(id, count);
            }
        }
    }

    @Inject(method = "saveWithoutId", at = @At("RETURN"))
    public void sdm$saveAdditional(CompoundTag compoundTag, CallbackInfoReturnable<CompoundTag> cir){
        ListTag a = new ListTag();
        for (ActiveEventData sdm$activeEvent : sdm$activeEvents) {
            a.add(sdm$activeEvent.serialize());
        }

        ListTag d = new ListTag();
        for (Map.Entry<ResourceLocation, Integer> entry : sdm$eventsHistory.entrySet()) {
            CompoundTag f = new CompoundTag();
            f.putString("id", entry.getKey().toString());
            f.putInt("count", entry.getValue());
            d.add(f);
        }

        compoundTag.put("activeEvents", a);
        compoundTag.put("eventHistory", d);
    }
}
