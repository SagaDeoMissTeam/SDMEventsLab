package net.sixik.sdmeventslab.network.client;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.sixik.sdmeventslab.network.SDMEventsLabNetwork;

public class SendSoundS2C extends BaseS2CMessage {

    private final SoundEvent event;
    private final SoundSource source;
    private final float d1;
    private final float d2;

    public SendSoundS2C(SoundEvent event, SoundSource source, float d1, float d2) {
        this.event = event;
        this.source = source;
        this.d1 = d1;
        this.d2 = d2;
    }

    public SendSoundS2C(FriendlyByteBuf buf) {
        this.event = BuiltInRegistries.SOUND_EVENT.get(buf.readResourceLocation());
        this.source = SoundSource.BLOCKS;
        this.d1 = buf.readFloat();
        this.d2 = buf.readFloat();
    }

    @Override
    public MessageType getType() {
        return SDMEventsLabNetwork.SEND_SOUND;
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeResourceLocation(event.getLocation());
        friendlyByteBuf.writeFloat(d1);
        friendlyByteBuf.writeFloat(d2);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(NetworkManager.PacketContext packetContext) {
        if(packetContext.getEnv().isClient()) {
            SimpleSoundInstance simpleSoundInstance = SimpleSoundInstance.forUI(event, d1,d2);
            Minecraft.getInstance().getSoundManager().play(simpleSoundInstance);
        }
    }
}
