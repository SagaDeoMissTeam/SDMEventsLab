package net.sixik.sdmeventslab.events.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.conditions.*;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import net.sixik.sdmeventslab.events.function.*;
import net.sixik.sdmeventslab.events.function.integration.botania.ManaCuriosGiveFunction;
import net.sixik.sdmeventslab.events.function.integration.botania.ManaPoolGiveFunction;
import net.sixik.sdmeventslab.register.EventsRegisters;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@ZenRegister
@Document("mods/eventslab/builder/EventBuilder")
@ZenCodeType.Name("mods.eventslab.builder.EventBuilder")
public class EventBuilder {

    @ZenCodeType.Method
    public static EventBuilder createEventString(String eventID, EventBase.EventSide eventSide) {
        return new EventBuilder(new ResourceLocation(eventID), eventSide);
    }

    @ZenCodeType.Method
    public static EventBuilder createEventID(ResourceLocation eventID, EventBase.EventSide eventSide) {
        return new EventBuilder(eventID, eventSide);
    }

    private EventBase eventBase;
    private ResourceLocation eventID;
    private EventBase.EventSide eventSide;

    private List<EventFunctionBuilder> eventFunctions = new ArrayList<>();
    private List<EventConditionBuilder> eventConditions = new ArrayList<>();
    private List<EventEndCondition> eventEndConditions = new ArrayList<>();
    private EventRenderBuilder eventRenderBuilder = new EventRenderBuilder();
    private EventPropertyBuilder eventPropertyBuilder = new EventPropertyBuilder();

    protected EventBuilder(ResourceLocation eventID, EventBase.EventSide eventSide) {
        this.eventID = eventID;
        this.eventSide = eventSide;
        eventBase = new EventBase(this.eventID, this.eventSide);
    }

