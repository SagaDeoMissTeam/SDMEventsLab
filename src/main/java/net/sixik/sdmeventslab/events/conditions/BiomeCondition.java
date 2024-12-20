package net.sixik.sdmeventslab.events.conditions;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/eventslab/conditions/BiomeCondition")
@ZenCodeType.Name("mods.eventslab.conditions.BiomeCondition")
public class BiomeCondition extends EventCondition{

    private final ResourceLocation biomeID;

    @ZenCodeType.Constructor
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
