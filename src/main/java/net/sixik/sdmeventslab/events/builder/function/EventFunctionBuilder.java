package net.sixik.sdmeventslab.events.builder.function;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.sixik.sdmeventslab.events.function.BlockInteractFunction;
import org.apache.logging.log4j.util.TriConsumer;

public class EventFunctionBuilder extends EventFunctionV1Builder {

    public EventFunctionBuilder() {}

    public EventFunctionBuilder addBlockInteractFunction(Block[] blocks, TriConsumer<ServerPlayer, BlockPos, BlockState> consumer) {
        eventFunctions.add(new BlockInteractFunction(blocks, consumer));
        return this;
    }

}
