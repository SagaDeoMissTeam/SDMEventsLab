package net.sixik.sdmeventslab.events.conditions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class WeatherCondition extends EventCondition{

    private final WeatherType type;

    public WeatherCondition(WeatherType type) {
        this.type = type;
    }

    @Override
    public boolean canExecuteGlobal(MinecraftServer server) {
        return type.isSuccess(server.overworld());
    }

    @Override
    public boolean canExecuteLocal(ServerPlayer player) {
        return canExecuteGlobal(player.server);
    }

    public enum WeatherType {
        THUNDER,
        RAIN,
        NONE;

        public boolean isSuccess(ServerLevel level) {
            return switch (this) {
                case THUNDER -> level.isThundering();
                case RAIN -> level.isRaining();
                case NONE -> !level.isRaining() && !level.isThundering();
                default -> false;
            };
        }
    }
}
