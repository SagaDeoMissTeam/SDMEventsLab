package net.sixik.sdmeventslab.events.function;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent;
import net.sixik.sdmeventslab.events.conditions.WeatherCondition;

public class RainEffectFunction extends EventFunction{

    protected final MobEffectInstance effect;
    protected final int checkEffectDelay;
    protected final WeatherCondition.WeatherType type;

    public RainEffectFunction(MobEffectInstance effect, WeatherCondition.WeatherType type) {
        this.effect = effect;
        this.checkEffectDelay = 10;
        this.type = type;
    }

    public RainEffectFunction(MobEffectInstance effect, WeatherCondition.WeatherType type, int checkEffectDelay) {
        this.effect = effect;
        this.checkEffectDelay = checkEffectDelay;
        this.type = type;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        if(event.player instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();

            if(level.getGameTime() % checkEffectDelay == 0) {

                if(player.isInWater()) {
                    MobEffectInstance e = player.getEffect(effect.getEffect());
                    if(e == null) {
                        player.addEffect(effect);
                    }
                }
            }
        }
    }
}
