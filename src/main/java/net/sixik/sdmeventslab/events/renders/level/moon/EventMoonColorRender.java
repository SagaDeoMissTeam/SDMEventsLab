package net.sixik.sdmeventslab.events.renders.level.moon;

import net.sixik.sdmeventslab.events.renders.EventRender;
import org.joml.Vector4f;

public class EventMoonColorRender extends EventRender {

    private float rColor = 1f;
    private float gColor = 1f;
    private float bColor = 1f;
    private float aColor = 1f;
    private float blend = 1f;

    public EventMoonColorRender setMoonColor(float rColor, float gColor, float bColor, float aColor, float blend) {
        this.rColor = rColor;
        this.gColor = gColor;
        this.bColor = bColor;
        this.aColor = aColor;
        this.blend = blend;
        return this;
    }

    @Override
    public Vector4f getVec4() {
        return new Vector4f(rColor,gColor,bColor, aColor);
    }
}
