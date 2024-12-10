package net.sixik.sdmeventslab.events.conditions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class WeatherCondition extends EventCondition{

    private final WeatherType type;

    public WeatherCondition(WeatherType type) {
        this.type = type;
    }

    @Override
    public boolean canExecuteGlobal(MinecraftServer server) {

        switch (type) {
            case THUNDER ->
            {
                return server.overworld().isThundering();
            }
            case RAIN -> {
                return server.overworld().isRaining();
            }
            case NONE -> {
                return !server.overworld().isRaining() && !server.overworld().isThundering();
            }
            default -> {
                return true;
            }
        }
    }

    @Override
    public boolean canExecuteLocal(ServerPlayer player) {
        return canExecuteGlobal(player.server);
    }

    public enum WeatherType {
        THUNDER,
        RAIN,
        NONE
    }
}
