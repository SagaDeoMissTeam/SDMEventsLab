package net.sixik.sdmeventslab.events.conditions;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/conditions/PlayerOnlineCondition")
@ZenCodeType.Name("mods.eventslab.conditions.PlayerOnlineCondition")
public class PlayerOnlineCondition extends EventCondition{

    private final int countPlayers;
    private final EventCondition.ConditionType type;

    @ZenCodeType.Constructor
    public PlayerOnlineCondition(int countPlayers, ConditionType type) {
        this.countPlayers = countPlayers;
        this.type = type;
    }

    @Override
    public boolean canExecuteGlobal(MinecraftServer server) {
        switch (type) {
            case LESS -> {
                return server.getPlayerCount() < countPlayers;
            }
            case GREATER -> {
                return server.getPlayerCount() > countPlayers;
            }
            case EQUALS -> {
                return server.getPlayerCount() == countPlayers;
            }
            case EQUALS_LESS -> {
                return server.getPlayerCount() <= countPlayers;
            }
            case EQUALS_GREATER -> {
                return server.getPlayerCount() >= countPlayers;
            }
            default -> {
                throw new IllegalStateException("Unexpected condition type: " + type);
            }
        }
    }

    @Override
    public boolean canExecuteLocal(ServerPlayer player) {
        return canExecuteGlobal(player.server);
    }
}
