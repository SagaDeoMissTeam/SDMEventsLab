package net.sixik.sdmeventslab.events.function;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.sixik.sdmeventslab.api.ActiveEventData;
import net.sixik.sdmeventslab.api.IEventHistory;

import java.util.Optional;

public class GiveItemsFunction extends EventFunction{

    private final ItemStack[] item;

    public GiveItemsFunction(ItemStack[] item) {
        this.item = item;
    }

    @Override
    public void onEventStart(MinecraftServer server) {
        switch (eventBase.getEventSide()) {
            case LOCAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    if(player instanceof IEventHistory eventHistory) {
                        Optional<ActiveEventData> o = eventHistory.sdm$getActivesEvents().stream().filter(s -> s.eventID.equals(eventBase.getEventID())).findFirst();
                        if(o.isPresent()) {
                            for (ItemStack itemStack : item) {
                                ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                            }
                            break;
                        }
                    }
                }
            }
            case GLOBAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    for (ItemStack itemStack : item) {
                        ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                    }
                }
            }
        }
    }
}
