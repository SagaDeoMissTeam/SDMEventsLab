package net.sixik.sdmeventslab.events.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.function.EventFunction;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@Document("mods/eventslab/builder/EventFunctionBuilder")
@ZenCodeType.Name("mods.eventslab.builder.EventFunctionBuilder")
public class EventFunctionBuilder {
    private Consumer<MinecraftServer> onEventStart;
    private Consumer<MinecraftServer> onEventEnd;
    private Consumer<MinecraftServer> onEventTick;
    private Consumer<MobSpawnEvent.FinalizeSpawn> onEntitySpawnEvent;
    private Consumer<TickEvent.PlayerTickEvent> onPlayerTickEvent;
    private Consumer<LivingDeathEvent> onLivingDeathEvent;
    private Consumer<PlayerInteractEvent.EntityInteract> onEntityInteractEvent;
    private Consumer<ServerPlayer> applyEffectPlayer;
    private Consumer<ServerPlayer> resetEffectFromPlayers;
    private Consumer<PlayerEvent.PlayerRespawnEvent> onPlayerRespawnEvent;
    private Consumer<PlayerEvent.ItemPickupEvent> onPlayerItemPickupEvent;

    public void checkSide(EventBase.EventSide side) {
        if(side == EventBase.EventSide.LOCAL) {
            if(onEntitySpawnEvent != null)
                throw new IllegalStateException("Event function 'onEntitySpawnEvent' can't be used on LOCAL side");
            if(onEventTick != null)
                throw new IllegalStateException("Event function 'onEventTick' can't be used on LOCAL side");
        }
    }

    @ZenCodeType.Method
    public EventFunctionBuilder addPlayerRespawnEvent(Consumer<PlayerEvent.PlayerRespawnEvent> consumer) {
        this.onPlayerRespawnEvent = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventFunctionBuilder addPlayerItemPickupEvent(Consumer<PlayerEvent.ItemPickupEvent> consumer) {
        this.onPlayerItemPickupEvent = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventFunctionBuilder addEventStart(Consumer<MinecraftServer> consumer) {
        this.onEventStart = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventFunctionBuilder addEventEnd(Consumer<MinecraftServer> consumer) {
        this.onEventEnd = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventFunctionBuilder addEventTick(Consumer<MinecraftServer> consumer) {
        this.onEventTick = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventFunctionBuilder addEntitySpawnEvent(Consumer<MobSpawnEvent.FinalizeSpawn> consumer) {
        this.onEntitySpawnEvent = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventFunctionBuilder addPlayerTickEvent(Consumer<TickEvent.PlayerTickEvent> consumer) {
        this.onPlayerTickEvent = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventFunctionBuilder addLivingDeathEvent(Consumer<LivingDeathEvent> consumer) {
        this.onLivingDeathEvent = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventFunctionBuilder addEntityInteractEvent(Consumer<PlayerInteractEvent.EntityInteract> consumer) {
        this.onEntityInteractEvent = consumer;
        return this;
    }

    @ZenCodeType.Method
    public EventFunctionBuilder applyEffectToPlayer(Consumer<ServerPlayer> applyEffectPlayer, Consumer<ServerPlayer> resetFromPlayer) {
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

            @Override
            public void onPlayerItemPickupEvent(PlayerEvent.ItemPickupEvent event) {
                if(onPlayerItemPickupEvent != null) {
                    onPlayerItemPickupEvent.accept(event);
                }
            }

            @Override
            public void onPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
                if(onPlayerRespawnEvent != null) {
                    onPlayerRespawnEvent.accept(event);
                }
            }
        };
    }
}
