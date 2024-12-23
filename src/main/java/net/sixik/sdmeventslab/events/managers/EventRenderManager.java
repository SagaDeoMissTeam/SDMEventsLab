package net.sixik.sdmeventslab.events.managers;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sixik.sdmeventslab.SDMEventsLab;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.renders.EventRender;
import net.sixik.sdmeventslab.events.renders.EventRenderLogo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SDMEventsLab.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EventRenderManager {

    public static List<EventBase> currentsEvents = new ArrayList<>();

    public static List<RenderStruct> renderList = new ArrayList<>();
    public static LinkedList<RenderLogoStruct> logoRender = new LinkedList<>();

    @SubscribeEvent
    public static void onRenderEvent(RenderGuiEvent.Post event) {

        Iterator<RenderLogoStruct> render = logoRender.iterator();

        while (render.hasNext()) {
            RenderLogoStruct logo = render.next();
            EventRenderLogo logoRender = logo.logoRender;

            if (logoRender.renderTime >= 0 && !logoRender.isLogoShoved) {
                logoRender.onRenderStart(event);
                logoRender.renderTime--;
            } else {
                render.remove();
            }
        }

        if(logoRender.isEmpty()) {
            for (RenderStruct eventRender : renderList) {
                eventRender.logoRender.onRenderGUI(event);
            }
        }
    }


    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if(!logoRender.isEmpty()) {
            logoRender.get(0).logoRender.onRenderLevel(event);
        } else {
            for (RenderStruct eventRender : renderList) {
                eventRender.logoRender.onRenderLevel(event);
            }
        }


    }

    public static class RenderLogoStruct {
        public EventRenderLogo logoRender;
        public String eventName;

        public RenderLogoStruct(EventRenderLogo logoRender, String eventName) {
            this.logoRender = logoRender;
            this.eventName = eventName;
        }
    }

    public static class RenderStruct {
        public EventRender logoRender;
        public String eventName;

        public RenderStruct(EventRender logoRender, String eventName) {
            this.logoRender = logoRender;
            this.eventName = eventName;
        }
    }
}
