package net.sixik.sdmeventslab.events.renders;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.v2.color.RGBA;
import net.sixik.v2.color.TextureColor;

public class EventRenderLogo extends EventRender{
    public boolean isLogoShoved = false;
    protected double logoAlpha = 0;
    public TextureColor texture;
    public int renderTime = 400;

    public EventRenderLogo(EventBase eventBase) {
        this.eventBase = eventBase;
        this.texture = TextureColor.create(new ResourceLocation(eventBase.getEventID().getNamespace() + "textures/events/" + eventBase.getEventID().getPath() + ".png"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void resetRender() {
        renderTime = 400;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRenderGUI(RenderGuiEvent.Post event) {
        if(renderTime < 0) resetRender();

        GuiGraphics guiGraphics = event.getGuiGraphics();
        Window window = event.getWindow();
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        poseStack.translate(0,0, 2000);

        int posX = 2;
        int posY = window.getGuiScaledHeight() - (eventBase.renderProperty.logoSizeY / 2) - 2;

        texture.withAlpha((int) logoAlpha).draw(guiGraphics, posX, posY, eventBase.renderProperty.logoSizeX / 2, eventBase.renderProperty.logoSizeY / 2);

        if(renderTime != 0) {
            if (renderTime > eventBase.renderProperty.timeToShowLogo) {
                logoAlpha = Math.min(logoAlpha + eventBase.renderProperty.logoAlphaPerTick, 255);
            }
            renderTime--;
        }


        poseStack.popPose();
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRenderStart(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        Window window = event.getWindow();
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        poseStack.translate(0,0, 800);

        int centerX = window.getGuiScaledWidth() / 2;
        centerX -= eventBase.renderProperty.logoSizeX / 2;

        texture.withAlpha((int) logoAlpha).draw(guiGraphics, centerX,8,eventBase.renderProperty.logoSizeX,eventBase.renderProperty.logoSizeY);

        if (renderTime > 50) {
            logoAlpha = Math.min(logoAlpha + 5.1, 255);
        } else {
            logoAlpha = Math.max(logoAlpha - 5.1, 0);
        }

        poseStack.popPose();
    }
}
