package net.sixik.sdmeventslab.events.renders;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.sixik.sdmeventslab.events.EventBase;
import org.joml.Vector4f;


public abstract class EventRender {

    public EventBase eventBase;

    public void setEventBase(EventBase eventBase) {
        this.eventBase = eventBase;
    }

    public int getInt() {
        return 0;
    }

    public Vector4f getVec4() { return new Vector4f(0,0,0, 0); }

    @OnlyIn(Dist.CLIENT)
    public void onRenderGUI(RenderGuiEvent.Post event) {}

    @OnlyIn(Dist.CLIENT)
    public void onRenderStart(RenderGuiEvent.Post event) {}

    @OnlyIn(Dist.CLIENT)
    public void onRenderLevel(RenderLevelStageEvent event) {}

    @OnlyIn(Dist.CLIENT)
    public void resetRender() {}

    @OnlyIn(Dist.CLIENT)
    public void customRender() {}

    @OnlyIn(Dist.CLIENT)
    public void customRender(GuiGraphics graphics) {}
}
