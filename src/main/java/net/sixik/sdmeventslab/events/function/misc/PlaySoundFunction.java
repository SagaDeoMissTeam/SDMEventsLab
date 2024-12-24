package net.sixik.sdmeventslab.events.function.misc;

import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.sixik.sdmeventslab.api.ActiveEventData;
import net.sixik.sdmeventslab.api.IEventHistory;
import net.sixik.sdmeventslab.events.function.EventFunction;
import net.sixik.sdmeventslab.network.client.SendSoundS2C;

import java.util.Optional;

public class PlaySoundFunction extends EventFunction {

    protected final SoundEvent soundEvent;
    protected final SoundSource source;
    protected final float volume;
    protected final float pitch;
    protected final FunctionStage stage;

    public PlaySoundFunction(SoundEvent soundEvent, SoundSource source, float volume, float pitch, FunctionStage stage) {
        this.soundEvent = soundEvent;
        this.source = source;
        this.volume = volume;
        this.pitch = pitch;
        this.stage = stage;
    }

    @Override
    public void onEventStart(MinecraftServer server) {
        if(stage != FunctionStage.START) return;
        play(server);
    }

    @Override
    public void onEventEnd(MinecraftServer server) {
        if(stage != FunctionStage.END) return;
        play(server);
    }

    protected void play(MinecraftServer server) {
        switch (eventBase.getEventSide()) {
            case LOCAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    if(player instanceof IEventHistory eventHistory) {
                        Optional<ActiveEventData> o = eventHistory.sdm$getActivesEvents().stream().filter(s -> s.eventID.equals(eventBase.getEventID())).findFirst();
                        if(o.isPresent()) {
                            new SendSoundS2C(soundEvent, source,volume,pitch).sendTo(player);
                            break;
                        }
                    }
                }
            }
            case GLOBAL -> {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    new SendSoundS2C(soundEvent, source,volume,pitch).sendTo(player);
                }
            }
        }
    }
}
