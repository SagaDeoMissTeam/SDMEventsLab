package net.sixik.sdmeventslab.events.renders.level.moon;

import net.sixik.sdmeventslab.events.renders.EventRender;

public class EventMoonSizeRender extends EventRender {

    private float size = 20f;

    public EventMoonSizeRender setSize(float size) {
        this.size = size;
        return this;
    }

    public float getSize() {
        return size;
    }
}
