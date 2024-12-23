package net.sixik.sdmeventslab.events.property;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.function.EventFunction;

import java.util.ArrayList;
import java.util.List;

public class EventBiomeProperty {

    public final ResourceLocation[] biome;
    protected final List<EventFunction> functions = new ArrayList<>();

    public EventBase eventBase;

    public List<EventFunction> getFunctions() {
        return functions;
    }

    public EventBiomeProperty(ResourceLocation[] biome) {
        this.biome = biome;
    }

    public EventBiomeProperty setEventBase(EventBase eventBase) {
        this.eventBase = eventBase;

        getFunctions().stream().map(s -> s.setEvent(eventBase));

        return this;
    }

    public boolean inBiome(BlockPos pos, ServerLevel level) {

        if(biome.length == 0) return true;

        for (ResourceLocation resourceLocation : biome) {
            if(level.getBiome(pos).is(resourceLocation)) return true;
        }

        return false;
    }
}
