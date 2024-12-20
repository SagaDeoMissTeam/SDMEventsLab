package net.sixik.sdmeventslab.mixin;

import net.minecraft.world.level.biome.Biome;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.EventRenderManager;
import net.sixik.sdmeventslab.events.renders.EventRender;
import net.sixik.sdmeventslab.events.renders.level.biome.EventFogColorRender;
import net.sixik.sdmeventslab.events.renders.level.biome.EventWaterColorRender;
import net.sixik.sdmeventslab.events.renders.level.biome.EventWaterFogColorRender;
import net.sixik.sdmeventslab.events.renders.level.sky.EventSkyColorRender;
import net.sixik.sdmeventslab.events.renders.level.sun.EventSunSizeRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {

    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    private void skyColorModifier(CallbackInfoReturnable<Integer> cir) {
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventSkyColorRender colorRender) {
                    cir.setReturnValue(colorRender.getInt());
                    return;
                }
            }
        }
    }

    @Inject(method = "getFogColor", at = @At("HEAD"), cancellable = true)
    private void fogColorModifier(CallbackInfoReturnable<Integer> cir) {
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventFogColorRender colorRender) {
                    cir.setReturnValue(colorRender.getInt());
                    return;
                }
            }
        }
    }

    @Inject(method = "getWaterColor", at = @At("HEAD"), cancellable = true)
    private void grassColorModifier(CallbackInfoReturnable<Integer> cir) {
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventWaterColorRender colorRender) {
                    cir.setReturnValue(colorRender.getInt());
                    return;
                }
            }
        }
    }

    @Inject(method = "getWaterFogColor", at = @At("HEAD"), cancellable = true)
    private void foliageColorModifier(CallbackInfoReturnable<Integer> cir) {
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventWaterFogColorRender colorRender) {
                    cir.setReturnValue(colorRender.getInt());
                    return;
                }
            }
        }
    }
}
