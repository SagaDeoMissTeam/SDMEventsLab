package net.sixik.sdmeventslab.events.function;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class GiveAttributeFunction extends EventFunction{

    protected final UUID attributeID;
    protected final Attribute attribute;
    protected final double value;
    protected final AttributeModifier.Operation operation;

    public GiveAttributeFunction(UUID attributeID, Attribute attribute, double value, AttributeModifier.Operation operation) {
        this.attributeID = attributeID;
        this.attribute = attribute;
        this.value = value;
        this.operation = operation;
        functionStage = FunctionStage.START;
    }

    @Override
    public void applyEffectPlayer(ServerPlayer player) {
        AttributeInstance attributeinstance = player.getAttribute(attribute);
        if(attributeinstance == null)
            return;

        AttributeModifier modifier = attributeinstance.getModifier(attributeID);

        if(modifier != null) return;

        attributeinstance.addTransientModifier(new AttributeModifier(attributeID, "Event_" + attributeinstance.toString(), value, operation));

    }

    @Override
    public void resetEffectFromPlayers(ServerPlayer player) {
        AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attributeinstance != null) {
            AttributeModifier modifier = attributeinstance.getModifier(attributeID);
            if (modifier != null) {
                attributeinstance.removeModifier(attributeID);
            }
        }
    }
}
