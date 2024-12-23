package net.sixik.sdmeventslab.events.managers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraftforge.common.util.INBTSerializable;
import net.sixik.sdmeventslab.api.ActiveEventData;
import net.sixik.sdmeventslab.api.IEventHistory;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.EventManagerConfig;
import net.sixik.sdmeventslab.events.conditions.EventCondition;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import net.sixik.sdmeventslab.register.EventsRegisters;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EventManager implements INBTSerializable<CompoundTag> {
    public static final EventManager INSTANCE = new EventManager();

    public final List<EventBase> startedGlobalEvents = new ArrayList<>();

    private final List<EventBase> canStartEvents = new ArrayList<>();

    private final Map<ResourceLocation, Integer> globalEventsHistory = new HashMap<>();
    private float chancePerHistory = 1.0f;

    public long nextDayEvent = -1;
    public long eventDayEnd = 0;

    public void tryEndEvents(MinecraftServer server) {
        if(!startedGlobalEvents.isEmpty()) {

            if(getDay(server) >= eventDayEnd) {

                Iterator<EventBase> events = startedGlobalEvents.iterator();
                while (events.hasNext()) {
                    EventBase eventBase = events.next();

                    int checkPoint = 0;

                    for (EventEndCondition endCondition : eventBase.getEndConditions()) {
                        if (endCondition.canEndGlobal(server)) checkPoint++;
                    }

                    if ((eventBase.properties.canEndByAnyCondition && checkPoint >= 1) || (checkPoint == eventBase.getEndConditions().size())) {
                        nextDayEvent = EventManagerConfig.dayTimeOutPerEvent > -1 ? getDay(server) + EventManagerConfig.dayTimeOutPerEvent : -1;
                        eventBase.onEventEnd(server);
                        events.remove();
                    }
                }
            }
        }

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if(player instanceof IEventHistory eventHistory) {
                Iterator<ActiveEventData> activeEventData = eventHistory.sdm$getActivesEvents().iterator();

                while (activeEventData.hasNext()) {
                    ActiveEventData event = activeEventData.next();

                    EventBase eventBase = EventsRegisters.getEvent(event.eventID);

                    if(eventBase == null) {
                        activeEventData.remove();
                        continue;
                    }

                    int checkPoint = 0;

                    for (EventEndCondition endCondition : eventBase.getEndConditions()) {
                        if(endCondition.canEndLocal(player)) checkPoint++;
                    }

                    if((eventBase.properties.canEndByAnyCondition && checkPoint >= 1) || (checkPoint == event.endConditions.size())) {
                        eventBase.onEventEnd(player.server);
                        activeEventData.remove();
                    }
                }
            }
        }
    }

    public void tryStartEvents(MinecraftServer server) {
        canStartEvents.clear();

        if(startedGlobalEvents.isEmpty()) {
            if(nextDayEvent <= -1 || getDay(server) >= nextDayEvent) {

                for (EventBase value : EventsRegisters.getEvents().values()) {
                    if (!value.eventSide.equals(EventBase.EventSide.GLOBAL)) continue;

                    if (!checkEventCondition(value, server, null)) continue;
                    canStartEvents.add(value);
                }

                startGlobalEvent(getEventWhoStarting(server), server);
            }
        }



        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            canStartEvents.clear();
            for (EventBase value : EventsRegisters.getEvents().values()) {
                if(!value.eventSide.equals(EventBase.EventSide.LOCAL)) continue;

                if (!checkEventCondition(value, server, player)) continue;
                canStartEvents.add(value);
            }

            startLocalEvent(getEventWhoStarting(server), player, server);
        }
    }

    public final void startLocalEvent(@Nullable EventBase base, ServerPlayer player, MinecraftServer server) {
        if(base == null) return;

        if(player instanceof IEventHistory eventHistory) {
            eventHistory.sdm$addActiveEvent(base.eventID, base.getEndConditions());
            base.onEventStart(server);
        }
    }

    public final void startGlobalEvent(@Nullable EventBase base, MinecraftServer server) {
        if(base == null) return;

        startedGlobalEvents.add(base);
        globalEventsHistory.put(base.getEventID(), globalEventsHistory.getOrDefault(base.getEventID(), 0) + 1);
        base.onEventStart(server);

        eventDayEnd = getDay(server) + base.properties.eventDayTime;
    }

    public static final long getDay(MinecraftServer server) {
        return server.overworld().getDayTime() / 24000;
    }

    public final boolean isStartedEvent(EventBase value) {
        for (EventBase eventBase : startedGlobalEvents) {
            if(eventBase.getEventID().equals(value.eventID)) return true;
        }
        return false;
    }

    public final boolean isStartedEvent(EventBase value, ServerPlayer player) {
        if(player instanceof IEventHistory eventHistory) {
            if(eventHistory.sdm$getActivesEvents().contains(value.eventID)) return false;
        }

        return true;
    }

    @Nullable
    protected EventBase getEventWhoStarting(MinecraftServer server) {
        RandomSource source = RandomSource.create();

        Map<EventBase, Double> events = new HashMap<>();

        for (EventBase event : canStartEvents) {
            if(event.properties.chanceStartEvent >= 100.0) events.put(event, 100.0);

            if (event.properties.chanceStartEvent < 100.0) {
                double rand = source.nextDouble() * 100.0;
                if (rand > event.properties.chanceStartEvent) continue;
                events.put(event, event.properties.chanceStartEvent);
            }
        }


        for (Map.Entry<EventBase, Double> value : events.entrySet()) {
            int countEvent = globalEventsHistory.getOrDefault(value.getKey().getEventID(), 0);

            double chanceDelete = chancePerHistory * countEvent;

            chanceDelete = Math.max(1.0, value.getValue() - chanceDelete);
            events.put(value.getKey(), chanceDelete);
        }

        return getRandomEvent(events, server);
    }

    @Nullable
    protected EventBase getRandomEvent(Map<EventBase, Double> events, MinecraftServer server) {
        RandomSource source = RandomSource.create();

        // Сначала ищем события с 100% шансом
        List<EventBase> guaranteedEvents = new ArrayList<>();
        for (Map.Entry<EventBase, Double> value : events.entrySet()) {
            if (value.getValue() >= 100.0) {
                guaranteedEvents.add(value.getKey());
            }
        }

        // Если есть события с 100% шансом, выбираем одно из них случайно
        if (!guaranteedEvents.isEmpty()) {
            return guaranteedEvents.get(source.nextInt(guaranteedEvents.size()));
        }

        // Если событий с 100% шансом нет, выбираем из всех событий
        double sum = 0;
        for (Map.Entry<EventBase, Double> value : events.entrySet()) {
            sum += value.getValue();
        }

        double rand = source.nextDouble() * sum;
        double currentSum = 0;

        for (Map.Entry<EventBase, Double> value : events.entrySet()) {
            currentSum += value.getValue();
            if (rand <= currentSum) return value.getKey();
        }

        return null; // Если почему-то ничего не выбрано (не должно случиться)
    }

    protected boolean checkEventCondition(EventBase eventBase, @Nullable MinecraftServer server, @Nullable ServerPlayer player) {
        int checkPoint = 0;

        for (EventBase canStartEvent : canStartEvents) {
            if(eventBase.eventID == canStartEvent.eventID) continue;
            if(eventBase.properties.backList.contains(canStartEvent.eventID)) return false;
            if(eventBase.properties.whiteList.contains(canStartEvent.eventID)) checkPoint++;
        }

        if (checkPoint != eventBase.properties.whiteList.size()) return false;

        switch (eventBase.eventSide) {
            case GLOBAL -> {
                if(server == null)
                    throw new RuntimeException("Server is can't be null!");

                if(isStartedEvent(eventBase)) return false;

                for (EventCondition condition : eventBase.getConditions()) {
                    if(!condition.canExecuteGlobal(server)) return false;
                }
            }
            case LOCAL -> {
                if(player == null)
                    throw new RuntimeException("Player is can't be null!");

                if(isStartedEvent(eventBase, player)) return false;

                for (EventCondition condition : eventBase.getConditions()) {
                    if(!condition.canExecuteLocal(player)) return false;
                }
            }
        }

        return true;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        ListTag l = new ListTag();
        for (EventBase startedGlobalEvent : startedGlobalEvents) {
            l.add(StringTag.valueOf(startedGlobalEvent.getEventID().toString()));
        }
        nbt.put("startedGlobalEvents", l);
        nbt.putLong("eventDayEnd", eventDayEnd);
        nbt.putLong("nextDayEvent", nextDayEvent);

        ListTag d = new ListTag();
        for (Map.Entry<ResourceLocation, Integer> entry : globalEventsHistory.entrySet()) {
            CompoundTag j = new CompoundTag();
            j.put("id", StringTag.valueOf(entry.getKey().toString()));
            j.putInt("count", entry.getValue());
            d.add(j);
        }
        nbt.put("globalEventsHistory", d);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        startedGlobalEvents.clear();
        globalEventsHistory.clear();

        if(nbt.contains("startedGlobalEvents")) {
            ListTag l = (ListTag) nbt.get("startedGlobalEvents");
            for (Tag s : l) {
               EventBase base = EventsRegisters.getEvent(new ResourceLocation(s.getAsString()));
               if(base == null) continue;
               startedGlobalEvents.add(base);
            }
        }
        if(nbt.contains("eventDayEnd"))
            eventDayEnd = nbt.getLong("eventDayEnd");
        if(nbt.contains("nextDayEvent"))
            nextDayEvent = nbt.getLong("nextDayEvent");

        if(nbt.contains("globalEventsHistory")) {
            globalEventsHistory.clear();
            ListTag d = (ListTag) nbt.get("globalEventsHistory");
            for (Tag j : d) {
                CompoundTag k = (CompoundTag) j;
                ResourceLocation id = new ResourceLocation(k.getString("id"));
                int count = k.getInt("count");
                globalEventsHistory.put(id, count);
            }
        }
    }


}