    @ZenCodeType.Method
    public EventBuilder addEventProperty(EventPropertyBuilder eventPropertyBuilder) {
        this.eventPropertyBuilder = eventPropertyBuilder;
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addRender(EventRenderBuilder renderBuilder) {
        eventRenderBuilder = renderBuilder;
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addEndCondition(String id, Consumer<CompoundTag> serializer, Consumer<CompoundTag> deserializer) {
        eventEndConditions.add(new EventEndConditionBuilder(id, serializer, deserializer).create());
        return this;
    }

    //*****************************
    //              FUNCTIONS
    // *****************************//
    @ZenCodeType.Method
    public EventBuilder addCustomFunction(EventFunctionBuilder eventFunction) {
        eventFunctions.add(eventFunction);
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addGiveAttributeFunction(UUID attributeID, Attribute attribute, double value, AttributeModifier.Operation operation) {
        eventBase.getFunctions().add(new GiveAttributeFunction(attributeID, attribute, value, operation));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addGiveEffectAroundBlockFunction(int radius, Function<BlockState, MobEffectInstance> predicate) {
        eventBase.getFunctions().add(new GiveEffectAroundBlockFunction(radius, predicate));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addGiveEffectFunction(MobEffectInstance effectInstance) {
        eventBase.getFunctions().add(new GiveEffectFunction(effectInstance));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addReplaceAroundBlockFunction(int size, Function<BlockState, @Nullable BlockState> predicate) {
        eventBase.getFunctions().add(new ReplaceAroundBlockFunction(size, predicate));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addRainDamageFunction(float damage, WeatherCondition.WeatherType type) {
        eventBase.getFunctions().add(new RainDamageFunction(damage, type));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addRainDamageFunction(float damage, int damageDelay, WeatherCondition.WeatherType type) {
        eventBase.getFunctions().add(new RainDamageFunction(damage, type, damageDelay));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addRainEffectFunction(MobEffectInstance effect, WeatherCondition.WeatherType type) {
        eventBase.getFunctions().add(new RainEffectFunction(effect, type));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addRainEffectFunction(MobEffectInstance effect, int checkEffectDelay, WeatherCondition.WeatherType type) {
        eventBase.getFunctions().add(new RainEffectFunction(effect, type, checkEffectDelay));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addReplaceAroundEntityFunction(int size, Function<Entity, EntityType<?>> predicate) {
        eventBase.getFunctions().add(new ReplaceAroundEntityFunction(size, predicate));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addReplaceBlockUnderPlayerFunction(Function<BlockState, @Nullable BlockState> predicate) {
        eventBase.getFunctions().add(new ReplaceBlockUnderPlayerFunction(predicate));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addSpawnReplaceFunction(EntityType<?> toReplaceEntity, EntityType<?>[] replacableTypes) {
        eventBase.getFunctions().add(new SpawnReplaceFunction(toReplaceEntity, replacableTypes));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addSpawnReplaceWithCustomFunction(EntityType<?> toReplaceEntity, EntityType<?>[] replacableTypes, BiConsumer<MobSpawnEvent.FinalizeSpawn, Entity> consumer) {
        eventBase.getFunctions().add(new SpawnReplaceWithCustomFunction(toReplaceEntity, replacableTypes, consumer));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addWaterDamageFunction(float damage) {
        eventBase.getFunctions().add(new WaterDamageFunction(damage));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addWaterDamageFunction(float damage, int damageDelay) {
        eventBase.getFunctions().add(new WaterDamageFunction(damage, damageDelay));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addWaterEffectFunction(MobEffectInstance effect) {
        eventBase.getFunctions().add(new WaterEffectFunction(effect));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addWaterEffectFunction(MobEffectInstance effect, int checkEffectDelay) {
        eventBase.getFunctions().add(new WaterEffectFunction(effect, checkEffectDelay));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addManaCuriosGiveFunction(int minMana, int maxMana) {
        eventBase.getFunctions().add(new ManaCuriosGiveFunction(minMana, maxMana));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addManaPoolGiveFunction(int minMana, int maxMana) {
        eventBase.getFunctions().add(new ManaPoolGiveFunction(minMana, maxMana));
        return this;
    }

    //*****************************
    //              CONDITIONS
    // *****************************//
    @ZenCodeType.Method
    public EventBuilder addCustomCondition(EventConditionBuilder eventCondition) {
        eventConditions.add(eventCondition);
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addDayCondition(long day, EventCondition.ConditionType predicate) {
        eventBase.getConditions().add(new DayCondition(day, predicate));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addBiomeCondition(ResourceLocation biomeID) {
        eventBase.getConditions().add(new BiomeCondition(biomeID).setEvent(eventBase));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addDimensionCondition(ResourceLocation dimensionID) {
        eventBase.getConditions().add(new DimensionCondition(dimensionID).setEvent(eventBase));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addWeatherCondition(WeatherCondition.WeatherType type) {
        eventBase.getConditions().add(new WeatherCondition(type).setEvent(eventBase));
        return this;
    }

    @ZenCodeType.Method
    public EventBuilder addPlayerOnlineCondition(int countPlayers, EventCondition.ConditionType type) {
        eventBase.getConditions().add(new PlayerOnlineCondition(countPlayers, type).setEvent(eventBase));
        return this;
    }

    @ZenCodeType.Method
    public EventBase create() {

        eventConditions.stream().map(EventConditionBuilder::create).map(s -> s.setEvent(eventBase)).forEach(eventBase.getConditions()::add);
        eventFunctions.stream().map(s -> {
            s.checkSide(eventSide);
            return s.create();
        }).map(s -> s.setEvent(eventBase)).forEach(eventBase.getFunctions()::add);
        eventEndConditions.forEach(eventBase.getEndConditions()::add);

        eventBase.getEventRenders().addAll(eventRenderBuilder.create(eventBase).getEventRenders());

        eventBase.renderProperty = eventPropertyBuilder.createRenderProperty();
        eventBase.properties = eventPropertyBuilder.createEventProperty();

        System.out.println("SDM TEST REGISTER EVENTY" + eventBase.getEventID().toString());

        return EventsRegisters.registerEvent(eventBase);
    }
}
