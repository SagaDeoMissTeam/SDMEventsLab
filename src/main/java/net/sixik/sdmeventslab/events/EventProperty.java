package net.sixik.sdmeventslab.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class EventProperty {

    public List<ResourceLocation> blackListBlocksWherePlayerCanStay = new ArrayList<>();

    public int eventDayTime = 1;
    public double chanceStartEvent = 100.0;
    public boolean canEndByAnyCondition = false;

    public List<ResourceLocation> backList = new ArrayList<>();
    public List<ResourceLocation> whiteList = new ArrayList<>();

    public boolean isPlayerCanUseShield = true;
    public boolean isPlayerCanStayOnSun = true;
    public boolean canFriendlyStayOnSun = true;

    public boolean canCropsSurvivors = true;
    public List<ResourceLocation> cropsCantDeath = new ArrayList<>();

    public boolean canAnimalsTame = true;
    public List<EntityType<?>> animalsCantTame = new ArrayList<>();

    public boolean canZombieBurnInSun = true;
    public boolean canSkeletonBurnInSun = true;

    public double damage = 0.0;
    public double protection = 0.0;


}
