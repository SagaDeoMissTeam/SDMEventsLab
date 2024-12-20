# SpawnReplaceWithCustomFunction

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.functions.SpawnReplaceWithCustomFunction;
```


## Extending SpawnReplaceFunction

SpawnReplaceWithCustomFunction extends [SpawnReplaceFunction](/mods/eventslab/functions/SpawnReplaceFunction). That means all methods available in [SpawnReplaceFunction](/mods/eventslab/functions/SpawnReplaceFunction) are also available in SpawnReplaceWithCustomFunction

## Constructors


```zenscript
new SpawnReplaceWithCustomFunction(toReplaceEntity as EntityType, replacableTypes as EntityType[], consumer as BiConsumer<FinalizeMobSpawnEvent,Entity>) as SpawnReplaceWithCustomFunction
```
|    Parameter    |                                                                    Type                                                                    |
|-----------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| toReplaceEntity | [EntityType](/vanilla/api/entity/EntityType)                                                                                               |
| replacableTypes | [EntityType](/vanilla/api/entity/EntityType)[]                                                                                             |
| consumer        | BiConsumer&lt;[FinalizeMobSpawnEvent](/forge/api/event/entity/living/spawn/FinalizeMobSpawnEvent),[Entity](/vanilla/api/entity/Entity)&gt; |



