# EventBuilder

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.builder.EventBuilder;
```


## Static Methods

:::group{name=createEventID}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.createEventID(eventID as ResourceLocation, eventSide as EventSide) as EventBuilder
```

| Parameter |                            Type                            |
|-----------|------------------------------------------------------------|
| eventID   | [ResourceLocation](/vanilla/api/resource/ResourceLocation) |
| eventSide | [EventSide](/mods/eventslab/EventSide)                     |


:::

:::group{name=createEventString}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.createEventString(eventID as string, eventSide as EventSide) as EventBuilder
```

| Parameter |                  Type                  |
|-----------|----------------------------------------|
| eventID   | string                                 |
| eventSide | [EventSide](/mods/eventslab/EventSide) |


:::

## Methods

:::group{name=addBiomeCondition}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addBiomeCondition(biomeID as ResourceLocation) as EventBuilder
```

| Parameter |                            Type                            |
|-----------|------------------------------------------------------------|
| biomeID   | [ResourceLocation](/vanilla/api/resource/ResourceLocation) |


:::

:::group{name=addCustomCondition}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addCustomCondition(eventCondition as EventConditionBuilder) as EventBuilder
```

|   Parameter    |                                  Type                                  |
|----------------|------------------------------------------------------------------------|
| eventCondition | [EventConditionBuilder](/mods/eventslab/builder/EventConditionBuilder) |


:::

:::group{name=addCustomFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addCustomFunction(eventFunction as EventFunctionBuilder) as EventBuilder
```

|   Parameter   |                                 Type                                 |
|---------------|----------------------------------------------------------------------|
| eventFunction | [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder) |


:::

:::group{name=addDayCondition}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addDayCondition(day as long, predicate as invalid) as EventBuilder
```

| Parameter |    Type     |
|-----------|-------------|
| day       | long        |
| predicate | **invalid** |


:::

:::group{name=addDimensionCondition}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addDimensionCondition(dimensionID as ResourceLocation) as EventBuilder
```

|  Parameter  |                            Type                            |
|-------------|------------------------------------------------------------|
| dimensionID | [ResourceLocation](/vanilla/api/resource/ResourceLocation) |


:::

:::group{name=addEndCondition}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addEndCondition(id as string, serializer as Consumer, deserializer as Consumer) as EventBuilder
```

|  Parameter   |   Type   |
|--------------|----------|
| id           | string   |
| serializer   | Consumer |
| deserializer | Consumer |


:::

:::group{name=addEventProperty}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addEventProperty(eventPropertyBuilder as EventPropertyBuilder) as EventBuilder
```

|      Parameter       |                                 Type                                 |
|----------------------|----------------------------------------------------------------------|
| eventPropertyBuilder | [EventPropertyBuilder](/mods/eventslab/builder/EventPropertyBuilder) |


:::

