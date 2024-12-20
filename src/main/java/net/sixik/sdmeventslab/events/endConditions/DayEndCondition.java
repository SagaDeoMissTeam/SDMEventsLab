package net.sixik.sdmeventslab.events.endConditions;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/endConditions/DayEndCondition")
@ZenCodeType.Name("mods.eventslab.endConditions.DayEndCondition")
public class DayEndCondition extends EventEndCondition {

    protected long dayLeft;

    @ZenCodeType.Constructor
    public DayEndCondition(long dayLeft) {
        this.dayLeft = dayLeft;
    }

    @Override
    public String getID() {
        return "dayCondition";
    }

    @Override
    public boolean canEndGlobal(MinecraftServer server) {
        return getDay(server) >= eventBase.dayStart + dayLeft;
    }

    @Override
    public boolean canEndLocal(ServerPlayer player) {
        return canEndGlobal(player.server);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        nbt.putLong("dayLeft", dayLeft);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.dayLeft = nbt.getLong("dayLeft");
    }
}
