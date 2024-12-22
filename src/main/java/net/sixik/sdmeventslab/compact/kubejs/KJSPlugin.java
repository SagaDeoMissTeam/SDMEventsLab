package net.sixik.sdmeventslab.compact.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.builder.*;
import net.sixik.sdmeventslab.events.conditions.EventCondition;
import net.sixik.sdmeventslab.events.conditions.WeatherCondition;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;
import net.sixik.sdmeventslab.events.function.EventFunction;

public class KJSPlugin extends KubeJSPlugin {

    @Override
    public void registerBindings(BindingsEvent event) {
        if(event.getType().isStartup()) {
            event.add("EventBuilder", EventBuilder.class);
            event.add("EventFunctionBuilder", EventCustomFunctionBuilder.class);
            event.add("EventConditionBuilder", EventConditionBuilder.class);
            event.add("EventEndCondition", EventEndCondition.class);
            event.add("EventRenderBuilder", EventRenderBuilder.class);
            event.add("EventPropertyBuilder", EventPropertyBuilder.class);
            event.add("EventSide", EventBase.EventSide.class);
            event.add("WeatherType", WeatherCondition.WeatherType.class);
            event.add("ConditionType", EventCondition.ConditionType.class);
            event.add("FunctionStage", EventFunction.FunctionStage.class);
        }
    }

//    @Override
//    public void registerClasses(ScriptType type, ClassFilter filter) {
//        filter.allow("net.sixik.sdmeventslab");
//    }
}
