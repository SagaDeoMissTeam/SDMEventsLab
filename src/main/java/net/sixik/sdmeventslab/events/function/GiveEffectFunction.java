package net.sixik.sdmeventslab.events.function;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class GiveEffectFunction extends EventFunction{

    private final MobEffectInstance effectInstance;

    public GiveEffectFunction(MobEffectInstance effectInstance) {
        this.effectInstance = effectInstance;
    }

    @Override
    public void applyEffectPlayer(ServerPlayer player) {
        MobEffectInstance effect = player.getEffect(effectInstance.getEffect());
        if(effect == null) {
            effect = effectInstance;
            player.addEffect(effect);
        }
    }

    @Override
    public void resetEffectFromPlayers(ServerPlayer player) {
        MobEffectInstance effect = player.getEffect(effectInstance.getEffect());
        if(effect != null) {
            player.removeEffect(effect.getEffect());
        }
    }
}
