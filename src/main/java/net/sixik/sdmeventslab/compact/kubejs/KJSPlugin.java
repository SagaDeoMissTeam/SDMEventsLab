package net.sixik.sdmeventslab.compact.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.builder.*;
import net.sixik.sdmeventslab.events.endConditions.EventEndCondition;

public class KJSPlugin extends KubeJSPlugin {

    @Override
    public void registerBindings(BindingsEvent event) {
        if(event.getType().isStartup()) {
            event.add("EventBuilder", EventBuilder.class);
            event.add("EventFunctionBuilder", EventFunctionBuilder.class);
            event.add("EventConditionBuilder", EventConditionBuilder.class);
            event.add("EventEndCondition", EventEndCondition.class);
            event.add("EventRenderBuilder", EventRenderBuilder.class);
            event.add("EventPropertyBuilder", EventPropertyBuilder.class);
            event.add("EventSide", EventBase.EventSide.class);
        }
    }

//    @Override
//    public void registerClasses(ScriptType type, ClassFilter filter) {
//        filter.allow("net.sixik.sdmeventslab");
//    }
}
