package net.sixik.sdmeventslab.network;

import dev.architectury.networking.simple.MessageType;
import dev.architectury.networking.simple.SimpleNetworkManager;
import net.sixik.sdmeventslab.SDMEventsLab;
import net.sixik.sdmeventslab.network.client.SendStartEventS2C;

public class SDMEventsLabNetwork {

    private static final SimpleNetworkManager MANAGER = SimpleNetworkManager.create(SDMEventsLab.MODID);

    public static final MessageType START_EVENT = MANAGER.registerS2C("send_start_event", SendStartEventS2C::new);
    public static final MessageType END_EVENT = MANAGER.registerS2C("send_end_event", SendStartEventS2C::new);


    public static void init() {}
}
