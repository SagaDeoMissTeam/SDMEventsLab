package net.sixik.sdmeventslab.events.function;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.MobSpawnEvent;

public class GiveEffectFunction extends EventFunction{

    private final MobEffectInstance effectInstance;

    public GiveEffectFunction(MobEffectInstance effectInstance) {
        this.effectInstance = effectInstance;
    }

//    @Override
//    public void onEntitySpawnEvent(MobSpawnEvent.FinalizeSpawn event) {
//        if(targetFunction == TargetFunction.PLAYER) return;
//
//        MobEffectInstance effect = event.getEntity().getEffect(effectInstance.getEffect());
//        if(effect == null) {
//            effect = effectInstance;
//            event.getEntity().addEffect(effect);
//        }
//    }
//
//    @Override
//    public void onEventEnd(MinecraftServer server) {
//        if(targetFunction == TargetFunction.PLAYER) return;
//
//        for (ServerLevel allLevel : server.getAllLevels()) {
//            for (Entity entity : allLevel.getEntities().getAll()) {
//                if(entity instanceof Player) continue;
//
//                if(entity instanceof LivingEntity living) {
//                    MobEffectInstance effect = living.getEffect(effectInstance.getEffect());
//                    if(effect != null) {
//                        living.removeEffect(effect.getEffect());
//                    }
//                }
//            }
//        }
//    }

    @Override
    public void applyEffectPlayer(ServerPlayer player) {
        if(targetFunction != TargetFunction.PLAYER) return;

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
