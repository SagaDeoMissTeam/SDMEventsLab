package net.sixik.sdmeventslab.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.managers.EventManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class MobMixin {

    @Unique
    private Mob sdm$mod = (Mob)(Object)this;

    @Inject(method = "isSunBurnTick", at = @At("RETURN"), cancellable = true)
    protected void sdm$isSunBurnTick(CallbackInfoReturnable<Boolean> cir) {
        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            if(sdm$mod instanceof Zombie && !startedGlobalEvent.properties.canZombieBurnInSun)
                if(cir.getReturnValue()) cir.setReturnValue(false);
            if(sdm$mod instanceof Skeleton && !startedGlobalEvent.properties.canSkeletonBurnInSun)
                if(cir.getReturnValue()) cir.setReturnValue(false);
        }
    }
}
