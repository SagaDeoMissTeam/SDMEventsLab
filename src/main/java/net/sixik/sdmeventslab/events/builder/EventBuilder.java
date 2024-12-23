package net.sixik.sdmeventslab.events.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.builder.property.EventBiomePropertyBuilder;
import net.sixik.sdmeventslab.events.builder.property.EventDimensionPropertyBuilder;
import net.sixik.sdmeventslab.events.builder.property.EventPropertyBuilder;
import net.sixik.sdmeventslab.events.builder.property.EventStructurePropertyBuilder;
import net.sixik.sdmeventslab.events.conditions.*;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import net.sixik.sdmeventslab.events.function.*;
import net.sixik.sdmeventslab.events.function.drops.CustomDropFromBlockFunction;
import net.sixik.sdmeventslab.events.function.drops.CustomDropFromEntityFunction;
import net.sixik.sdmeventslab.events.function.integration.botania.ManaCuriosGiveFunction;
import net.sixik.sdmeventslab.events.function.integration.botania.ManaPoolGiveFunction;
import net.sixik.sdmeventslab.events.function.misc.DamageTickFunction;
import net.sixik.sdmeventslab.events.function.misc.PlaySoundFunction;
import net.sixik.sdmeventslab.events.function.misc.PlaySoundPerTickFunction;
import net.sixik.sdmeventslab.events.function.misc.SendMessageFunction;
import net.sixik.sdmeventslab.register.EventsRegisters;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@ZenRegister
@Document("mods/eventslab/builder/EventBuilder")
@ZenCodeType.Name("mods.eventslab.builder.EventBuilder")
public class EventBuilder {

    public static EventBuilder createEventString(String eventID, EventBase.EventSide eventSide) {
        return new EventBuilder(new ResourceLocation(eventID), eventSide);
    }

    public static EventBuilder createEventID(ResourceLocation eventID, EventBase.EventSide eventSide) {
        return new EventBuilder(eventID, eventSide);
    }

    private EventBase eventBase;
    private ResourceLocation eventID;
    private EventBase.EventSide eventSide;

    private List<EventFunctionBuilder> functionBuilders = new ArrayList<>();
    private List<EventCustomFunctionBuilder> eventFunctions = new ArrayList<>();
    private List<EventConditionBuilder> eventConditions = new ArrayList<>();
    private List<EventEndCondition> eventEndConditions = new ArrayList<>();
    private EventRenderBuilder eventRenderBuilder = new EventRenderBuilder();

    private EventPropertyBuilder eventPropertyBuilder = new EventPropertyBuilder();
    private final List<EventStructurePropertyBuilder> eventStructurePropertyBuilder = new ArrayList<>();
    private final List<EventDimensionPropertyBuilder> eventDimensionPropertyBuilder = new ArrayList<>();
    private final List<EventBiomePropertyBuilder> eventBiomePropertyBuilder = new ArrayList<>();

    protected EventBuilder(ResourceLocation eventID, EventBase.EventSide eventSide) {
        this.eventID = eventID;
        this.eventSide = eventSide;
        eventBase = new EventBase(this.eventID, this.eventSide);
    }

    public EventBuilder addFunction(EventFunctionBuilder function) {
        functionBuilders.add(function);
        return this;
    }

    public EventBuilder addEventProperty(EventPropertyBuilder eventPropertyBuilder) {
        this.eventPropertyBuilder = eventPropertyBuilder;
        return this;
    }

    public EventBuilder addRender(EventRenderBuilder renderBuilder) {
        eventRenderBuilder = renderBuilder;
        return this;
    }

    public EventBuilder addEndCondition(String id, Function<MinecraftServer, Boolean> consumer, Function<ServerPlayer, Boolean> consumer1) {
        eventEndConditions.add(new EventEndConditionBuilder(id).addCanEndLocal(consumer1).addCanEndGlobal(consumer).create());
        return this;
    }

