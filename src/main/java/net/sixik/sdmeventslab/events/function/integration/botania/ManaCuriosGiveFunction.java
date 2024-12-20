package net.sixik.sdmeventslab.events.function.integration.botania;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModList;
import net.sixik.sdmeventslab.events.function.EventFunction;
import org.openzen.zencode.java.ZenCodeType;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.Optional;

@ZenRegister
@Document("mods/eventslab/functions/integration/ManaCuriosGiveFunction")
@ZenCodeType.Name("mods.eventslab.functions.integration.ManaCuriosGiveFunction")
public class ManaCuriosGiveFunction extends EventFunction {

    private final RandomSource randomSource = RandomSource.create();
    private final int minMana;
    private final int maxMana;

    @ZenCodeType.Constructor
    public ManaCuriosGiveFunction(int minMana, int maxMana) {
        this.minMana = minMana;
        this.maxMana = maxMana;
    }

    @Override
    public String getModID() {
        return "botania";
    }

    @Override
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        if(event.player instanceof ServerPlayer player) {
            ServerLevel level = player.serverLevel();
            if (level.getGameTime() % 10 == 0 && ModList.get().isLoaded(getModID())) {

                Optional<ICuriosItemHandler> cur_inventory = CuriosApi.getCuriosInventory(player).resolve();
                if (cur_inventory.isPresent()) {
                    ICuriosItemHandler inventory = cur_inventory.get();
                    for (int i = 0; i < inventory.getSlots(); i++) {
                        ItemStack itemStack = inventory.getEquippedCurios().getStackInSlot(i);
                        if (itemStack.isEmpty()) continue;


                        ManaItem manaItem = XplatAbstractions.INSTANCE.findManaItem(itemStack);
                        if (manaItem == null) continue;

                        int maxMana = manaItem.getMaxMana();
                        int currentMana = manaItem.getMana();
                        int manaAvailable = maxMana - currentMana;

                        if (manaAvailable <= 0) continue;



                        int randomMana = minMana;
                        if(minMana != maxMana) {
                            randomMana = randomSource.nextInt(minMana, this.maxMana);
                        }

                        int manaToAdd = Math.min(randomMana, manaAvailable);
                        manaItem.addMana(manaToAdd);
                    }
                }
            }
        }
    }
}
