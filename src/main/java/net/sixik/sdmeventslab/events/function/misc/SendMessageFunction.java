package net.sixik.sdmeventslab.events.function.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.sixik.sdmeventslab.api.ActiveEventData;
import net.sixik.sdmeventslab.api.IEventHistory;
import net.sixik.sdmeventslab.events.function.EventFunction;

import java.util.Optional;

public class SendMessageFunction extends EventFunction {

    protected final Component[] text;
    protected final FunctionStage stage;

    public SendMessageFunction(Component[] text, FunctionStage stage) {
        this.text = text;
        this.stage = stage;
    }

    @Override
    public void onEventStart(MinecraftServer server) {
        if(stage != FunctionStage.START) return;
        send(server);
    }

    @Override
    public void onEventEnd(MinecraftServer server) {
        if(stage != FunctionStage.END) return;
        send(server);
    }

    protected void send(MinecraftServer server) {
        switch (eventBase.getEventSide()) {
            case LOCAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    if(player instanceof IEventHistory eventHistory) {
                        Optional<ActiveEventData> o = eventHistory.sdm$getActivesEvents().stream().filter(s -> s.eventID.equals(eventBase.getEventID())).findFirst();
                        if(o.isPresent()) {
                            for (Component component : text) {
                                player.sendSystemMessage(component);
                            }
                            break;
                        }
                    }
                }
            }
            case GLOBAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    for (Component component : text) {
                        player.sendSystemMessage(component);
                    }
                }
            }
        }
    }
}
