package net.sixik.sdmeventslab.events.function;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("mods/eventslab/functions/SpawnReplaceFunction")
@ZenCodeType.Name("mods.eventslab.functions.SpawnReplaceFunction")
public class SpawnReplaceFunction extends EventFunction{

    protected final EntityType<?> toReplaceEntity;
    protected final List<EntityType<?>> replacableTypes;

    @ZenCodeType.Constructor
    public SpawnReplaceFunction(EntityType<?> toReplaceEntity, EntityType<?>[] replacableTypes) {
        this.toReplaceEntity = toReplaceEntity;
        this.replacableTypes = List.of(replacableTypes);
    }

    @ZenCodeType.Constructor
    public SpawnReplaceFunction(EntityType<?> toReplaceEntity, List<EntityType<?>> replacableTypes) {
        this.toReplaceEntity = toReplaceEntity;
        this.replacableTypes = replacableTypes;
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
    }
}
