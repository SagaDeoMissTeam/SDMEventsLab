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

public class RandomAroundSpawnEntityFunction extends EventFunction{

    protected final int minCount;
    protected final int maxCount;
    protected final EntityType<?> entityType;
    protected final int timePerSpawn;
    protected final int radius;

    public RandomAroundSpawnEntityFunction(int minCount, int maxCount, int radius, EntityType<?> entityType) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.entityType = entityType;
        this.timePerSpawn = 120;
        this.radius = radius;
    }

    public RandomAroundSpawnEntityFunction(int minCount, int maxCount, int radius, EntityType<?> entityType, int timePerSpawn) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.entityType = entityType;
        this.timePerSpawn = timePerSpawn;
        this.radius = radius;
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if(event.phase != TickEvent.Phase.START || event.player.level().isClientSide) return;

        if(event.player.level().getGameTime() % timePerSpawn == 0) {
            spawnMobsAroundPlayer(event.player, entityType, radius);
        }
    }

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
                }
            }
        }
    }
}
