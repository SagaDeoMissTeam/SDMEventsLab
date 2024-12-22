package net.sixik.sdmeventslab.events.function.drops;

import com.blamejared.crafttweaker.api.loot.table.LootTableManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.sixik.sdmeventslab.events.function.ChancableFunction;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CustomDropFromEntityFunction<T> extends ChancableFunction<T> {

    protected final List<EntityType<?>> entityTypes;

    protected CustomDropFromEntityFunction(ResourceLocation[] entityTypes, Map<T, Double> contents) {
        super(contents);
        this.entityTypes = new ArrayList<>();
        for (ResourceLocation entityType : entityTypes) {
            Optional<EntityType<?>> s = BuiltInRegistries.ENTITY_TYPE.getOptional(entityType);
            if(s.isEmpty()) continue;
            this.entityTypes.add(s.get());
        }
    }

    protected CustomDropFromEntityFunction(EntityType<?>[] entityTypes, Map<T, Double> contents) {
        super(contents);
        this.entityTypes = List.of(entityTypes);
    }


    public static class ItemDrop extends CustomDropFromEntityFunction<ItemStack> {

        public ItemDrop(ResourceLocation[] entityTypes, Map<ItemStack, Double> contents) {
            super(entityTypes, contents);
        }

        public ItemDrop(EntityType<?>[] entityTypes, Map<ItemStack, Double> contents) {
            super(entityTypes, contents);
        }

        @Override
        public void onLivingDeathEvent(LivingDeathEvent event) {
            if(!entityTypes.isEmpty() && !entityTypes.contains(event.getEntity().getType())) return;

            if(event.getSource().getEntity() instanceof ServerPlayer player) {

                ItemStack drop = getRandomElement();
                if(drop!= null) {
                    ItemEntity entity = new ItemEntity(player.level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), drop);
                    player.level().addFreshEntity(entity);
                }
            }
        }
    }

    public static class LootTableDrop extends CustomDropFromEntityFunction<ResourceLocation> {

        public LootTableDrop(ResourceLocation[] entityTypes, Map<ResourceLocation, Double> contents) {
            super(entityTypes, contents);
        }

        public LootTableDrop(EntityType<?>[] entityTypes, Map<ResourceLocation, Double> contents) {
            super(entityTypes, contents);
        }

        @Override
        public void onLivingDeathEvent(LivingDeathEvent event) {
            if(!entityTypes.isEmpty() && !entityTypes.contains(event.getEntity().getType())) return;

            if(event.getSource().getEntity() instanceof ServerPlayer player) {

                @Nullable ResourceLocation drop = getRandomElement();
                if(drop!= null) {
                    LootTable table = LootTableManager.INSTANCE.getTable(drop);
                    LootParams.Builder builder = new LootParams.Builder(player.serverLevel());

                    table.getRandomItems(builder.create(LootContextParamSets.ENTITY), (s) -> {
                        ItemEntity entity = new ItemEntity(player.level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), s);
                        player.level().addFreshEntity(entity);
                    });
                }
            }
        }
    }
}
