package net.sixik.sdmeventslab.events.function;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.apache.logging.log4j.util.TriConsumer;

public class BlockInteractFunction extends EventFunction {

    protected final Block[] blocks;
    protected final TriConsumer<ServerPlayer, BlockPos, BlockState> consumer;

    public BlockInteractFunction(Block[] blocks, TriConsumer<ServerPlayer, BlockPos, BlockState> consumer) {
        this.blocks = blocks;
        this.consumer = consumer;
    }

    @Override
    public void onBlockInteractEvent(PlayerInteractEvent.RightClickBlock event) {
        if(event.getEntity() instanceof ServerPlayer player)
            consumer.accept(player, event.getPos(), player.level().getBlockState(event.getPos()));
    }
}
