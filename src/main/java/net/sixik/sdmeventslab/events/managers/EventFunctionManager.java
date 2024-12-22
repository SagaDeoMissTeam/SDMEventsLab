package net.sixik.sdmeventslab.events.managers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.sixik.sdmeventslab.SDMEventsLab;
import net.sixik.sdmeventslab.api.ActiveEventData;
import net.sixik.sdmeventslab.api.IEventHistory;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.EventManagerConfig;
import net.sixik.sdmeventslab.events.property.EventStructureProperty;
import net.sixik.sdmeventslab.events.function.EventFunction;
import net.sixik.sdmeventslab.network.client.SendEndEventS2C;
import net.sixik.sdmeventslab.register.EventsRegisters;

import java.util.Map;

public class EventFunctionManager {

    public static final EventFunctionManager INSTANCE = new EventFunctionManager();

    @SubscribeEvent
    public void onEventTick(TickEvent.ServerTickEvent server) {
        if(server.phase != TickEvent.Phase.START) return;

        if(server.getServer().overworld().dayTime() % 20 == 0 && SDMEventsLab.debugMessages) {
            long currentTime = server.getServer().overworld().dayTime();
            long ticksLeft = 8000 - (currentTime % 8000);
            System.out.println("Tick left for event: " + ticksLeft + "/8000");
        }

        if(server.getServer().overworld().dayTime() % EventManagerConfig.timePerEvent == 0) {
            EventManager.INSTANCE.tryEndEvents(server.getServer());
            EventManager.INSTANCE.tryStartEvents(server.getServer());
        }


        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            startedGlobalEvent.onEventTick(server.getServer());
        }
    }

    @SubscribeEvent
    public void onEntitySpawnEvent(MobSpawnEvent.FinalizeSpawn event) {

        if(event.getEntity().level() instanceof ServerLevel level) {

            for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
                for (EventFunction function : startedGlobalEvent.getFunctions()) {
                    function.onEntitySpawnEvent(event);
                }

                for (EventStructureProperty structureProperty : startedGlobalEvent.getStructureProperties()) {
                    if(structureProperty.inStructure(event.getEntity().blockPosition(), level)) {

                        for (EventFunction function : structureProperty.getFunctions()) {
                            function.onEntitySpawnEvent(event);
                        }
                    }
                }
            }

        }

    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        boolean flag = false;


        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            for (EventFunction function : startedGlobalEvent.getFunctions()) {
                function.onPlayerTickEvent(event);
            }

            if(event.player instanceof ServerPlayer player) {

                for (EventStructureProperty structureProperty : startedGlobalEvent.getStructureProperties()) {
                    if (structureProperty.inStructure(player.blockPosition(), player.serverLevel())) {

                        for (EventFunction function : structureProperty.getFunctions()) {
                            function.onPlayerTickEvent(event);
                        }
                    }
                }
            }

            flag = startedGlobalEvent.properties.isPlayerCanStayOnSun;
        }

        if(event.player instanceof IEventHistory eventHistory) {
            for (ActiveEventData activeEventData : eventHistory.sdm$getActivesEvents()) {
                EventBase base = EventsRegisters.getEvent(activeEventData.eventID);
                if(base == null) continue;

                for (EventFunction function : base.getFunctions()) {
                    function.onPlayerTickEvent(event);
                }

                if(event.player instanceof ServerPlayer player) {

                    for (EventStructureProperty structureProperty : base.getStructureProperties()) {
                        if (structureProperty.inStructure(player.blockPosition(), player.serverLevel())) {

                            for (EventFunction function : structureProperty.getFunctions()) {
                                function.onPlayerTickEvent(event);
                            }
                        }
                    }
                }

                if(!flag)
                    flag = base.properties.isPlayerCanStayOnSun;
            }
        }

        if(!flag && event.player instanceof ServerPlayer player && player.level().isDay()) {
            if(player.level().canSeeSky(player.blockPosition())) {
                player.setSecondsOnFire(8);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            for (EventFunction function : startedGlobalEvent.getFunctions()) {
                function.onLivingDeathEvent(event);
            }

            if(event.getEntity().level() instanceof ServerLevel level) {

                for (EventStructureProperty structureProperty : startedGlobalEvent.getStructureProperties()) {
                    if (structureProperty.inStructure(event.getEntity().blockPosition(), level)) {

                        for (EventFunction function : structureProperty.getFunctions()) {
                            function.onLivingDeathEvent(event);
                        }
                    }
                }
            }
        }

        if(event.getSource().getEntity() instanceof ServerPlayer player) {
            if(player instanceof IEventHistory eventHistory) {
                for (ActiveEventData activeEventData : eventHistory.sdm$getActivesEvents()) {
                    EventBase base = EventsRegisters.getEvent(activeEventData.eventID);
                    if(base == null) continue;
                    for (EventFunction function : base.getFunctions()) {
                        function.onLivingDeathEvent(event);
                    }



                    for (EventStructureProperty structureProperty : base.getStructureProperties()) {
                        if (structureProperty.inStructure(player.blockPosition(), player.serverLevel())) {

                            for (EventFunction function : structureProperty.getFunctions()) {
                                function.onLivingDeathEvent(event);
                            }
                        }
                    }

                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDamageEvent(LivingDamageEvent event) {
        double newDamage = event.getAmount();

        // Если атакуемый — игрок
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
                double protectionEffect = startedGlobalEvent.properties.protection;

                // Защита корректирует урон (в процентах)
                newDamage *= (1 + protectionEffect / 100.0);
            }
            event.setAmount((float) newDamage);
            return;
        }

        // Если атакующий — игрок
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
                double damageEffect = startedGlobalEvent.properties.damage;

                // Урон корректирует значение (в процентах)
                newDamage *= (1 - damageEffect / 100.0);
            }
            event.setAmount((float) newDamage);
            return;
        }
    }

    @SubscribeEvent
    public void onEntityInteractEvent(PlayerInteractEvent.EntityInteract event) {
        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            for (EventFunction function : startedGlobalEvent.getFunctions()) {
                function.onEntityInteractEvent(event);
            }

            if(event.getEntity().level() instanceof ServerLevel level) {

                for (EventStructureProperty structureProperty : startedGlobalEvent.getStructureProperties()) {
                    if (structureProperty.inStructure(event.getEntity().blockPosition(), level)) {

                        for (EventFunction function : structureProperty.getFunctions()) {
                            function.onEntityInteractEvent(event);
                        }
                    }
                }
            }
        }

        if(event.getEntity() instanceof ServerPlayer player) {
            if(player instanceof IEventHistory eventHistory) {
                for (ActiveEventData activeEventData : eventHistory.sdm$getActivesEvents()) {
                    EventBase base = EventsRegisters.getEvent(activeEventData.eventID);
                    if(base == null) continue;
                    for (EventFunction function : base.getFunctions()) {
                        function.onEntityInteractEvent(event);
                    }

                    for (EventStructureProperty structureProperty : base.getStructureProperties()) {
                        if (structureProperty.inStructure(player.blockPosition(), player.serverLevel())) {

                            for (EventFunction function : structureProperty.getFunctions()) {
                                function.onEntityInteractEvent(event);
                            }
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void onPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            for (EventFunction function : startedGlobalEvent.getFunctions()) {
                function.onPlayerRespawnEvent(event);

                if(event.getEntity() instanceof ServerPlayer player) {
                    for (EventBase value : EventsRegisters.getEvents().values()) {
                        if(!EventManager.INSTANCE.isStartedEvent(value) || !EventManager.INSTANCE.isStartedEvent(value, player)) {
                            for (EventFunction function1 : value.getFunctions()) {
                                function1.resetEffectFromPlayers(player);
                            }
                        }
                    }
                }
            }

            if(event.getEntity().level() instanceof ServerLevel level) {

                for (EventStructureProperty structureProperty : startedGlobalEvent.getStructureProperties()) {
                    if (structureProperty.inStructure(event.getEntity().blockPosition(), level)) {

                        for (EventFunction function : structureProperty.getFunctions()) {
                            function.onPlayerRespawnEvent(event);
                        }
                    }
                }
            }

        }

        if(EventManager.INSTANCE.startedGlobalEvents.isEmpty()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                for (EventBase value : EventsRegisters.getEvents().values()) {
                    for (EventFunction function : value.getFunctions()) {
                        function.resetEffectFromPlayers(player);
                    }
                }
            }
        }

        if(event.getEntity() instanceof IEventHistory eventHistory && event.getEntity().level() instanceof ServerLevel level) {
            for (ActiveEventData activeEventData : eventHistory.sdm$getActivesEvents()) {
                EventBase base = EventsRegisters.getEvent(activeEventData.eventID);
                if(base == null) continue;
                for (EventFunction function : base.getFunctions()) {
                    function.onPlayerRespawnEvent(event);
                }



                for (EventStructureProperty structureProperty : base.getStructureProperties()) {
                    if (structureProperty.inStructure(event.getEntity().blockPosition(), level)) {

                        for (EventFunction function : structureProperty.getFunctions()) {
                            function.onPlayerRespawnEvent(event);
                        }
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public void onPlayerItemPickupEvent(PlayerEvent.ItemPickupEvent event) {
        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            for (EventFunction function : startedGlobalEvent.getFunctions()) {
                function.onPlayerItemPickupEvent(event);
            }

            if(event.getEntity().level() instanceof ServerLevel level) {

                for (EventStructureProperty structureProperty : startedGlobalEvent.getStructureProperties()) {
                    if (structureProperty.inStructure(event.getEntity().blockPosition(), level)) {

                        for (EventFunction function : structureProperty.getFunctions()) {
                            function.onPlayerItemPickupEvent(event);
                        }
                    }
                }
            }
        }

        if(event.getEntity() instanceof IEventHistory eventHistory && event.getEntity().level() instanceof ServerLevel level) {
            for (ActiveEventData activeEventData : eventHistory.sdm$getActivesEvents()) {
                EventBase base = EventsRegisters.getEvent(activeEventData.eventID);
                if(base == null) continue;
                for (EventFunction function : base.getFunctions()) {
                    function.onPlayerItemPickupEvent(event);
                }



                for (EventStructureProperty structureProperty : base.getStructureProperties()) {
                    if (structureProperty.inStructure(event.getEntity().blockPosition(), level)) {

                        for (EventFunction function : structureProperty.getFunctions()) {
                            function.onPlayerItemPickupEvent(event);
                        }
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public void onAnimalTameEvent(AnimalTameEvent event) {
        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            if(!startedGlobalEvent.properties.canAnimalsTame && !startedGlobalEvent.properties.animalsCantTame.contains(event.getAnimal().getType())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingTickEvent event) {

        if(!event.getEntity().level().isClientSide && event.getEntity().getType().getCategory().isFriendly()) {

            if(event.getEntity().level().isDay()) {

                for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {

                    if(!startedGlobalEvent.properties.canFriendlyStayOnSun) {
                        if (event.getEntity().level().canSeeSky(event.getEntity().blockPosition())) {
                            event.getEntity().setSecondsOnFire(8);
                        }
                    }

                    for (EventFunction function : startedGlobalEvent.getFunctions()) {
                        function.onLivingEntityTickEvent(event);
                    }

                    if(event.getEntity().level() instanceof ServerLevel level) {

                        for (EventStructureProperty structureProperty : startedGlobalEvent.getStructureProperties()) {
                            if (structureProperty.inStructure(event.getEntity().blockPosition(), level)) {

                                for (EventFunction function : structureProperty.getFunctions()) {
                                    function.onLivingEntityTickEvent(event);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {

        if(event.getEntity().level().isClientSide) return;

        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            startedGlobalEvent.sendNotify((ServerPlayer) event.getEntity());
        }

    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {

        if(event.getEntity().level().isClientSide) return;

        for (Map.Entry<ResourceLocation, EventBase> resourceLocationEventBaseEntry : EventsRegisters.getEvents().entrySet()) {
            new SendEndEventS2C(resourceLocationEventBaseEntry.getKey().toString()).sendTo((ServerPlayer) event.getEntity());
        }
    }

    @SubscribeEvent
    public void onBlockBreakEvent(BlockEvent.BreakEvent event) {
        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            for (EventFunction function : startedGlobalEvent.getFunctions()) {
                function.onBlockBreakEvent(event);
            }

            if(event.getPlayer().level() instanceof ServerLevel level) {

                for (EventStructureProperty structureProperty : startedGlobalEvent.getStructureProperties()) {
                    if (structureProperty.inStructure(event.getPlayer().blockPosition(), level)) {

                        for (EventFunction function : structureProperty.getFunctions()) {
                            function.onBlockBreakEvent(event);
                        }
                    }
                }
            }
        }

        if(event.getPlayer() instanceof ServerPlayer player) {
            if(player instanceof IEventHistory eventHistory) {
                for (ActiveEventData activeEventData : eventHistory.sdm$getActivesEvents()) {
                    EventBase base = EventsRegisters.getEvent(activeEventData.eventID);
                    if(base == null) continue;
                    for (EventFunction function : base.getFunctions()) {
                        function.onBlockBreakEvent(event);
                    }

                    for (EventStructureProperty structureProperty : base.getStructureProperties()) {
                        if (structureProperty.inStructure(player.blockPosition(), player.serverLevel())) {

                            for (EventFunction function : structureProperty.getFunctions()) {
                                function.onBlockBreakEvent(event);
                            }
                        }
                    }
                }
            }
        }
    }

}
