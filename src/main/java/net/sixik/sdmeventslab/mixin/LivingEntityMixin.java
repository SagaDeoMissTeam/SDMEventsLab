package net.sixik.sdmeventslab.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.managers.EventManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Unique
    private LivingEntity sdm$events$entity = (LivingEntity)(Object) this;

    @Inject(method = "isBlocking", at = @At("HEAD"), cancellable = true)
    private void sdm$isBlocking(CallbackInfoReturnable<Boolean> cir) {
        if(sdm$events$entity instanceof Player player) {
            for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
                if(!startedGlobalEvent.properties.isPlayerCanUseShield) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }
    }
}
