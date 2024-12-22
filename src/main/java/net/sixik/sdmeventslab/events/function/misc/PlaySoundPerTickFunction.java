package net.sixik.sdmeventslab.events.function.misc;

import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.TickEvent;

public class PlaySoundPerTickFunction extends PlaySoundFunction {

    protected final int tick;

    public PlaySoundPerTickFunction(SoundEvent soundEvent, SoundSource source, float volume, float pitch, int tick, FunctionStage functionStage) {
        super(soundEvent, source, volume, pitch, functionStage);
        this.tick = tick;

    }

    @Override
    public void onEventStart(MinecraftServer server) {}

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if(event.player.level().getGameTime() % tick == 0) {
            var player = event.player;

            player.level().playSound(player, player.getX(), player.getY(), player.getZ(), soundEvent, source, volume, pitch);
        }
    }
}
