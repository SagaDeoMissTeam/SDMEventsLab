package net.sixik.sdmeventslab.events.function.integration.botania;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.sixik.sdmeventslab.events.function.EventFunction;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;

@ZenRegister
@Document("mods/eventslab/functions/integration/ManaPoolGiveFunction")
@ZenCodeType.Name("mods.eventslab.functions.integration.ManaPoolGiveFunction")
public class ManaPoolGiveFunction extends EventFunction {
    private final RandomSource randomSource = RandomSource.create();
    private final int minMana;
    private final int maxMana;

    @ZenCodeType.Constructor
    public ManaPoolGiveFunction(int minMana, int maxMana) {
        this.minMana = minMana;
        this.maxMana = maxMana;
    }

    @Override
    public String getModID() {
        return "botania";
    }

    @Override
    public void functionOnBlockEntity(BlockEntity blockEntity, @Nullable BlockState state, BlockPos pos, Level level) {
        if(level.getGameTime() % 10 == 0 && ModList.get().isLoaded(getModID()) && blockEntity instanceof ManaPoolBlockEntity entity) {
            int randomMana = minMana;
            if(minMana != maxMana) {
                randomMana = randomSource.nextInt(minMana, maxMana);
            }

            if(entity.getCurrentMana() < entity.getMaxMana()) {
                entity.receiveMana(randomMana);
            }
        }
    }
}
