package net.sixik.sdmeventslab.events.function;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;

import java.util.function.Function;

public class GiveEffectAroundBlockFunction extends EventFunction{

    protected final Function<BlockState, MobEffectInstance> predicate;
    protected final int radius;

    public GiveEffectAroundBlockFunction(int radius, Function<BlockState, MobEffectInstance> predicate) {
        this.predicate = predicate;
        this.radius = radius;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        if(event.player instanceof ServerPlayer player) {
            BlockPos playerPos = player.blockPosition();
            boolean isNearTargetBlock = false;
            Level level = player.level();

            if (level.getGameTime() % 20 != 0) {
                return;
            }


            MobEffectInstance effectInstance = null;

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -1; dy <= 2; dy++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        BlockPos pos = playerPos.offset(dx, dy, dz);

                        effectInstance = predicate.apply(level.getBlockState(pos));
                        if (effectInstance != null) {
                            isNearTargetBlock = true;
                            break;
                        }
                    }
                    if (isNearTargetBlock) break;
                }
                if (isNearTargetBlock) break;
            }

            if (isNearTargetBlock) {
                if (player.hasEffect(effectInstance.getEffect())) {
                    return;
                }

                player.addEffect(effectInstance);
            }
        }


    }
}
