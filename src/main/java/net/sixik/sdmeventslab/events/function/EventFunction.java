package net.sixik.sdmeventslab.events.function;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.sixik.sdmeventslab.events.EventBase;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/functions/EventFunction")
@ZenCodeType.Name("mods.eventslab.functions.EventFunction")
public class EventFunction {

    public EventBase eventBase;
    public FunctionStage functionStage = FunctionStage.TICK;

    public final EventFunction setEvent(EventBase event) {
        this.eventBase = event;
        return this;
    }

    public void onEventStart(MinecraftServer server) {}
    public void onEventEnd(MinecraftServer server) {}
    public void onEventTick(MinecraftServer server) {}

    public void onEntitySpawnEvent(MobSpawnEvent.FinalizeSpawn event) {}
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {}
    public void onLivingDeathEvent(LivingDeathEvent event) {}
    public void onEntityInteractEvent(PlayerInteractEvent.EntityInteract event) {}
    public void onPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {}
    public void onPlayerItemPickupEvent(PlayerEvent.ItemPickupEvent event) {}

    public void applyEffectPlayer(ServerPlayer player) {}

    public void resetEffectFromPlayers(ServerPlayer player) {}

    public void functionOnBlockState(BlockState blockState, BlockPos pos, Level level) {}
    public void functionOnBlockEntity(BlockEntity blockEntity, @Nullable BlockState state, BlockPos pos, Level level) {}

    public String getModID() {
        return "minecraft";
    }

    public enum FunctionStage {
        START,
        END,
        TICK
    }
}
