package net.sixik.sdmeventslab.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class EventProperty {

    public List<ResourceLocation> backList = new ArrayList<>();
    public List<ResourceLocation> whiteList = new ArrayList<>();

    public List<Item> whiteListUsableItems = new ArrayList<>();
    public List<Item> blackListUsableItems = new ArrayList<>();

    public boolean isPlayerCanUseShield = true;
    public boolean isPlayerCanStayOnSun = true;

    public List<ResourceLocation> blackListBlocksWherePlayerCanStay = new ArrayList<>();

}
