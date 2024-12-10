package net.sixik.sdmeventslab.events.conditions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public class DimensionCondition extends EventCondition{

    private final ResourceLocation dimensionID;

    public DimensionCondition(ResourceLocation dimensionID) {
        this.dimensionID = dimensionID;
    }

    @Override
    public boolean canExecuteGlobal(MinecraftServer server) {
        throw new RuntimeException("DimensionCondition can only be executed LOCAL!");
    }

    @Override
    public boolean canExecuteLocal(ServerPlayer player) {
        return Objects.equals(player.serverLevel().dimension().registry(), dimensionID);
    }
}
