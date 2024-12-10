package net.sixik.sdmeventslab.events.function;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ReplaceAroundBlockFunction extends EventFunction {

    protected final int size;
    protected final Function<BlockState, @Nullable BlockState> predicate;

    public ReplaceAroundBlockFunction(int size, Function<BlockState, @Nullable BlockState> predicate) {
        this.size = size;
        this.predicate = predicate;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        if(event.player instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();

            if (level.getGameTime() % 30 == 0) {

                CompletableFuture<Void> task = level.getServer().submitAsync(() -> {

                    int x = player.blockPosition().getX();
                    int y = player.blockPosition().getY();
                    int z = player.blockPosition().getZ();
                    for (int dx = x - size; dx <= x + size; dx++) {
                        for (int dy = y - 4; dy <= y + 4; dy++) {
                            for (int dz = z - size; dz <= z + size; dz++) {

                                BlockState block = predicate.apply(level.getBlockState(new BlockPos(dx, dy, dz)));
                                if(block != null) {
                                    level.setBlockAndUpdate(new BlockPos(dx, dy, dz), block);
                                }
                            }
                        }
                    }
                });

                level.getServer().managedBlock(task::isDone);

            }

        }

    }
}