package net.sixik.sdmeventslab.events;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.sixik.sdmeventslab.events.conditions.EventCondition;
import net.sixik.sdmeventslab.register.EventsRegisters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EventManager {
    public static final EventManager INSTANCE = new EventManager();
    public List<EventBase> mayStarted = new ArrayList<>();
    public HashMap<UUID,List<ResourceLocation>> activeOnPlayer = new HashMap<>();

    public void checkProperties(MinecraftServer server){

        mayStarted.clear();
        for (EventBase e : EventsRegisters.getEvents().values()) {

            int checkPoint = 0;

            for(EventBase e1 : EventsRegisters.getEvents().values()){

                if (e.eventID == e1.eventID) continue;
                if(e.properties.backList.contains(e1.eventID) && e1.isActive) break; ;

            }

            for(EventBase e1 : EventsRegisters.getEvents().values()){

                if (e.eventID == e1.eventID) continue;
                if(e.properties.whiteList.contains(e1.eventID) && e1.isActive) checkPoint++; ;

            }

            if (checkPoint == e.properties.whiteList.size()) {
                mayStarted.add(e);
            };

            if (e.eventSide == EventBase.EventSide.LOCAL){
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    boolean isBreak = false;
                    for (EventCondition c : e.conditions2) {

                        if(!c.canExecuteLocal(player)) {
                            isBreak = true;
                            break;
                        };

                    }
                    if(!isBreak && mayStarted.contains(e)) {
                        e.onEventStart(server);
                        if(!activeOnPlayer.containsKey(player.getGameProfile().getId()))
                            activeOnPlayer.put(player.getGameProfile().getId(),new ArrayList<>());
                        activeOnPlayer.get(player.getGameProfile().getId()).add(e.eventID);
                    }

                }
            }else{
                    boolean isBreak = false;
                    for (EventCondition c : e.conditions2) {
                       if(!c.canExecuteGlobal(server)) {
                           isBreak = true;
                           break;
                       };

                    }
                    if(!isBreak && mayStarted.contains(e)) e.onEventStart(server);
                    for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                        if(!activeOnPlayer.containsKey(player.getGameProfile().getId()))
                            activeOnPlayer.put(player.getGameProfile().getId(),new ArrayList<>());
                        activeOnPlayer.get(player.getGameProfile().getId()).add(e.eventID);
                    }
            }

        }
    };



}
