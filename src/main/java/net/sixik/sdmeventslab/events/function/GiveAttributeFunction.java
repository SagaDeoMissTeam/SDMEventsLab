package net.sixik.sdmeventslab.events.function;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.UUID;

@ZenRegister
@Document("mods/eventslab/functions/GiveAttributeFunction")
@ZenCodeType.Name("mods.eventslab.functions.GiveAttributeFunction")
public class GiveAttributeFunction extends EventFunction{

    protected final UUID attributeID;
    protected final Attribute attribute;
    protected final double value;
    protected final AttributeModifier.Operation operation;

    @ZenCodeType.Constructor
    public GiveAttributeFunction(UUID attributeID, Attribute attribute, double value, AttributeModifier.Operation operation) {
        this.attributeID = attributeID;
        this.attribute = attribute;
        this.value = value;
        this.operation = operation;
        functionStage = FunctionStage.START;
    }

//    @Override
//    public void onEntitySpawnEvent(MobSpawnEvent.FinalizeSpawn event) {
//        if(targetFunction == TargetFunction.PLAYER) return;
//
//
//        AttributeInstance attributeinstance = event.getEntity().getAttribute(attribute);
//        if(attributeinstance == null)
//            return;
//
//        AttributeModifier modifier = attributeinstance.getModifier(attributeID);
//
//        if(modifier != null) return;
//
//        attributeinstance.addTransientModifier(new AttributeModifier(attributeID, "Event_" + attributeinstance.toString(), value, operation));
//
//    }
//
//    @Override
//    public void onEventEnd(MinecraftServer server) {
//        if(targetFunction == TargetFunction.PLAYER) return;
//
//        for (ServerLevel allLevel : server.getAllLevels()) {
//            for (Entity entity : allLevel.getEntities().getAll()) {
//                if(entity instanceof Player) continue;
//
//                if(entity instanceof LivingEntity living) {
//                    AttributeInstance attributeinstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
//                    if (attributeinstance != null) {
//                        AttributeModifier modifier = attributeinstance.getModifier(attributeID);
//                        if (modifier != null) {
//                            attributeinstance.removeModifier(attributeID);
//                        }
//                    }
//                }
//            }
//        }
//    }

    @Override
    public void applyEffectPlayer(ServerPlayer player) {
        if(targetFunction == TargetFunction.ENTITY) return;

        AttributeInstance attributeinstance = player.getAttribute(attribute);
        if(attributeinstance == null)
            return;

        AttributeModifier modifier = attributeinstance.getModifier(attributeID);

        if(modifier != null) return;

        attributeinstance.addTransientModifier(new AttributeModifier(attributeID, "Event_" + attributeinstance.toString(), value, operation));

    }

    @Override
    public void resetEffectFromPlayers(ServerPlayer player) {
        if(targetFunction == TargetFunction.ENTITY) return;

        AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attributeinstance != null) {
            AttributeModifier modifier = attributeinstance.getModifier(attributeID);
            if (modifier != null) {
                attributeinstance.removeModifier(attributeID);
            }
        }
    }
}
