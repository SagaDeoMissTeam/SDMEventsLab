package net.sixik.sdmeventslab.events.conditions;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/conditions/PlayerOnlineCondition")
@ZenCodeType.Name("mods.eventslab.conditions.PlayerOnlineCondition")
public class WeatherCondition extends EventCondition{

    private final WeatherType type;

    @ZenCodeType.Constructor
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

    @ZenRegister
    @Document("mods/eventslab/conditions/WeatherType")
    @ZenCodeType.Name("mods.eventslab.conditions.WeatherType")
    @BracketEnum("eventslab:weather")
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