    public EventBuilder addStructureProperty(EventStructurePropertyBuilder builder) {
        eventStructurePropertyBuilder.add(builder);
        return this;
    }

    public EventBuilder addDimensionProperty(EventDimensionPropertyBuilder builder) {
        eventDimensionPropertyBuilder.add(builder);
        return this;
    }

    public EventBuilder addBiomeProperty(EventBiomePropertyBuilder builder) {
        eventBiomePropertyBuilder.add(builder);
        return this;
    }

    //*****************************
    //              FUNCTIONS
    // *****************************//
    public EventBuilder addCustomFunction(EventCustomFunctionBuilder eventFunction) {
        eventFunctions.add(eventFunction);
        return this;
    }

    public EventBuilder addGiveAttributeFunction(UUID attributeID, Attribute attribute, double value, AttributeModifier.Operation operation) {
        eventBase.getFunctions().add(new GiveAttributeFunction(attributeID, attribute, value, operation));
        return this;
    }

    public EventBuilder addGiveEffectAroundBlockFunction(int radius, Function<BlockState, MobEffectInstance> predicate) {
        eventBase.getFunctions().add(new GiveEffectAroundBlockFunction(radius, predicate));
        return this;
    }

    public EventBuilder addGiveEffectFunction(MobEffectInstance effectInstance) {
        eventBase.getFunctions().add(new GiveEffectFunction(effectInstance));
        return this;
    }

    public EventBuilder addReplaceAroundBlockFunction(int size, Function<BlockState, @Nullable BlockState> predicate) {
        eventBase.getFunctions().add(new ReplaceAroundBlockFunction(size, predicate));
        return this;
    }

    public EventBuilder addRainDamageFunction(float damage, WeatherCondition.WeatherType type) {
        eventBase.getFunctions().add(new RainDamageFunction(damage, type));
        return this;
    }

    public EventBuilder addRainDamageFunction(float damage, int damageDelay, WeatherCondition.WeatherType type) {
        eventBase.getFunctions().add(new RainDamageFunction(damage, type, damageDelay));
        return this;
    }

    public EventBuilder addRainEffectFunction(MobEffectInstance effect, WeatherCondition.WeatherType type) {
        eventBase.getFunctions().add(new RainEffectFunction(effect, type));
        return this;
    }

    public EventBuilder addRainEffectFunction(MobEffectInstance effect, int checkEffectDelay, WeatherCondition.WeatherType type) {
        eventBase.getFunctions().add(new RainEffectFunction(effect, type, checkEffectDelay));
        return this;
    }

    public EventBuilder addReplaceAroundEntityFunction(int size, Function<Entity, EntityType<?>> predicate) {
        eventBase.getFunctions().add(new ReplaceAroundEntityFunction(size, predicate));
        return this;
    }

    public EventBuilder addReplaceBlockUnderPlayerFunction(Function<BlockState, @Nullable BlockState> predicate) {
        eventBase.getFunctions().add(new ReplaceBlockUnderPlayerFunction(predicate));
        return this;
    }

    public EventBuilder addSpawnReplaceFunction(EntityType<?> toReplaceEntity, EntityType<?>[] replacableTypes) {
        eventBase.getFunctions().add(new SpawnReplaceFunction(toReplaceEntity, replacableTypes));
        return this;
    }

    public EventBuilder addSpawnReplaceWithCustomFunction(EntityType<?> toReplaceEntity, EntityType<?>[] replacableTypes, BiConsumer<MobSpawnEvent.FinalizeSpawn, Entity> consumer) {
        eventBase.getFunctions().add(new SpawnReplaceWithCustomFunction(toReplaceEntity, replacableTypes, consumer));
        return this;
    }

    public EventBuilder addWaterDamageFunction(float damage) {
        eventBase.getFunctions().add(new WaterDamageFunction(damage));
        return this;
    }

    public EventBuilder addWaterDamageFunction(float damage, int damageDelay) {
        eventBase.getFunctions().add(new WaterDamageFunction(damage, damageDelay));
        return this;
    }

