# GiveAttributeFunction

## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.eventslab.functions.GiveAttributeFunction;
```


## Extending EventFunction

GiveAttributeFunction extends [EventFunction](/mods/eventslab/functions/EventFunction). That means all methods available in [EventFunction](/mods/eventslab/functions/EventFunction) are also available in GiveAttributeFunction

## Constructors


```zenscript
new GiveAttributeFunction(attributeID as invalid, attribute as Attribute, value as double, operation as AttributeOperation) as GiveAttributeFunction
```
|  Parameter  |                                  Type                                  |
|-------------|------------------------------------------------------------------------|
| attributeID | **invalid**                                                            |
| attribute   | [Attribute](/vanilla/api/entity/attribute/Attribute)                   |
| value       | double                                                                 |
| operation   | [AttributeOperation](/vanilla/api/entity/attribute/AttributeOperation) |



