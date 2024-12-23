package net.sixik.sdmeventslab.events.function.drops;

import com.blamejared.crafttweaker.api.loot.table.LootTableManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.event.level.BlockEvent;
import net.sixik.sdmeventslab.events.function.ChancableFunction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class CustomDropFromBlockFunction<T> extends ChancableFunction<T> {

    protected final List<BlockState> blocks;

    public CustomDropFromBlockFunction(Block[] blocks, Map<T, Double> contents) {
        super(contents);
        this.blocks = List.of(blocks).stream().map(Block::defaultBlockState).toList();
    }

    public CustomDropFromBlockFunction(List<BlockState> blocks, Map<T, Double> contents) {
        super(contents);
        this.blocks = blocks;
    }

    public static class ItemDrop extends CustomDropFromBlockFunction<ItemStack> {

        public ItemDrop(Block[] blocks, Map<ItemStack, Double> contents) {
            super(List.of(blocks).stream().map(Block::defaultBlockState).toList(), contents);
        }

        public ItemDrop(List<BlockState> entityTypes, Map<ItemStack, Double> contents) {
            super(entityTypes, contents);
        }

        @Override
        public void onBlockBreakEvent(BlockEvent.BreakEvent event) {
            if(!blocks.isEmpty() && !blocks.contains(event.getState())) return;

            if(event.getPlayer() instanceof ServerPlayer player) {
                ItemStack drop = getRandomElement();
                if(drop!= null) {
                    ItemEntity entity = new ItemEntity(player.level(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), drop);
                    player.level().addFreshEntity(entity);
                }
            }
        }
    }

    public static class LootTableDrop extends CustomDropFromBlockFunction<ResourceLocation> {

        public LootTableDrop(Block[] blocks, Map<ResourceLocation, Double> contents) {
            super(List.of(blocks).stream().map(Block::defaultBlockState).toList(), contents);
        }

        public LootTableDrop(List<BlockState> entityTypes, Map<ResourceLocation, Double> contents) {
            super(entityTypes, contents);
        }

        @Override
        public void onBlockBreakEvent(BlockEvent.BreakEvent event) {
            if(!blocks.isEmpty() && !blocks.contains(event.getState())) return;

            if(event.getPlayer() instanceof ServerPlayer player) {
                @Nullable ResourceLocation drop = getRandomElement();
                if(drop!= null) {
                    LootTable table = LootTableManager.INSTANCE.getTable(drop);
                    LootParams.Builder builder = new LootParams.Builder(player.serverLevel());

                    table.getRandomItems(builder.create(LootContextParamSets.ENTITY), (s) -> {
                        ItemEntity entity = new ItemEntity(player.level(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), s);
                        player.level().addFreshEntity(entity);
                    });
                }
            }
        }
    }
}
