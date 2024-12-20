package net.sixik.sdmeventslab.events.renders.level.moon;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import net.sixik.sdmeventslab.events.renders.EventRender;

public class EventMoonTextureRender extends EventRender {

    private ResourceLocation textureLocation;

    public EventMoonTextureRender setTexture(ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
        return this;
    }

    @Override
    public void customRender() {
        RenderSystem.setShaderTexture(0, textureLocation);
    }
}
