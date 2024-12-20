# GiveEffectAroundBlockFunction

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.functions.GiveEffectAroundBlockFunction;
```


## Extending EventFunction

GiveEffectAroundBlockFunction extends [EventFunction](/mods/eventslab/functions/EventFunction). That means all methods available in [EventFunction](/mods/eventslab/functions/EventFunction) are also available in GiveEffectAroundBlockFunction

## Constructors


```zenscript
new GiveEffectAroundBlockFunction(radius as int, predicate as Function<BlockState,MobEffectInstance>) as GiveEffectAroundBlockFunction
```
| Parameter |                                                             Type                                                              |
|-----------|-------------------------------------------------------------------------------------------------------------------------------|
| radius    | int                                                                                                                           |
| predicate | Function&lt;[BlockState](/vanilla/api/block/BlockState),[MobEffectInstance](/vanilla/api/entity/effect/MobEffectInstance)&gt; |



