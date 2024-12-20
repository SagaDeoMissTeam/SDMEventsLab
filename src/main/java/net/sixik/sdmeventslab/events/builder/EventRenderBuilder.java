package net.sixik.sdmeventslab.events.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.renders.EventRender;
import net.sixik.sdmeventslab.events.renders.EventRenderLogo;
import net.sixik.sdmeventslab.events.renders.level.biome.EventFogColorRender;
import net.sixik.sdmeventslab.events.renders.level.biome.EventWaterColorRender;
import net.sixik.sdmeventslab.events.renders.level.biome.EventWaterFogColorRender;
import net.sixik.sdmeventslab.events.renders.level.moon.EventMoonColorRender;
import net.sixik.sdmeventslab.events.renders.level.moon.EventMoonSizeRender;
import net.sixik.sdmeventslab.events.renders.level.moon.EventMoonTextureRender;
import net.sixik.sdmeventslab.events.renders.level.sky.EventSkyColorRender;
import net.sixik.sdmeventslab.events.renders.level.sun.EventSunColorRender;
import net.sixik.sdmeventslab.events.renders.level.sun.EventSunSizeRender;
import net.sixik.sdmeventslab.events.renders.level.sun.EventSunTextureRender;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@Document("mods/eventslab/builder/EventRenderBuilder")
@ZenCodeType.Name("mods.eventslab.builder.EventRenderBuilder")
public class EventRenderBuilder {
    private List<EventRender> eventRenders = new ArrayList<>();

    @ZenCodeType.Method
    public EventRenderBuilder addLogoRender() {
        eventRenders.add(new EventRenderLogo());
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addMoonTexture(ResourceLocation textureLocation) {
        eventRenders.add(new EventMoonTextureRender().setTexture(textureLocation));
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addMoonColor(float rColor, float gColor, float bColor, float aColor) {
        eventRenders.add(new EventMoonColorRender().setMoonColor(rColor,gColor,bColor,aColor, 1f));
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addMoonSize(float size) {
        eventRenders.add(new EventMoonSizeRender().setSize(size));
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addSunTexture(ResourceLocation textureLocation) {
        eventRenders.add(new EventSunTextureRender().setTexture(textureLocation));
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addSunColor(float rColor, float gColor, float bColor, float aColor) {
        eventRenders.add(new EventSunColorRender().setSunColor(rColor,gColor,bColor,aColor, 1f));
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addSunSize(float size) {
        eventRenders.add(new EventSunSizeRender().setSize(size));
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addSkyColor(float rColor, float gColor, float bColor, float aColor) {
        eventRenders.add(new EventSkyColorRender().setSunColor(rColor,gColor,bColor,aColor));
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addFogColor(float rColor, float gColor, float bColor) {
        eventRenders.add(new EventFogColorRender().setFogColor(rColor,gColor,bColor));
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addWaterColor(float rColor, float gColor, float bColor) {
        eventRenders.add(new EventWaterColorRender().setWaterColor(rColor,gColor,bColor));
        return this;
    }

    @ZenCodeType.Method
    public EventRenderBuilder addWaterFogColor(float rColor, float gColor, float bColor) {
        eventRenders.add(new EventWaterFogColorRender().setWaterColor(rColor,gColor,bColor));
        return this;
    }


    public EventRenderBuilder create(EventBase eventBase) {
        eventRenders.forEach(render -> render.setEventBase(eventBase));
        return this;
    }

    public List<EventRender> getEventRenders() {
        return eventRenders;
    }
}
