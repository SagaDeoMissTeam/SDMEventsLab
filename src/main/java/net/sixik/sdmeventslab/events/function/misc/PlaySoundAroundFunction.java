package net.sixik.sdmeventslab.events.function.misc;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.sixik.sdmeventslab.api.ActiveEventData;
import net.sixik.sdmeventslab.api.IEventHistory;

import java.util.Optional;

public class PlaySoundAroundFunction extends PlaySoundFunction{

    protected final int zone;

    public PlaySoundAroundFunction(SoundEvent soundEvent, SoundSource source, float volume, float pitch, FunctionStage stage, int zone) {
        super(soundEvent, source, volume, pitch, stage);
        this.zone = zone;
    }

    @Override
    protected void play(MinecraftServer server) {
        switch (eventBase.getEventSide()) {
            case LOCAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    if(player instanceof IEventHistory eventHistory) {
                        Optional<ActiveEventData> o = eventHistory.sdm$getActivesEvents().stream().filter(s -> s.eventID.equals(eventBase.getEventID())).findFirst();
                        if(o.isPresent()) {
                            double offsetX = (player.level().random.nextDouble() - 0.5) * 2 * zone;
                            double offsetY = (player.level().random.nextDouble() - 0.5) * 2 * zone; // Для вертикали
                            double offsetZ = (player.level().random.nextDouble() - 0.5) * 2 * zone;

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
                            break;
                        }
                    }
                }
            }
            case GLOBAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {

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
    }
}
