package net.sixik.sdmeventslab.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sixik.sdmeventslab.SDMEventsLab;
import net.sixik.sdmeventslab.items.RandomEventItem;

public class ItemRegister {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SDMEventsLab.MODID);;


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SDMEventsLab.MODID);



//    public static final RegistryObject<Item> RESET_EVENT = ITEMS.register("reset_event", () -> {
//        return new Item((new Item.Properties()));
//    });

    public static final RegistryObject<Item> RANDOM_EVENT = ITEMS.register("rendom_event", RandomEventItem::new);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("eventlabs_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(RANDOM_EVENT.get()))
            .title(Component.translatable("mod.eventlabs.creative_mode_tab"))
            .displayItems((i,o) -> {
                o.accept(RANDOM_EVENT.get());
            }).build());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        TABS.register(eventBus);
    }

}
