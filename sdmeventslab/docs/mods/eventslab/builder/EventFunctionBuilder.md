# EventFunctionBuilder

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.builder.EventFunctionBuilder;
```


## Methods

:::group{name=addEntityInteractEvent}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.addEntityInteractEvent(consumer as Consumer<EntityInteractEvent>) as EventFunctionBuilder
```

| Parameter |                                         Type                                         |
|-----------|--------------------------------------------------------------------------------------|
| consumer  | Consumer&lt;[EntityInteractEvent](/forge/api/event/interact/EntityInteractEvent)&gt; |


:::

:::group{name=addEntitySpawnEvent}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.addEntitySpawnEvent(consumer as Consumer<FinalizeMobSpawnEvent>) as EventFunctionBuilder
```

| Parameter |                                                Type                                                 |
|-----------|-----------------------------------------------------------------------------------------------------|
| consumer  | Consumer&lt;[FinalizeMobSpawnEvent](/forge/api/event/entity/living/spawn/FinalizeMobSpawnEvent)&gt; |


:::

:::group{name=addEventEnd}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.addEventEnd(consumer as Consumer<Server>) as EventFunctionBuilder
```

| Parameter |                        Type                        |
|-----------|----------------------------------------------------|
| consumer  | Consumer&lt;[Server](/vanilla/api/game/Server)&gt; |


:::

:::group{name=addEventStart}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.addEventStart(consumer as Consumer<Server>) as EventFunctionBuilder
```

| Parameter |                        Type                        |
|-----------|----------------------------------------------------|
| consumer  | Consumer&lt;[Server](/vanilla/api/game/Server)&gt; |


:::

:::group{name=addEventTick}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.addEventTick(consumer as Consumer<Server>) as EventFunctionBuilder
```

| Parameter |                        Type                        |
|-----------|----------------------------------------------------|
| consumer  | Consumer&lt;[Server](/vanilla/api/game/Server)&gt; |


:::

:::group{name=addLivingDeathEvent}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.addLivingDeathEvent(consumer as Consumer<LivingDeathEvent>) as EventFunctionBuilder
```

| Parameter |                                        Type                                         |
|-----------|-------------------------------------------------------------------------------------|
| consumer  | Consumer&lt;[LivingDeathEvent](/forge/api/event/entity/living/LivingDeathEvent)&gt; |


:::

:::group{name=addPlayerItemPickupEvent}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.addPlayerItemPickupEvent(consumer as Consumer<ItemPickupEvent>) as EventFunctionBuilder
```

| Parameter |                                   Type                                   |
|-----------|--------------------------------------------------------------------------|
| consumer  | Consumer&lt;[ItemPickupEvent](/forge/api/event/item/ItemPickupEvent)&gt; |


:::

:::group{name=addPlayerRespawnEvent}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.addPlayerRespawnEvent(consumer as Consumer<PlayerRespawnEvent>) as EventFunctionBuilder
```

| Parameter |                                          Type                                           |
|-----------|-----------------------------------------------------------------------------------------|
| consumer  | Consumer&lt;[PlayerRespawnEvent](/forge/api/event/entity/player/PlayerRespawnEvent)&gt; |


:::

:::group{name=addPlayerTickEvent}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.addPlayerTickEvent(consumer as Consumer<PlayerTickEvent>) as EventFunctionBuilder
```

| Parameter |                                   Type                                   |
|-----------|--------------------------------------------------------------------------|
| consumer  | Consumer&lt;[PlayerTickEvent](/forge/api/event/tick/PlayerTickEvent)&gt; |


:::

:::group{name=applyEffectToPlayer}

Return Type: [EventFunctionBuilder](/mods/eventslab/builder/EventFunctionBuilder)

```zenscript
EventFunctionBuilder.applyEffectToPlayer(applyEffectPlayer as Consumer<ServerPlayer>, resetFromPlayer as Consumer<ServerPlayer>) as EventFunctionBuilder
```

|     Parameter     |                                     Type                                     |
|-------------------|------------------------------------------------------------------------------|
| applyEffectPlayer | Consumer&lt;[ServerPlayer](/vanilla/api/entity/type/player/ServerPlayer)&gt; |
| resetFromPlayer   | Consumer&lt;[ServerPlayer](/vanilla/api/entity/type/player/ServerPlayer)&gt; |


:::


