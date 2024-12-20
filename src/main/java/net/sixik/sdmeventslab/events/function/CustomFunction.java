package net.sixik.sdmeventslab.events.function;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/functions/CustomFunction")
@ZenCodeType.Name("mods.eventslab.functions.CustomFunction")
public class CustomFunction extends EventFunction {

    private String functionID;

    public CustomFunction() {

    }

    public final void setFunctionID(String functionID) {
        this.functionID = functionID;
    }

    public final String getFunctionID() {
        return functionID;
    }
}
