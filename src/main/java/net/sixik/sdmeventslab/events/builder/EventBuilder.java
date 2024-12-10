package net.sixik.sdmeventslab.events.builder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.conditions.*;
import net.sixik.sdmeventslab.events.function.EventFunction;
import net.sixik.sdmeventslab.register.EventsRegisters;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class EventBuilder {

    public static EventBuilder createEvent(ResourceLocation eventID, EventBase.EventSide eventSide) {
        return new EventBuilder(eventID, eventSide);
    }

    private EventBase eventBase;
    private ResourceLocation eventID;
    private EventBase.EventSide eventSide;

    private List<FunctionBuilder> eventFunctions = new ArrayList<>();
    private List<ConditionBuilder> eventConditions = new ArrayList<>();

    protected EventBuilder(ResourceLocation eventID, EventBase.EventSide eventSide) {
        this.eventID = eventID;
        this.eventSide = eventSide;
        eventBase = new EventBase(this.eventID, this.eventSide);
    }

    public EventBuilder addCustomFunction(FunctionBuilder eventFunction) {
        eventFunctions.add(eventFunction);
        return this;
    }

    public EventBuilder addCustomCondition(ConditionBuilder eventCondition) {
        eventConditions.add(eventCondition);
        return this;
    }

    public EventBuilder addDayCondition(long day, EventCondition.ConditionType predicate) {
        eventBase.getConditions2().add(new DayCondition(day, predicate));
        return this;
    }

    public EventBuilder addBiomeCondition(ResourceLocation biomeID) {
        eventBase.getConditions2().add(new BiomeCondition(biomeID).setEvent(eventBase));
        return this;
    }

    public EventBuilder addDimensionCondition(ResourceLocation dimensionID) {
        eventBase.getConditions2().add(new DimensionCondition(dimensionID).setEvent(eventBase));
        return this;
    }

    public EventBuilder addWeatherCondition(WeatherCondition.WeatherType type) {
        eventBase.getConditions2().add(new WeatherCondition(type).setEvent(eventBase));
        return this;
    }

    public EventBuilder addPlayerOnlineCondition(int countPlayers, EventCondition.ConditionType type) {
        eventBase.getConditions2().add(new PlayerOnlineCondition(countPlayers, type).setEvent(eventBase));
        return this;
    }

    public EventBase create() {

        eventConditions.stream().map(ConditionBuilder::create).map(s -> s.setEvent(eventBase)).forEach(eventBase.getConditions2()::add);
        eventFunctions.stream().map(FunctionBuilder::create).map(s -> s.setEvent(eventBase)).forEach(eventBase.getFunctions()::add);

        return EventsRegisters.registerEvent(eventBase);
    }

    public static class ConditionBuilder {
        private Function<MinecraftServer, Boolean> canExecuteGlobal;
        private Function<ServerPlayer, Boolean> canExecuteLocal;

        public ConditionBuilder() {}

        public ConditionBuilder addGlobal(Function<MinecraftServer, Boolean> consumer) {
            this.canExecuteGlobal = consumer;
            return this;
        }

        public ConditionBuilder addLocal(Function<ServerPlayer, Boolean> consumer) {
            this.canExecuteLocal = consumer;
            return this;
        }

        public EventCondition create() {
            return new EventCondition() {
                @Override
                public boolean canExecuteGlobal(MinecraftServer server) {
                    return canExecuteGlobal!= null && canExecuteGlobal.apply(server);
                }

                @Override
                public boolean canExecuteLocal(ServerPlayer player) {
                    return canExecuteLocal!= null && canExecuteLocal.apply(player);
                }
            };
        }
    }

    public static class FunctionBuilder {

        private Consumer<MinecraftServer> onEventStart;
        private Consumer<MinecraftServer> onEventEnd;
        private Consumer<MinecraftServer> onEventTick;
        private Consumer<MobSpawnEvent.FinalizeSpawn> onEntitySpawnEvent;
        private Consumer<TickEvent.PlayerTickEvent> onPlayerTickEvent;
        private Consumer<LivingDeathEvent> onLivingDeathEvent;
        private Consumer<PlayerInteractEvent.EntityInteract> onEntityInteractEvent;
        private Consumer<ServerPlayer> applyEffectPlayer;
        private Consumer<ServerPlayer> resetEffectFromPlayers;

        public FunctionBuilder addEventStart(Consumer<MinecraftServer> consumer) {
            this.onEventStart = consumer;
            return this;
        }

        public FunctionBuilder addEventEnd(Consumer<MinecraftServer> consumer) {
            this.onEventEnd = consumer;
            return this;
        }

        public FunctionBuilder addEventTick(Consumer<MinecraftServer> consumer) {
            this.onEventTick = consumer;
            return this;
        }

        public FunctionBuilder addEntitySpawnEvent(Consumer<MobSpawnEvent.FinalizeSpawn> consumer) {
            this.onEntitySpawnEvent = consumer;
            return this;
        }

        public FunctionBuilder addPlayerTickEvent(Consumer<TickEvent.PlayerTickEvent> consumer) {
            this.onPlayerTickEvent = consumer;
            return this;
        }

        public FunctionBuilder addLivingDeathEvent(Consumer<LivingDeathEvent> consumer) {
            this.onLivingDeathEvent = consumer;
            return this;
        }

        public FunctionBuilder addEntityInteractEvent(Consumer<PlayerInteractEvent.EntityInteract> consumer) {
            this.onEntityInteractEvent = consumer;
            return this;
        }

        public FunctionBuilder applyEffectToPlayer(Consumer<ServerPlayer> applyEffectPlayer, Consumer<ServerPlayer> resetFromPlayer) {
            this.applyEffectPlayer = applyEffectPlayer;
            this.resetEffectFromPlayers = resetFromPlayer;
            return this;
        }

        public EventFunction create() {
            return new EventFunction() {
                @Override
                public void onEventStart(MinecraftServer server) {
                    if (onEventStart!= null) {
                        onEventStart.accept(server);
                    }
                }

                @Override
                public void onEventEnd(MinecraftServer server) {
                    if (onEventEnd!= null) {
                        onEventEnd.accept(server);
                    }
                }

                @Override
                public void onEventTick(MinecraftServer server) {
                    if (onEventTick!= null) {
                        onEventTick.accept(server);
                    }
                }

                @Override
                public void onEntitySpawnEvent(MobSpawnEvent.FinalizeSpawn event) {
                    if (onEntitySpawnEvent!= null) {
                        onEntitySpawnEvent.accept(event);
                    }
                }

                @Override
                public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
                    if (onPlayerTickEvent!= null) {
                        onPlayerTickEvent.accept(event);
                    }
                }

                @Override
                public void onLivingDeathEvent(LivingDeathEvent event) {
                    if (onLivingDeathEvent!= null) {
                        onLivingDeathEvent.accept(event);
                    }
                }

                @Override
                public void onEntityInteractEvent(PlayerInteractEvent.EntityInteract event) {
                    if (onEntityInteractEvent!= null) {
                        onEntityInteractEvent.accept(event);
                    }
                }

                @Override
                public void applyEffectPlayer(ServerPlayer player) {
                    if (applyEffectPlayer!= null) {
                        applyEffectPlayer.accept(player);
                    }
                }

                @Override
                public void resetEffectFromPlayers(ServerPlayer player) {
                    if (resetEffectFromPlayers!= null) {
                        resetEffectFromPlayers.accept(player);
                    }
                }
            };
        }
    }
}
