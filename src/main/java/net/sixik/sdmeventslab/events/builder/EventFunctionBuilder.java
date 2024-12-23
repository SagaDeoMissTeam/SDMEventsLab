package net.sixik.sdmeventslab.events.builder;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.sixik.sdmeventslab.events.conditions.WeatherCondition;
import net.sixik.sdmeventslab.events.function.*;
import net.sixik.sdmeventslab.events.function.drops.CustomDropFromBlockFunction;
import net.sixik.sdmeventslab.events.function.drops.CustomDropFromEntityFunction;
import net.sixik.sdmeventslab.events.function.integration.botania.ManaCuriosGiveFunction;
import net.sixik.sdmeventslab.events.function.integration.botania.ManaPoolGiveFunction;
import net.sixik.sdmeventslab.events.function.misc.DamageTickFunction;
import net.sixik.sdmeventslab.events.function.misc.PlaySoundFunction;
import net.sixik.sdmeventslab.events.function.misc.PlaySoundPerTickFunction;
import net.sixik.sdmeventslab.events.function.misc.SendMessageFunction;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class EventFunctionBuilder {

    public List<EventFunction> getEventFunctions() {
        return eventFunctions;
    }

    public List<EventCustomFunctionBuilder> getEventCustomFunctions() {
        return eventCustomFunctions;
    }

    private List<EventCustomFunctionBuilder> eventCustomFunctions = new ArrayList<>();
    private final List<EventFunction> eventFunctions = new ArrayList<>();

    public EventFunctionBuilder addCustomFunction(EventCustomFunctionBuilder eventFunction) {
        eventCustomFunctions.add(eventFunction);
        return this;
    }

    public EventFunctionBuilder addGiveAttributeFunction(UUID attributeID, Attribute attribute, double value, AttributeModifier.Operation operation) {
        eventFunctions.add(new GiveAttributeFunction(attributeID, attribute, value, operation));
        return this;
    }

    public EventFunctionBuilder addGiveEffectAroundBlockFunction(int radius, Function<BlockState, MobEffectInstance> predicate) {
        eventFunctions.add(new GiveEffectAroundBlockFunction(radius, predicate));
        return this;
    }

    public EventFunctionBuilder addGiveEffectFunction(MobEffectInstance effectInstance) {
        eventFunctions.add(new GiveEffectFunction(effectInstance));
        return this;
    }

    public EventFunctionBuilder addReplaceAroundBlockFunction(int size, Function<BlockState, @Nullable BlockState> predicate) {
        eventFunctions.add(new ReplaceAroundBlockFunction(size, predicate));
        return this;
    }

    public EventFunctionBuilder addRainDamageFunction(float damage, WeatherCondition.WeatherType type) {
        eventFunctions.add(new RainDamageFunction(damage, type));
        return this;
    }

    public EventFunctionBuilder addRainDamageFunction(float damage, int damageDelay, WeatherCondition.WeatherType type) {
        eventFunctions.add(new RainDamageFunction(damage, type, damageDelay));
        return this;
    }

    public EventFunctionBuilder addRainEffectFunction(MobEffectInstance effect, WeatherCondition.WeatherType type) {
        eventFunctions.add(new RainEffectFunction(effect, type));
        return this;
    }

    public EventFunctionBuilder addRainEffectFunction(MobEffectInstance effect, int checkEffectDelay, WeatherCondition.WeatherType type) {
        eventFunctions.add(new RainEffectFunction(effect, type, checkEffectDelay));
        return this;
    }

    public EventFunctionBuilder addReplaceAroundEntityFunction(int size, Function<Entity, EntityType<?>> predicate) {
        eventFunctions.add(new ReplaceAroundEntityFunction(size, predicate));
        return this;
    }

    public EventFunctionBuilder addReplaceBlockUnderPlayerFunction(Function<BlockState, @Nullable BlockState> predicate) {
        eventFunctions.add(new ReplaceBlockUnderPlayerFunction(predicate));
        return this;
    }

    public EventFunctionBuilder addSpawnReplaceFunction(EntityType<?> toReplaceEntity, EntityType<?>[] replacableTypes) {
        eventFunctions.add(new SpawnReplaceFunction(toReplaceEntity, replacableTypes));
        return this;
    }

    public EventFunctionBuilder addSpawnReplaceWithCustomFunction(EntityType<?> toReplaceEntity, EntityType<?>[] replacableTypes, BiConsumer<MobSpawnEvent.FinalizeSpawn, Entity> consumer) {
        eventFunctions.add(new SpawnReplaceWithCustomFunction(toReplaceEntity, replacableTypes, consumer));
        return this;
    }

    public EventFunctionBuilder addWaterDamageFunction(float damage) {
        eventFunctions.add(new WaterDamageFunction(damage));
        return this;
    }

    public EventFunctionBuilder addWaterDamageFunction(float damage, int damageDelay) {
        eventFunctions.add(new WaterDamageFunction(damage, damageDelay));
        return this;
    }

    public EventFunctionBuilder addWaterEffectFunction(MobEffectInstance effect) {
        eventFunctions.add(new WaterEffectFunction(effect));
        return this;
    }

    public EventFunctionBuilder addWaterEffectFunction(MobEffectInstance effect, int checkEffectDelay) {
        eventFunctions.add(new WaterEffectFunction(effect, checkEffectDelay));
        return this;
    }

    public EventFunctionBuilder addManaCuriosGiveFunction(int minMana, int maxMana) {
        eventFunctions.add(new ManaCuriosGiveFunction(minMana, maxMana));
        return this;
    }

    public EventFunctionBuilder addManaPoolGiveFunction(int minMana, int maxMana) {
        eventFunctions.add(new ManaPoolGiveFunction(minMana, maxMana));
        return this;
    }

    public EventFunctionBuilder addRandomAroundSpawnEntityFunction(int minCount, int maxCount, int radius, EntityType<?> entityType) {
        eventFunctions.add(new RandomAroundSpawnEntityFunction(minCount, maxCount, radius, entityType));
        return this;
    }

    public EventFunctionBuilder addRandomAroundSpawnEntityFunction(int minCount, int maxCount, int radius, EntityType<?> entityType, int timePerSpawn) {
        eventFunctions.add(new RandomAroundSpawnEntityFunction(minCount, maxCount, radius, entityType, timePerSpawn));
        return this;
    }

    public EventFunctionBuilder addRandomAroundSpawnEntityWithCustomFunction(int minCount, int maxCount, int radius, EntityType<?> entityType, Consumer<Entity> consumer) {
        eventFunctions.add(new RandomAroundSpawnEntityWithCustomFunction(minCount, maxCount, radius, entityType, consumer));
        return this;
    }

    public EventFunctionBuilder addRandomAroundSpawnEntityWithCustomFunction(int minCount, int maxCount, int radius, EntityType<?> entityType, int timePerSpawn, Consumer<Entity> consumer) {
        eventFunctions.add(new RandomAroundSpawnEntityWithCustomFunction(minCount, maxCount, radius, entityType, timePerSpawn, consumer));
        return this;
    }

    public EventFunctionBuilder addDropItemFromEntityFunction(ResourceLocation[] entityTypes, Map<ItemStack, Double> contents) {
        eventFunctions.add(new CustomDropFromEntityFunction.ItemDrop(entityTypes, contents));
        return this;
    }

    public EventFunctionBuilder addDropLootTableFromEntityFunction(ResourceLocation[] entityTypes, Map<ResourceLocation, Double> contents) {
        eventFunctions.add(new CustomDropFromEntityFunction.LootTableDrop(entityTypes, contents));
        return this;
    }

    public EventFunctionBuilder addDropItemFromBlockFunction(ResourceLocation[] blocks, Map<ItemStack, Double> contents) {
        eventFunctions.add(new CustomDropFromBlockFunction.ItemDrop(Arrays.stream(blocks).map(s -> {
            Optional<Block> b = BuiltInRegistries.BLOCK.getOptional(s);
            if(b.isEmpty()) return Blocks.AIR.defaultBlockState();
            return b.get().defaultBlockState();
        }).toList(), contents));
        return this;
    }

    public EventFunctionBuilder addDropLootTableFromBlockFunction(ResourceLocation[] blocks, Map<ResourceLocation, Double> contents) {
        eventFunctions.add(new CustomDropFromBlockFunction.LootTableDrop(Arrays.stream(blocks).map(s -> {
            Optional<Block> b = BuiltInRegistries.BLOCK.getOptional(s);
            if(b.isEmpty()) return Blocks.AIR.defaultBlockState();
            return b.get().defaultBlockState();
        }).toList(), contents));
        return this;
    }

    public EventFunctionBuilder addDamageTickFunction(ResourceLocation[] entityTypes, float damage, int damageDelay) {
        eventFunctions.add(new DamageTickFunction(entityTypes, damage, damageDelay));
        return this;
    }

    public EventFunctionBuilder addPlaySoundFunction(SoundEvent soundEvent, SoundSource source, float volume, float pitch, EventFunction.FunctionStage stage) {
        eventFunctions.add(new PlaySoundFunction(soundEvent, source, volume, pitch, stage));
        return this;
    }

    public EventFunctionBuilder addPlaySoundPerTickFunction(SoundEvent soundEvent, SoundSource source, float volume, float pitch, int tick) {
        eventFunctions.add(new PlaySoundPerTickFunction(soundEvent, source, volume, pitch, tick, EventFunction.FunctionStage.TICK));
        return this;
    }

    public EventFunctionBuilder addSendMessageFunction(Component[] text, EventFunction.FunctionStage stage) {
        eventFunctions.add(new SendMessageFunction(text, stage));
        return this;
    }

}
