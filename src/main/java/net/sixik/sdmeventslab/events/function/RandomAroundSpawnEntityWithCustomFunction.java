package net.sixik.sdmeventslab.events.function;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;

import java.util.Random;
import java.util.function.Consumer;

public class RandomAroundSpawnEntityWithCustomFunction extends RandomAroundSpawnEntityFunction{

    private final Consumer<Entity> consumer;

    public RandomAroundSpawnEntityWithCustomFunction(int minCount, int maxCount, int radius, EntityType<?> entityType, Consumer<Entity> consumer) {
        super(minCount, maxCount, radius, entityType);
        this.consumer = consumer;
    }

    public RandomAroundSpawnEntityWithCustomFunction(int minCount, int maxCount, int radius, EntityType<?> entityType, int timePerSpawn, Consumer<Entity> consumer) {
        super(minCount, maxCount, radius, entityType, timePerSpawn);
        this.consumer = consumer;
    }

    @Override
    public void spawnMobsAroundPlayer(Player player, EntityType<?> entityType, int radius) {
        Level level = player.level();

        if (!(level instanceof ServerLevel serverLevel)) return;

        Random random = new Random();

        int countsMobs = random.nextInt(minCount, maxCount);

        for (int i = 0; i < countsMobs; i++) {
            double offsetX = (random.nextDouble() - 0.5) * 2 * radius;
            double offsetZ = (random.nextDouble() - 0.5) * 2 * radius;

            BlockPos spawnPos = player.blockPosition().offset((int) offsetX, 0, (int) offsetZ);

            spawnPos = serverLevel.getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING, spawnPos);
            BlockState blockBelow = serverLevel.getBlockState(spawnPos.below());


            if (!blockBelow.isAir() && serverLevel.getBlockState(spawnPos).isAir() && serverLevel.getBlockState(spawnPos.above()).isAir()) {

                Entity mob = entityType.create(serverLevel);
                if (mob != null) {
                    mob.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
                    serverLevel.addFreshEntity(mob);
                    consumer.accept(mob);
                }
            }
        }
    }
}
