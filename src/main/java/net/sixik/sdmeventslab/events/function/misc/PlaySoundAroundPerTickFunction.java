package net.sixik.sdmeventslab.events.function.misc;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.TickEvent;

public class PlaySoundAroundPerTickFunction extends PlaySoundPerTickFunction {

    protected final int zone;

    public PlaySoundAroundPerTickFunction(SoundEvent soundEvent, SoundSource source, float volume, float pitch, int tick,  int zone) {
        super(soundEvent, source, volume, pitch, tick, FunctionStage.START);
        this.zone = zone;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if(event.player.level().getGameTime() % tick == 0) {
            var player = event.player;

            double offsetX = (player.level().random.nextDouble() * 2 - 1) * zone;
            double offsetY = (player.level().random.nextDouble() * 2 - 1) * zone; // Для вертикали
            double offsetZ = (player.level().random.nextDouble() * 2 - 1) * zone;

            // Координаты для проигрывания звука
            double soundX = player.getX() + offsetX;
            double soundY = player.getY() + offsetY;
            double soundZ = player.getZ() + offsetZ;

            // Проигрывание звука
            player.level().playSound(
                    player,
                    soundX, soundY, soundZ,
                    soundEvent,
                    source,
                    volume,
                    pitch
            );
        }
    }
}
