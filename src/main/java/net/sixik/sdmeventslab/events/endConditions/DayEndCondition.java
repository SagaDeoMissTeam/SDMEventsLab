package net.sixik.sdmeventslab.events.endConditions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class DayEndCondition extends EventEndCondition {

    protected final long dayLeft;

    public DayEndCondition(long dayLeft) {
        this.dayLeft = dayLeft;
    }

    @Override
    public boolean canEndGlobal(MinecraftServer server) {
        return getDay(server) >= eventBase.dayStart + dayLeft;
    }

    @Override
    public boolean canEndLocal(ServerPlayer player) {
        return canEndGlobal(player.server);
    }
}
