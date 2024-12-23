package net.sixik.sdmeventslab.events.builder.property;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.sixik.sdmeventslab.events.property.EventGlobalProperty;
import net.sixik.sdmeventslab.events.property.EventRenderProperty;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("mods/eventslab/builder/EventPropertyBuilder")
@ZenCodeType.Name("mods.eventslab.builder.EventPropertyBuilder")
public class EventPropertyBuilder {

    private EventGlobalProperty eventProperty = new EventGlobalProperty();
    private EventRenderProperty eventRenderProperty = new EventRenderProperty();

    @ZenCodeType.Method
    public EventPropertyBuilder setDayTime(int days) {
        eventProperty.eventDayTime = days;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setDamage(double damage) {
        eventProperty.damage = damage;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setProtection(double protection) {
        eventProperty.protection = protection;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setChanceStartEvent(double chanceStartEvent) {
        eventProperty.chanceStartEvent = Math.max(0.00001, Math.min(chanceStartEvent, 100.0));
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setCanEndByAnyCondition(boolean canEndByAnyCondition) {
        eventProperty.canEndByAnyCondition = canEndByAnyCondition;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setPlayerCanUseShield(boolean isPlayerCanUseShield) {
        eventProperty.isPlayerCanUseShield = isPlayerCanUseShield;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setPlayerCanStayOnSun(boolean isPlayerCanStayOnSun) {
        eventProperty.isPlayerCanStayOnSun = isPlayerCanStayOnSun;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setCanFriendlyStayOnSun(boolean canFriendlyStayOnSun) {
        eventProperty.canFriendlyStayOnSun = canFriendlyStayOnSun;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setCanCropsSurvivors(boolean canCropsSurvivors) {
        eventProperty.canCropsSurvivors = canCropsSurvivors;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setCropsCantDeath(ResourceLocation[] cropsIds) {
        eventProperty.cropsCantDeath.addAll(List.of(cropsIds));
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setCanAnimalsTame(boolean canAnimalsTame) {
        eventProperty.canAnimalsTame = canAnimalsTame;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setAnimalsCantTame(EntityType<?>[] entityTypes) {
        eventProperty.animalsCantTame.addAll(List.of(entityTypes));
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setCanZombieBurnInSun(boolean canZombieBurnInSun) {
        eventProperty.canZombieBurnInSun = canZombieBurnInSun;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setCanSkeletonBurnInSun(boolean canSkeletonBurnInSun) {
        eventProperty.canSkeletonBurnInSun = canSkeletonBurnInSun;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setLogoSize(int logoSizeX, int logoSizeY) {
        eventRenderProperty.logoSizeX = logoSizeX;
        eventRenderProperty.logoSizeY = logoSizeY;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setTimeToShowLogo(int timeToShowLogo) {
        eventRenderProperty.timeToShowLogo = timeToShowLogo;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setLogoAlphaPerTick(double logoAlphaPerTick) {
        eventRenderProperty.logoAlphaPerTick = logoAlphaPerTick;
        return this;
    }

    @ZenCodeType.Method
    public EventPropertyBuilder setRenderTitleInScreenAfterLogo(boolean value) {
        eventRenderProperty.isRenderTitleInScreenAfterLogo = value;
        return this;
    }

    public EventGlobalProperty createEventProperty() {
        return eventProperty;
    }

    public EventRenderProperty createRenderProperty() {
        return eventRenderProperty;
    }
}
