package net.sixik.sdmeventslab.events.renders.level.sun;

import com.mojang.blaze3d.systems.RenderSystem;
import net.sixik.sdmeventslab.SDMEventsLab;
import net.sixik.sdmeventslab.events.renders.EventRender;

@Deprecated
public class EventSunColorRender extends EventRender {

    public float rColor = 1f;
    public float gColor = 1f;
    public float bColor = 1f;
    public float aColor = 1f;
    public float blend = 1f;

    public EventSunColorRender setSunColor(float rColor, float gColor, float bColor, float aColor, float blend) {
        this.rColor = rColor;
        this.gColor = gColor;
        this.bColor = bColor;
        this.aColor = aColor;
        this.blend = blend;
        return this;
    }

    @Override
    public int getInt() {
        return SDMEventsLab.rgbToInt(rColor,gColor,bColor);
    }

    @Override
    public void customRender() {
        RenderSystem.setShaderColor(rColor,gColor,bColor,aColor);
    }
}
