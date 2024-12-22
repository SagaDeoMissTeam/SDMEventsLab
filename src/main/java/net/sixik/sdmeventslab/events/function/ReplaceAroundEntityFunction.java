package net.sixik.sdmeventslab.events.function;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.function.Function;

@ZenRegister
@Document("mods/eventslab/functions/ReplaceAroundEntityFunction")
@ZenCodeType.Name("mods.eventslab.functions.ReplaceAroundEntityFunction")
public class ReplaceAroundEntityFunction extends EventFunction{

    protected final int size;
    protected final Function<Entity, EntityType<?>> predicate;

    @ZenCodeType.Constructor
    public ReplaceAroundEntityFunction(int size, Function<Entity, EntityType<?>> predicate) {
        this.size = size;
        this.predicate = predicate;
    }

//    s

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide || targetFunction != TargetFunction.PLAYER) {
            return;
        }

        if(event.player instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();

            if (level.getGameTime() % 15 == 0) {

                int x = player.blockPosition().getX();
                int y = player.blockPosition().getY();
                int z = player.blockPosition().getZ();

                List<Entity> entityList = level.getEntitiesOfClass(Entity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));

                for (Entity entity : entityList) {
                    EntityType<?> entityType = predicate.apply(entity);
                    if(entityType != null) {
                        entity.discard();

                        Entity entity1 = entityType.create(entity.level());
                        if(entity1 == null) continue;
                        entity1.setPos(entity.getX(), entity.getY(), entity.getZ());
                        level.addFreshEntity(entity1);
                    }
                }
            }
        }
    }
}
