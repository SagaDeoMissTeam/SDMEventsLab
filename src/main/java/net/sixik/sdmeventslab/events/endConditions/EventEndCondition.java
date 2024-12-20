package net.sixik.sdmeventslab.events.endConditions;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.INBTSerializable;
import net.sixik.sdmeventslab.events.EventBase;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/endConditions/EventEndCondition")
@ZenCodeType.Name("mods.eventslab.endConditions.EventEndCondition")
public abstract class EventEndCondition implements INBTSerializable<CompoundTag> {

    protected EventBase eventBase;

    public EventEndCondition setEventBase(EventBase eventBase) {
        this.eventBase = eventBase;
        return this;
    }

    public abstract String getID();

    public boolean canEndGlobal(MinecraftServer server) {
        return false;
    }

    public boolean canEndLocal(ServerPlayer player) {
        return false;
    }


    protected final long getDay(MinecraftServer server) {
        return server.overworld().getDayTime() / 24000;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("id", getID());
        return nbt;
    }
}
