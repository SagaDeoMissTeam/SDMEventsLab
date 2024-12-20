package net.sixik.sdmeventslab.events.conditions;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenRegister
@Document("mods/eventslab/conditions/DimensionCondition")
@ZenCodeType.Name("mods.eventslab.conditions.DimensionCondition")
public class DimensionCondition extends EventCondition{

    private final ResourceLocation dimensionID;

    @ZenCodeType.Constructor
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
