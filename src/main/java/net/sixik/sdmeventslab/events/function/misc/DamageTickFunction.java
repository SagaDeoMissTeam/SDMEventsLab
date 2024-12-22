package net.sixik.sdmeventslab.events.function.misc;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.sixik.sdmeventslab.events.function.EventFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DamageTickFunction extends EventFunction {

    private final List<EntityType<?>> entityTypes;

    private final float damage;
    private final int damageDelay;

    public DamageTickFunction(ResourceLocation[] entityTypes, float damage, int damageDelay) {
        this.entityTypes = new ArrayList<>();
        for (ResourceLocation entityType : entityTypes) {
            Optional<EntityType<?>> s = BuiltInRegistries.ENTITY_TYPE.getOptional(entityType);
            if(s.isEmpty()) continue;
            this.entityTypes.add(s.get());
        }
        this.damage = damage;
        this.damageDelay = damageDelay;
    }

    public DamageTickFunction(EntityType<?>[] entityTypes, float damage, int damageDelay) {
        this.entityTypes = List.of(entityTypes);
        this.damage = damage;
        this.damageDelay = damageDelay;
    }

    @Override
    public void onLivingEntityTickEvent(LivingEvent.LivingTickEvent event) {
        if(event.getEntity().level().getGameTime() % damageDelay == 0) {

            if(!entityTypes.isEmpty()) {
                if (entityTypes.contains(event.getEntity().getType())) {
                    event.getEntity().hurt(event.getEntity().damageSources().magic(), damage);
                }
                return;
            }

            if(targetFunction == TargetFunction.ENTITY || targetFunction == TargetFunction.ALL || targetFunction == TargetFunction.DEFAULT) {
                if(event.getEntity() instanceof Player player) return;
                event.getEntity().hurt(event.getEntity().damageSources().magic(), damage);
            }
        }
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if(event.player.level().getGameTime() % damageDelay == 0) {

            if(!entityTypes.isEmpty()) {
                if (entityTypes.contains(event.player.getType())) {
                    event.player.hurt(event.player.damageSources().magic(), damage);
                }
                return;
            }

            if (targetFunction == TargetFunction.PLAYER || targetFunction == TargetFunction.ALL) {
                event.player.hurt(event.player.damageSources().magic(), damage);
            }
        }
    }
}
