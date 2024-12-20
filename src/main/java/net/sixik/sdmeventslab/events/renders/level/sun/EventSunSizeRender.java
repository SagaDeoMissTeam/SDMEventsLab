package net.sixik.sdmeventslab.events.renders.level.sun;

import net.sixik.sdmeventslab.events.renders.EventRender;

public class EventSunSizeRender extends EventRender {

    private float size = 20f;

    public EventSunSizeRender setSize(float size) {
        this.size = size;
        return this;
    }

    public float getSize() {
        return size;
    }
}