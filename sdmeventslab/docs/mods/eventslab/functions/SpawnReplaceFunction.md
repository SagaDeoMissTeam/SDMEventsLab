# SpawnReplaceFunction

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.functions.SpawnReplaceFunction;
```


## Extending EventFunction

SpawnReplaceFunction extends [EventFunction](/mods/eventslab/functions/EventFunction). That means all methods available in [EventFunction](/mods/eventslab/functions/EventFunction) are also available in SpawnReplaceFunction

## Constructors


```zenscript
new SpawnReplaceFunction(toReplaceEntity as EntityType, replacableTypes as EntityType[]) as SpawnReplaceFunction
```
|    Parameter    |                      Type                      |
|-----------------|------------------------------------------------|
| toReplaceEntity | [EntityType](/vanilla/api/entity/EntityType)   |
| replacableTypes | [EntityType](/vanilla/api/entity/EntityType)[] |



