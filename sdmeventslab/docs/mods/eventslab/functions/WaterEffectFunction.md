# WaterEffectFunction

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.functions.WaterEffectFunction;
```


## Extending EventFunction

WaterEffectFunction extends [EventFunction](/mods/eventslab/functions/EventFunction). That means all methods available in [EventFunction](/mods/eventslab/functions/EventFunction) are also available in WaterEffectFunction

## Constructors


```zenscript
new WaterEffectFunction(effect as MobEffectInstance) as WaterEffectFunction
```
| Parameter |                               Type                                |
|-----------|-------------------------------------------------------------------|
| effect    | [MobEffectInstance](/vanilla/api/entity/effect/MobEffectInstance) |



```zenscript
new WaterEffectFunction(effect as MobEffectInstance, checkEffectDelay as int) as WaterEffectFunction
```
|    Parameter     |                               Type                                |
|------------------|-------------------------------------------------------------------|
| effect           | [MobEffectInstance](/vanilla/api/entity/effect/MobEffectInstance) |
| checkEffectDelay | int                                                               |



