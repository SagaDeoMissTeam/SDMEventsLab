package net.sixik.sdmeventslab.events.function;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.living.MobSpawnEvent;

import java.util.List;
import java.util.function.BiConsumer;

public class SpawnReplaceWithCustomFunction extends SpawnReplaceFunction{

    protected final BiConsumer<MobSpawnEvent.FinalizeSpawn, Entity> consumer;

    public SpawnReplaceWithCustomFunction(EntityType<?> toReplaceEntity, EntityType<?>[] replacableTypes, BiConsumer<MobSpawnEvent.FinalizeSpawn, Entity> consumer) {
        super(toReplaceEntity, replacableTypes);
        this.consumer = consumer;
    }

    public SpawnReplaceWithCustomFunction(EntityType<?> toReplaceEntity, List<EntityType<?>> replacableTypes, BiConsumer<MobSpawnEvent.FinalizeSpawn, Entity> consumer) {
        super(toReplaceEntity, replacableTypes);
        this.consumer = consumer;
    }

    @Override
    public void onEntitySpawnEvent(MobSpawnEvent.FinalizeSpawn event) {
        Entity entity = event.getEntity();

        if(!event.getLevel().getLevel().dimension().equals(ServerLevel.OVERWORLD)) return;

        if(entity.getType().equals(EntityType.PLAYER)) return;

        if(!replacableTypes.contains(entity.getType())) return;

        BlockPos pos = entity.blockPosition();

        event.setSpawnCancelled(true);

        Entity zo = toReplaceEntity.create(event.getLevel().getLevel());
        if (zo != null) {
            zo.setPos(pos.getX(), pos.getY(), pos.getZ());
            event.getLevel().addFreshEntity(zo);
        }

        consumer.accept(event, zo);
    }
}
