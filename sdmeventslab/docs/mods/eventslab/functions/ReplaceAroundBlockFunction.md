# ReplaceAroundBlockFunction

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.functions.ReplaceAroundBlockFunction;
```


## Extending EventFunction

ReplaceAroundBlockFunction extends [EventFunction](/mods/eventslab/functions/EventFunction). That means all methods available in [EventFunction](/mods/eventslab/functions/EventFunction) are also available in ReplaceAroundBlockFunction

## Constructors


```zenscript
new ReplaceAroundBlockFunction(size as int, predicate as Function<BlockState,BlockState>) as ReplaceAroundBlockFunction
```
| Parameter |                                                  Type                                                   |
|-----------|---------------------------------------------------------------------------------------------------------|
| size      | int                                                                                                     |
| predicate | Function&lt;[BlockState](/vanilla/api/block/BlockState),[BlockState](/vanilla/api/block/BlockState)&gt; |



