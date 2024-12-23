package net.sixik.sdmeventslab.events.property;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.function.EventFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventStructureProperty {

    public final ResourceLocation[] structures;

    public boolean disableEntityInteract = true;
    public boolean disablePvP = false;
    public boolean disableMobDamage = false;

    protected final List<Block> blackListDestroyBlocks = new ArrayList<Block>();
    protected final List<Block> blackListPlaceBlocks = new ArrayList<Block>();
    protected final List<Block> blackListUsableBlocks = new ArrayList<>();

    protected final List<EventFunction> functions = new ArrayList<>();

    public EventBase eventBase;

    public EventStructureProperty(ResourceLocation[] structures) {
        this.structures = structures;
    }

    public EventStructureProperty setEventBase(EventBase eventBase) {
        this.eventBase = eventBase;

        getFunctions().stream().map(s -> s.setEvent(eventBase));

        return this;
    }

    public boolean inStructure(BlockPos pos, ServerLevel level) {
        Either<ResourceKey<Structure>, TagKey<Structure>> structure;
        StructureManager mgr = level.structureManager();

        for (ResourceLocation resourceLocation : structures) {
            structure = Either.left(ResourceKey.create(Registries.STRUCTURE, resourceLocation));
            if(structure.map(
                    key -> mgr.getStructureWithPieceAt(pos, key).isValid(),
                    tag -> mgr.getStructureWithPieceAt(pos, tag).isValid()
            )) return true;
        }

        return false;
    }

    public EventStructureProperty addFunction(EventFunction function) {
        functions.add(function);
        return this;
    }

    public List<EventFunction> getFunctions() {
        return functions;
    }

    public EventStructureProperty addBlackListDestroyBlock(Block block) {
        blackListDestroyBlocks.add(block);
        return this;
    }

    public EventStructureProperty addBlackListDestroyBlock(Block... block) {
        Collections.addAll(blackListDestroyBlocks, block);
        return this;
    }

    public EventStructureProperty addBlackListPlaceBlocks(Block block) {
        blackListPlaceBlocks.add(block);
        return this;
    }

    public EventStructureProperty addBlackListPlaceBlocks(Block... block) {
        Collections.addAll(blackListPlaceBlocks, block);
        return this;
    }

    public EventStructureProperty addBlackListUsableBlocks(Block block) {
        blackListUsableBlocks.add(block);
        return this;
    }

    public EventStructureProperty addBlackListUsableBlocks(Block... block) {
        Collections.addAll(blackListUsableBlocks, block);
        return this;
    }

    public EventStructureProperty setDisablePvP(boolean disablePvP) {
        this.disablePvP = disablePvP;
        return this;
    }

    public EventStructureProperty setDisableMobDamage(boolean disableMobDamage) {
        this.disableMobDamage = disableMobDamage;
        return this;
    }

    public EventStructureProperty setDisableEntityInteract(boolean disableEntityInteract) {
        this.disableEntityInteract = disableEntityInteract;
        return this;
    }
}
