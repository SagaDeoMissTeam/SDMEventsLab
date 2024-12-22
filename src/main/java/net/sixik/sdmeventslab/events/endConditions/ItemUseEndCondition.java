package net.sixik.sdmeventslab.events.endConditions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.sixik.sdmeventslab.utils.NBTUtils;

public class ItemUseEndCondition extends EventEndCondition{

    private ItemStack itemStack;

    public ItemUseEndCondition(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public String getID() {
        return "itemUseCondition";
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        NBTUtils.putItemStack(nbt, "item", itemStack);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.itemStack = NBTUtils.getItemStack(nbt, "item");
    }
}
