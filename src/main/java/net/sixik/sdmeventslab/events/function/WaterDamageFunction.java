package net.sixik.sdmeventslab.events.function;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/functions/WaterDamageFunction")
@ZenCodeType.Name("mods.eventslab.functions.WaterDamageFunction")
public class WaterDamageFunction extends EventFunction{

    protected final float damage;
    protected final int damageDelay;

    @ZenCodeType.Constructor
    public WaterDamageFunction(float damage) {
        this.damage = damage;
        this.damageDelay = 2;
    }

    @ZenCodeType.Constructor
    public WaterDamageFunction(float damage, int damageDelay) {
        this.damage = damage;
        this.damageDelay = damageDelay;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        if(event.player instanceof ServerPlayer serverPlayer) {

            if (event.player.level().getGameTime() % damageDelay == 0) {
                if (event.player.isInWater()) {
                    serverPlayer.hurt(serverPlayer.damageSources().generic(), damage);
                }
            }
        }
    }
}
