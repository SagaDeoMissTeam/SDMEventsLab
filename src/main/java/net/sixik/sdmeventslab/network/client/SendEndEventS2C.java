package net.sixik.sdmeventslab.network.client;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.managers.EventRenderManager;
import net.sixik.sdmeventslab.network.SDMEventsLabNetwork;
import net.sixik.sdmeventslab.register.EventsRegisters;

public class SendEndEventS2C extends BaseS2CMessage {

    private final String eventName;

    public SendEndEventS2C(String eventName) {
        this.eventName = eventName;
    }

    public SendEndEventS2C(FriendlyByteBuf friendlyByteBuf) {
        this.eventName = friendlyByteBuf.readUtf();
    }

    @Override
    public MessageType getType() {
        return SDMEventsLabNetwork.END_EVENT;
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

        EventRenderManager.renderList.removeIf(render -> render.eventName.equals(eventName));
        EventRenderManager.logoRender.removeIf(render -> render.eventName.equals(eventName));

        EventRenderManager.currentsEvents.removeIf(s -> s.getEventID().toString().equals(eventName));
    }
}
