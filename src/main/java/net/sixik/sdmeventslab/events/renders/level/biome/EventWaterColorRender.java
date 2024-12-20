package net.sixik.sdmeventslab.events.renders.level.biome;

import net.sixik.sdmeventslab.SDMEventsLab;
import net.sixik.sdmeventslab.events.renders.EventRender;

public class EventWaterColorRender extends EventRender {

    private float rColor = 1f;
    private float gColor = 1f;
    private float bColor = 1f;

    public EventWaterColorRender setWaterColor(float rColor, float gColor, float bColor) {
        this.rColor = rColor;
        this.gColor = gColor;
        this.bColor = bColor;
        return this;
    }

    @Override
    public int getInt() {
        return SDMEventsLab.rgbToInt(rColor,gColor,bColor);
    }
}
