package net.sixik.sdmeventslab.events.function;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@ZenRegister
@Document("mods/eventslab/functions/ReplaceBlockUnderPlayerFunction")
@ZenCodeType.Name("mods.eventslab.functions.ReplaceBlockUnderPlayerFunction")
public class ReplaceBlockUnderPlayerFunction extends EventFunction{

    protected final Function<BlockState, @Nullable BlockState> predicate;

    @ZenCodeType.Constructor
    public ReplaceBlockUnderPlayerFunction(Function<BlockState, @Nullable BlockState> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        if(event.player instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();

            if (level.getGameTime() % 2 == 0) {

                CompletableFuture<Void> task = level.getServer().submitAsync(() -> {

                    BlockState block = predicate.apply(level.getBlockState(new BlockPos(player.blockPosition().getX(), player.blockPosition().getY() - 1, player.blockPosition().getZ())));
                    if(block == null) block = Blocks.AIR.defaultBlockState();

                    level.setBlockAndUpdate(new BlockPos(player.blockPosition().getX(), player.blockPosition().getY() - 1, player.blockPosition().getZ()), block);

                });

                level.getServer().managedBlock(task::isDone);

            }

        }
    }
}
