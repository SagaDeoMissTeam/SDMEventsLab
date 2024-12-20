package net.sixik.sdmeventslab.events.function;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/functions/WaterEffectFunction")
@ZenCodeType.Name("mods.eventslab.functions.WaterEffectFunction")
public class WaterEffectFunction extends EventFunction{

    protected final MobEffectInstance effect;
    protected final int checkEffectDelay;

    @ZenCodeType.Constructor
    public WaterEffectFunction(MobEffectInstance effect) {
        this.effect = effect;
        this.checkEffectDelay = 10;
    }

    @ZenCodeType.Constructor
    public WaterEffectFunction(MobEffectInstance effect, int checkEffectDelay) {
        this.effect = effect;
        this.checkEffectDelay = checkEffectDelay;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        if(event.player instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();

            if(level.getGameTime() % checkEffectDelay == 0) {

                if(player.isInWater()) {
                    MobEffectInstance e = player.getEffect(effect.getEffect());
                    if(e == null) {
                        player.addEffect(effect);
                    }
                }
            }
        }
    }
}
