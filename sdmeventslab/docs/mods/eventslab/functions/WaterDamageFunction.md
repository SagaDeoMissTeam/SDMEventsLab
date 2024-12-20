# WaterDamageFunction

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.functions.WaterDamageFunction;
```


## Extending EventFunction

WaterDamageFunction extends [EventFunction](/mods/eventslab/functions/EventFunction). That means all methods available in [EventFunction](/mods/eventslab/functions/EventFunction) are also available in WaterDamageFunction

## Constructors


```zenscript
new WaterDamageFunction(damage as float) as WaterDamageFunction
```
| Parameter | Type  |
|-----------|-------|
| damage    | float |



```zenscript
new WaterDamageFunction(damage as float, damageDelay as int) as WaterDamageFunction
```
|  Parameter  | Type  |
|-------------|-------|
| damage      | float |
| damageDelay | int   |



