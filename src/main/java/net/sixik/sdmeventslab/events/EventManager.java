package net.sixik.sdmeventslab.events;

import net.minecraft.server.MinecraftServer;
import net.sixik.sdmeventslab.register.EventsRegisters;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    public static final EventManager INSTANCE = new EventManager();
    public List<EventBase> mayStarted = new ArrayList<>();

    public void checkProperties(MinecraftServer server){

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
                System.out.println("Work");
            };

        }
    };



}
