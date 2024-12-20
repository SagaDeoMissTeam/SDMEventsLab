# ReplaceAroundEntityFunction

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.functions.ReplaceAroundEntityFunction;
```


## Extending EventFunction

ReplaceAroundEntityFunction extends [EventFunction](/mods/eventslab/functions/EventFunction). That means all methods available in [EventFunction](/mods/eventslab/functions/EventFunction) are also available in ReplaceAroundEntityFunction

## Constructors


```zenscript
new ReplaceAroundEntityFunction(size as int, predicate as Function<Entity,EntityType>) as ReplaceAroundEntityFunction
```
| Parameter |                                               Type                                                |
|-----------|---------------------------------------------------------------------------------------------------|
| size      | int                                                                                               |
| predicate | Function&lt;[Entity](/vanilla/api/entity/Entity),[EntityType](/vanilla/api/entity/EntityType)&gt; |



