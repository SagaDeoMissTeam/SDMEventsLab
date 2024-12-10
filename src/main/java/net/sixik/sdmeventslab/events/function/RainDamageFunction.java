package net.sixik.sdmeventslab.events.function;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.sixik.sdmeventslab.events.conditions.WeatherCondition;

public class RainDamageFunction extends EventFunction{

    protected final float damage;
    protected final int damageDelay;
    protected final WeatherCondition.WeatherType type;

    public RainDamageFunction(float damage, WeatherCondition.WeatherType type) {
        this.damage = damage;
        this.type = type;
        this.damageDelay = 2;
    }

    public RainDamageFunction(float damage, WeatherCondition.WeatherType type, int damageDelay) {
        this.damage = damage;
        this.type = type;
        this.damageDelay = damageDelay;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        if(event.player instanceof ServerPlayer serverPlayer) {

            if (event.player.level().getGameTime() % damageDelay == 0) {
                if (type.isSuccess(serverPlayer.serverLevel())) {
                    serverPlayer.hurt(serverPlayer.damageSources().generic(), damage);
                }
            }
        }
    }
}