:::group{name=addGiveAttributeFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addGiveAttributeFunction(attributeID as invalid, attribute as Attribute, value as double, operation as AttributeOperation) as EventBuilder
```

|  Parameter  |                                  Type                                  |
|-------------|------------------------------------------------------------------------|
| attributeID | **invalid**                                                            |
| attribute   | [Attribute](/vanilla/api/entity/attribute/Attribute)                   |
| value       | double                                                                 |
| operation   | [AttributeOperation](/vanilla/api/entity/attribute/AttributeOperation) |


:::

:::group{name=addGiveEffectAroundBlockFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addGiveEffectAroundBlockFunction(radius as int, predicate as Function<BlockState,MobEffectInstance>) as EventBuilder
```

| Parameter |                                                             Type                                                              |
|-----------|-------------------------------------------------------------------------------------------------------------------------------|
| radius    | int                                                                                                                           |
| predicate | Function&lt;[BlockState](/vanilla/api/block/BlockState),[MobEffectInstance](/vanilla/api/entity/effect/MobEffectInstance)&gt; |


:::

:::group{name=addGiveEffectFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addGiveEffectFunction(effectInstance as MobEffectInstance) as EventBuilder
```

|   Parameter    |                               Type                                |
|----------------|-------------------------------------------------------------------|
| effectInstance | [MobEffectInstance](/vanilla/api/entity/effect/MobEffectInstance) |


:::

:::group{name=addManaCuriosGiveFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addManaCuriosGiveFunction(minMana as int, maxMana as int) as EventBuilder
```

| Parameter | Type |
|-----------|------|
| minMana   | int  |
| maxMana   | int  |


:::

:::group{name=addManaPoolGiveFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addManaPoolGiveFunction(minMana as int, maxMana as int) as EventBuilder
```

| Parameter | Type |
|-----------|------|
| minMana   | int  |
| maxMana   | int  |


:::

:::group{name=addPlayerOnlineCondition}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addPlayerOnlineCondition(countPlayers as int, type as invalid) as EventBuilder
```

|  Parameter   |    Type     |
|--------------|-------------|
| countPlayers | int         |
| type         | **invalid** |


:::

:::group{name=addRainDamageFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addRainDamageFunction(damage as float, type as WeatherType) as EventBuilder
```

| Parameter |                         Type                          |
|-----------|-------------------------------------------------------|
| damage    | float                                                 |
| type      | [WeatherType](/mods/eventslab/conditions/WeatherType) |


:::

:::group{name=addRainDamageFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addRainDamageFunction(damage as float, damageDelay as int, type as WeatherType) as EventBuilder
```

|  Parameter  |                         Type                          |
|-------------|-------------------------------------------------------|
| damage      | float                                                 |
| damageDelay | int                                                   |
| type        | [WeatherType](/mods/eventslab/conditions/WeatherType) |


:::

:::group{name=addRainEffectFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addRainEffectFunction(effect as MobEffectInstance, type as WeatherType) as EventBuilder
```

| Parameter |                               Type                                |
|-----------|-------------------------------------------------------------------|
| effect    | [MobEffectInstance](/vanilla/api/entity/effect/MobEffectInstance) |
| type      | [WeatherType](/mods/eventslab/conditions/WeatherType)             |


:::

:::group{name=addRainEffectFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addRainEffectFunction(effect as MobEffectInstance, checkEffectDelay as int, type as WeatherType) as EventBuilder
```

|    Parameter     |                               Type                                |
|------------------|-------------------------------------------------------------------|
| effect           | [MobEffectInstance](/vanilla/api/entity/effect/MobEffectInstance) |
| checkEffectDelay | int                                                               |
| type             | [WeatherType](/mods/eventslab/conditions/WeatherType)             |


:::

:::group{name=addRender}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addRender(renderBuilder as EventRenderBuilder) as EventBuilder
```

|   Parameter   |                               Type                               |
|---------------|------------------------------------------------------------------|
| renderBuilder | [EventRenderBuilder](/mods/eventslab/builder/EventRenderBuilder) |


:::

:::group{name=addReplaceAroundBlockFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addReplaceAroundBlockFunction(size as int, predicate as Function<BlockState,BlockState>) as EventBuilder
```

| Parameter |                                                  Type                                                   |
|-----------|---------------------------------------------------------------------------------------------------------|
| size      | int                                                                                                     |
| predicate | Function&lt;[BlockState](/vanilla/api/block/BlockState),[BlockState](/vanilla/api/block/BlockState)&gt; |


:::

:::group{name=addReplaceAroundEntityFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addReplaceAroundEntityFunction(size as int, predicate as Function<Entity,EntityType>) as EventBuilder
```

| Parameter |                                               Type                                                |
|-----------|---------------------------------------------------------------------------------------------------|
| size      | int                                                                                               |
| predicate | Function&lt;[Entity](/vanilla/api/entity/Entity),[EntityType](/vanilla/api/entity/EntityType)&gt; |


:::

:::group{name=addReplaceBlockUnderPlayerFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addReplaceBlockUnderPlayerFunction(predicate as Function<BlockState,BlockState>) as EventBuilder
```

| Parameter |                                                  Type                                                   |
|-----------|---------------------------------------------------------------------------------------------------------|
| predicate | Function&lt;[BlockState](/vanilla/api/block/BlockState),[BlockState](/vanilla/api/block/BlockState)&gt; |


:::

:::group{name=addSpawnReplaceFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addSpawnReplaceFunction(toReplaceEntity as EntityType, replacableTypes as EntityType[]) as EventBuilder
```

|    Parameter    |                      Type                      |
|-----------------|------------------------------------------------|
| toReplaceEntity | [EntityType](/vanilla/api/entity/EntityType)   |
| replacableTypes | [EntityType](/vanilla/api/entity/EntityType)[] |


:::

:::group{name=addSpawnReplaceWithCustomFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addSpawnReplaceWithCustomFunction(toReplaceEntity as EntityType, replacableTypes as EntityType[], consumer as BiConsumer<FinalizeMobSpawnEvent,Entity>) as EventBuilder
```

|    Parameter    |                                                                    Type                                                                    |
|-----------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| toReplaceEntity | [EntityType](/vanilla/api/entity/EntityType)                                                                                               |
| replacableTypes | [EntityType](/vanilla/api/entity/EntityType)[]                                                                                             |
| consumer        | BiConsumer&lt;[FinalizeMobSpawnEvent](/forge/api/event/entity/living/spawn/FinalizeMobSpawnEvent),[Entity](/vanilla/api/entity/Entity)&gt; |


:::

:::group{name=addWaterDamageFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addWaterDamageFunction(damage as float) as EventBuilder
```

| Parameter | Type  |
|-----------|-------|
| damage    | float |


:::

:::group{name=addWaterDamageFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addWaterDamageFunction(damage as float, damageDelay as int) as EventBuilder
```

|  Parameter  | Type  |
|-------------|-------|
| damage      | float |
| damageDelay | int   |


:::

:::group{name=addWaterEffectFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addWaterEffectFunction(effect as MobEffectInstance) as EventBuilder
```

| Parameter |                               Type                                |
|-----------|-------------------------------------------------------------------|
| effect    | [MobEffectInstance](/vanilla/api/entity/effect/MobEffectInstance) |


:::

:::group{name=addWaterEffectFunction}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addWaterEffectFunction(effect as MobEffectInstance, checkEffectDelay as int) as EventBuilder
```

|    Parameter     |                               Type                                |
|------------------|-------------------------------------------------------------------|
| effect           | [MobEffectInstance](/vanilla/api/entity/effect/MobEffectInstance) |
| checkEffectDelay | int                                                               |


:::

:::group{name=addWeatherCondition}

Return Type: [EventBuilder](/mods/eventslab/builder/EventBuilder)

```zenscript
EventBuilder.addWeatherCondition(type as WeatherType) as EventBuilder
```

| Parameter |                         Type                          |
|-----------|-------------------------------------------------------|
| type      | [WeatherType](/mods/eventslab/conditions/WeatherType) |


:::

:::group{name=create}

Return Type: [EventBase](/mods/eventslab/EventBase)

```zenscript
// EventBuilder.create() as EventBase

myEventBuilder.create();
```

:::


