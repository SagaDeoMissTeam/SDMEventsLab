package net.sixik.sdmeventslab.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.managers.EventRenderManager;
import net.sixik.sdmeventslab.events.renders.EventRender;
import net.sixik.sdmeventslab.events.renders.level.moon.EventMoonColorRender;
import net.sixik.sdmeventslab.events.renders.level.moon.EventMoonSizeRender;
import net.sixik.sdmeventslab.events.renders.level.moon.EventMoonTextureRender;
import net.sixik.sdmeventslab.events.renders.level.sun.EventSunColorRender;
import net.sixik.sdmeventslab.events.renders.level.sun.EventSunSizeRender;
import net.sixik.sdmeventslab.events.renders.level.sun.EventSunTextureRender;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow
    @Final
    private static ResourceLocation MOON_LOCATION;

    @Shadow @Final private static ResourceLocation SUN_LOCATION;

    @Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getMoonPhase()I"))
    private void sdm$changeMoonColor(PoseStack $$0, Matrix4f $$1, float partialTicks, Camera $$3, boolean $$4, Runnable $$5, CallbackInfo ci) {
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventMoonColorRender colorRender) {
                    colorRender.customRender();
                }
            }
        }
    }


    @Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", ordinal = 1))
    private void sdm$changeMoonTexture(int moonTextureId, ResourceLocation resourceLocation) {
        boolean isE = false;
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventMoonTextureRender colorRender) {
                    colorRender.customRender();
                    isE = true;
                }
            }
        }

        if(!isE) {
            RenderSystem.setShaderTexture(moonTextureId, MOON_LOCATION);
        }
    }


    @ModifyConstant(method = "renderSky", constant = @Constant(floatValue = 20.0F))
    private float sdm$changeMoonSize(float ogSize) {
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventMoonSizeRender colorRender) {
                    return colorRender.getSize();
                }
            }
        }

        return ogSize;
    }


    @Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;begin(Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;Lcom/mojang/blaze3d/vertex/VertexFormat;)V", ordinal = 1))
    public void sdm$changeSunColor(PoseStack p_202424_, Matrix4f p_254034_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_, CallbackInfo ci) {
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventSunColorRender colorRender) {
                    colorRender.customRender();
                }
            }
        }

    }



    @Redirect(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", ordinal = 0))
    private void sdm$changeSunTexture(int moonTextureId, ResourceLocation resourceLocation) {
        boolean isE = false;
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventSunTextureRender colorRender) {
                    colorRender.customRender();
                    isE = true;
                }
            }
        }

        if(!isE) {
            RenderSystem.setShaderTexture(moonTextureId, SUN_LOCATION);
        }
    }

    @ModifyConstant(
            method = "renderSky",
            constant = @Constant(floatValue = 30.0F)
    )
    private float sdm$changeSunSize(float original) {
        for (EventBase currentsEvent : EventRenderManager.currentsEvents) {
            for (EventRender eventRender : currentsEvent.getEventRenders()) {
                if(eventRender instanceof EventSunSizeRender colorRender) {
                    return colorRender.getSize();
                }
            }
        }

        return original;
    }
}
