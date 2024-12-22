package net.sixik.sdmeventslab.items;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sixik.sdmeventslab.events.EventBase;
import net.sixik.sdmeventslab.events.managers.EventManager;

import java.util.Iterator;

public class RandomEventItem extends Item {
    
    public RandomEventItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        
        if(p_41433_ instanceof ServerPlayer player) {

            player.getCooldowns().addCooldown(this, 5);

            Iterator<EventBase> events = EventManager.INSTANCE.startedGlobalEvents.iterator();
            while (events.hasNext()) {
                EventBase eventBase = events.next();
                eventBase.onEventEnd(player.server);
                events.remove();
            }
            EventManager.INSTANCE.nextDayEvent = EventManager.getDay(player.server);

            EventManager.INSTANCE.tryStartEvents(player.server);

            if(EventManager.INSTANCE.startedGlobalEvents.isEmpty()) return InteractionResultHolder.success(p_41433_.getMainHandItem());
            EventBase base = EventManager.INSTANCE.startedGlobalEvents.get(0);
            if(base != null) {
                player.sendSystemMessage(Component.literal(base.getEventID().toString()));
            }
        }
        return InteractionResultHolder.success(p_41433_.getMainHandItem());
    }
}
