package net.sixik.sdmeventslab.network.client;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.EventRenderManager;
import net.sixik.sdmeventslab.events.renders.EventRender;
import net.sixik.sdmeventslab.events.renders.EventRenderLogo;
import net.sixik.sdmeventslab.network.SDMEventsLabNetwork;
import net.sixik.sdmeventslab.register.EventsRegisters;

public class SendStartEventS2C extends BaseS2CMessage {

    private final String eventName;

    public SendStartEventS2C(String eventName) {
        this.eventName = eventName;
    }

    public SendStartEventS2C(FriendlyByteBuf friendlyByteBuf) {
        this.eventName = friendlyByteBuf.readUtf();
    }

    @Override
    public MessageType getType() {
        return SDMEventsLabNetwork.START_EVENT;
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUtf(eventName);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(NetworkManager.PacketContext packetContext) {

        if(eventName.isEmpty()) {
            return;
        }

        EventBase eventBase = EventsRegisters.getEvent(new ResourceLocation(eventName));
        if(eventBase == null) {
            //SDMEventsLab.LOGGER.error("Event not found!");
            return;
        }

        for (EventRender eventRender : eventBase.getEventRenders()) {
            eventRender.resetRender();
            if(eventRender instanceof EventRenderLogo renderLogo) {
                EventRenderManager.logoRender.add(new EventRenderManager.RenderLogoStruct(renderLogo, eventName));
                if(eventBase.renderProperty.isRenderTitleInScreenAfterLogo)
                    EventRenderManager.renderList.add(new EventRenderManager.RenderStruct(renderLogo, eventName));
            } else {
                EventRenderManager.renderList.add(new EventRenderManager.RenderStruct(eventRender, eventName));
            }

            EventRenderManager.currentsEvents.add(eventBase);
        }
    }
}