    public EventBuilder addWaterEffectFunction(MobEffectInstance effect) {
        eventBase.getFunctions().add(new WaterEffectFunction(effect));
        return this;
    }

    public EventBuilder addWaterEffectFunction(MobEffectInstance effect, int checkEffectDelay) {
        eventBase.getFunctions().add(new WaterEffectFunction(effect, checkEffectDelay));
        return this;
    }

    public EventBuilder addManaCuriosGiveFunction(int minMana, int maxMana) {
        eventBase.getFunctions().add(new ManaCuriosGiveFunction(minMana, maxMana));
        return this;
    }

    public EventBuilder addManaPoolGiveFunction(int minMana, int maxMana) {
        eventBase.getFunctions().add(new ManaPoolGiveFunction(minMana, maxMana));
        return this;
    }

    public EventBuilder addRandomAroundSpawnEntityFunction(int minCount, int maxCount, int radius, EntityType<?> entityType) {
        eventBase.getFunctions().add(new RandomAroundSpawnEntityFunction(minCount, maxCount, radius, entityType));
        return this;
    }

    public EventBuilder addRandomAroundSpawnEntityFunction(int minCount, int maxCount, int radius, EntityType<?> entityType, int timePerSpawn) {
        eventBase.getFunctions().add(new RandomAroundSpawnEntityFunction(minCount, maxCount, radius, entityType, timePerSpawn));
        return this;
    }

    public EventBuilder addRandomAroundSpawnEntityWithCustomFunction(int minCount, int maxCount, int radius, EntityType<?> entityType, Consumer<Entity> consumer) {
        eventBase.getFunctions().add(new RandomAroundSpawnEntityWithCustomFunction(minCount, maxCount, radius, entityType, consumer));
        return this;
    }

    public EventBuilder addRandomAroundSpawnEntityWithCustomFunction(int minCount, int maxCount, int radius, EntityType<?> entityType, int timePerSpawn, Consumer<Entity> consumer) {
        eventBase.getFunctions().add(new RandomAroundSpawnEntityWithCustomFunction(minCount, maxCount, radius, entityType, timePerSpawn, consumer));
        return this;
    }

    public EventBuilder addDropItemFromEntityFunction(ResourceLocation[] entityTypes, Map<ItemStack, Double> contents) {
        eventBase.getFunctions().add(new CustomDropFromEntityFunction.ItemDrop(entityTypes, contents));
        return this;
    }

    public EventBuilder addDropLootTableFromEntityFunction(ResourceLocation[] entityTypes, Map<ResourceLocation, Double> contents) {
        eventBase.getFunctions().add(new CustomDropFromEntityFunction.LootTableDrop(entityTypes, contents));
        return this;
    }

    public EventBuilder addDropItemFromBlockFunction(ResourceLocation[] blocks, Map<ItemStack, Double> contents) {
        eventBase.getFunctions().add(new CustomDropFromBlockFunction.ItemDrop(Arrays.stream(blocks).map(s -> {
            Optional<Block> b = BuiltInRegistries.BLOCK.getOptional(s);
            if(b.isEmpty()) return Blocks.AIR.defaultBlockState();
            return b.get().defaultBlockState();
        }).toList(), contents));
        return this;
    }

    public EventBuilder addDropLootTableFromBlockFunction(ResourceLocation[] blocks, Map<ResourceLocation, Double> contents) {
        eventBase.getFunctions().add(new CustomDropFromBlockFunction.LootTableDrop(Arrays.stream(blocks).map(s -> {
            Optional<Block> b = BuiltInRegistries.BLOCK.getOptional(s);
            if(b.isEmpty()) return Blocks.AIR.defaultBlockState();
            return b.get().defaultBlockState();
        }).toList(), contents));
        return this;
    }

    public EventBuilder addDamageTickFunction(ResourceLocation[] entityTypes, float damage, int damageDelay) {
        eventBase.getFunctions().add(new DamageTickFunction(entityTypes, damage, damageDelay));
        return this;
    }

