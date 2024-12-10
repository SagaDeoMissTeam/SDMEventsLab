package net.sixik.sdmeventslab.events.renders;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.sixik.sdmeventslab.events.EventBase;


public class EventRender {

    public EventBase eventBase;

    public void setEventBase(EventBase eventBase) {
        this.eventBase = eventBase;
    }

    @OnlyIn(Dist.CLIENT)
    public void onRenderGUI(RenderGuiEvent.Post event) {}

    @OnlyIn(Dist.CLIENT)
    public void onRenderStart(RenderGuiEvent.Post event) {}

    @OnlyIn(Dist.CLIENT)
    public void onRenderLevel(RenderLevelStageEvent event) {}

    @OnlyIn(Dist.CLIENT)
    public void resetRender() {}
}
