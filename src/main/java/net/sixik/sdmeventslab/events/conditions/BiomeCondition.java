package net.sixik.sdmeventslab.events.conditions;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;

public class BiomeCondition extends EventCondition{

    private final ResourceLocation biomeID;

    public BiomeCondition(ResourceLocation biomeID) {
        this.biomeID = biomeID;
    }

    @Override
    public boolean canExecuteGlobal(MinecraftServer server) {
        throw new RuntimeException("Biome condition can only be executed LOCAL!");
    }

    @Override
    public boolean canExecuteLocal(ServerPlayer player) {
        Holder<Biome> biomeHolder = player.serverLevel().getBiome(player.blockPosition());
        return biomeHolder.is(biomeID);
    }
}
