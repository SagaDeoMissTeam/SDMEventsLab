package net.sixik.sdmeventslab.mixin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.managers.EventManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public class CropBlockMixin {

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void sdm$canSurvive(BlockState state, LevelReader p_52283_, BlockPos p_52284_, CallbackInfoReturnable<Boolean> cir) {
        for (EventBase startedGlobalEvent : EventManager.INSTANCE.startedGlobalEvents) {
            if(!startedGlobalEvent.properties.canCropsSurvivors && !startedGlobalEvent.properties.cropsCantDeath.contains(BuiltInRegistries.BLOCK.getKey(state.getBlock()))) {
                cir.setReturnValue(false);
                return;
            }
        }
    }
}
