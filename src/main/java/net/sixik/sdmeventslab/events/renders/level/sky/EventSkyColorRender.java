package net.sixik.sdmeventslab.events.renders.level.sky;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sixik.sdmeventslab.SDMEventsLab;
import net.sixik.sdmeventslab.events.renders.EventRender;

public class EventSkyColorRender extends EventRender {

    private float rColor = 1f;
    private float gColor = 1f;
    private float bColor = 1f;
    private float aColor = 1f;

    public EventSkyColorRender setSunColor(float rColor, float gColor, float bColor, float aColor) {
        this.rColor = rColor;
        this.gColor = gColor;
        this.bColor = bColor;
        this.aColor = aColor;
        return this;
    }

    @Override
    public int getInt() {
        return SDMEventsLab.rgbToInt(rColor,gColor,bColor);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void customRender() {
        RenderSystem.setShaderColor(rColor,gColor,bColor,aColor);
    }
}
