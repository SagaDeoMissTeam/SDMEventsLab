package net.sixik.sdmeventslab.mixin.integration.botania;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.managers.EventManager;
import net.sixik.sdmeventslab.events.function.EventFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;

@Mixin(value = ManaPoolBlockEntity.class, remap = false)
public class ManaPoolBlockEntityMixin {


    @Inject(method = "serverTick", at = @At("RETURN"))
    private static void sdm$serverTick(Level level, BlockPos worldPosition, BlockState state, ManaPoolBlockEntity self, CallbackInfo ci) {

        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            for (EventFunction function : startedGlobalEvent.getFunctions()) {
                function.functionOnBlockEntity(self,state,worldPosition,level);
            }
        }
    }
}