    public EventBuilder addPlaySoundFunction(SoundEvent soundEvent, SoundSource source, float volume, float pitch, EventFunction.FunctionStage stage) {
        eventBase.getFunctions().add(new PlaySoundFunction(soundEvent, source, volume, pitch, stage));
        return this;
    }

    public EventBuilder addPlaySoundPerTickFunction(SoundEvent soundEvent, SoundSource source, float volume, float pitch, int tick) {
        eventBase.getFunctions().add(new PlaySoundPerTickFunction(soundEvent, source, volume, pitch, tick, EventFunction.FunctionStage.TICK));
        return this;
    }

    public EventBuilder addSendMessageFunction(Component[] text, EventFunction.FunctionStage stage) {
        eventBase.getFunctions().add(new SendMessageFunction(text, stage));
        return this;
    }

    //*****************************
    //              CONDITIONS
    // *****************************//
    public EventBuilder addCustomCondition(EventConditionBuilder eventCondition) {
        eventConditions.add(eventCondition);
        return this;
    }

    public EventBuilder addDayCondition(long day, EventCondition.ConditionType predicate) {
        eventBase.getConditions().add(new DayCondition(day, predicate));
        return this;
    }

    public EventBuilder addBiomeCondition(ResourceLocation biomeID) {
        eventBase.getConditions().add(new BiomeCondition(biomeID).setEvent(eventBase));
        return this;
    }

    public EventBuilder addDimensionCondition(ResourceLocation dimensionID) {
        eventBase.getConditions().add(new DimensionCondition(dimensionID).setEvent(eventBase));
        return this;
    }

    public EventBuilder addWeatherCondition(WeatherCondition.WeatherType type) {
        eventBase.getConditions().add(new WeatherCondition(type).setEvent(eventBase));
        return this;
    }

    public EventBuilder addPlayerOnlineCondition(int countPlayers, EventCondition.ConditionType type) {
        eventBase.getConditions().add(new PlayerOnlineCondition(countPlayers, type).setEvent(eventBase));
        return this;
    }

    public EventBase create() {

        eventBase.getFunctions().forEach(s -> s.setEvent(eventBase));

        for (EventFunctionBuilder functionBuilder : functionBuilders) {
            functionBuilder.getEventFunctions().stream().map(s -> s.setEvent(eventBase)).forEach(eventBase.getFunctions()::add);
            functionBuilder.getEventCustomFunctions().stream().map(EventCustomFunctionBuilder::create).map(s -> s.setEvent(eventBase)).forEach(eventBase.getFunctions()::add);
        }


        eventFunctions.stream().map(s -> {
            s.checkSide(eventSide);
            return s.create();
        }).map(s -> s.setEvent(eventBase)).forEach(eventBase.getFunctions()::add);

        eventConditions.stream().map(EventConditionBuilder::create).map(s -> s.setEvent(eventBase)).forEach(eventBase.getConditions()::add);

        eventEndConditions.forEach(eventBase.getEndConditions()::add);

        eventBase.getEventRenders().addAll(eventRenderBuilder.create(eventBase).getEventRenders());

        eventBase.renderProperty = eventPropertyBuilder.createRenderProperty();
        eventBase.properties = eventPropertyBuilder.createEventProperty();

        eventStructurePropertyBuilder
                .stream()
                .map(EventStructurePropertyBuilder::create)
                .map(s -> s.setEventBase(eventBase))
                .forEach(eventBase.getStructureProperties()::add);

        eventDimensionPropertyBuilder
                .stream()
                .map(EventDimensionPropertyBuilder::create)
                .map(s -> s.setEventBase(eventBase))
                .forEach(eventBase.getDimensionProperties()::add);

        eventBiomePropertyBuilder
                .stream()
                .map(EventBiomePropertyBuilder::create)
                .map(s -> s.setEventBase(eventBase))
                .forEach(eventBase.getBiomeProperties()::add);

        return EventsRegisters.registerEvent(eventBase);
    }
}
