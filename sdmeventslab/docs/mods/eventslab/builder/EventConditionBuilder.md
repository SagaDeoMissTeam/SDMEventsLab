# EventConditionBuilder

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.builder.EventConditionBuilder;
```


## Constructors


```zenscript
new EventConditionBuilder() as EventConditionBuilder
new EventConditionBuilder();
```

## Methods

:::group{name=addGlobal}

Return Type: [EventConditionBuilder](/mods/eventslab/builder/EventConditionBuilder)

```zenscript
EventConditionBuilder.addGlobal(consumer as Function<Server,bool?>) as EventConditionBuilder
```

| Parameter |                           Type                           |
|-----------|----------------------------------------------------------|
| consumer  | Function&lt;[Server](/vanilla/api/game/Server),bool?&gt; |


:::

:::group{name=addLocal}

Return Type: [EventConditionBuilder](/mods/eventslab/builder/EventConditionBuilder)

```zenscript
EventConditionBuilder.addLocal(consumer as Function<ServerPlayer,bool?>) as EventConditionBuilder
```

| Parameter |                                        Type                                        |
|-----------|------------------------------------------------------------------------------------|
| consumer  | Function&lt;[ServerPlayer](/vanilla/api/entity/type/player/ServerPlayer),bool?&gt; |


:::


