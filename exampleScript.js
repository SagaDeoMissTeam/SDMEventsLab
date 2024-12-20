// priority: 0

// Visit the wiki for more info - https://kubejs.com/

const CropBlock = Java.loadClass("net.minecraft.world.level.block.CropBlock")
const EquipmentSlot = Java.loadClass("net.minecraft.world.entity.EquipmentSlot")
const MobEffectInstance = Java.loadClass("net.minecraft.world.effect.MobEffectInstance")
const Blocks = Java.loadClass("net.minecraft.world.level.block.Blocks")
const Items = Java.loadClass("net.minecraft.world.item.Items")
const EntityType = Java.loadClass("net.minecraft.world.entity.EntityType")
const MobEffects = Java.loadClass("net.minecraft.world.effect.MobEffects")
const AbstractFurnaceBlock = Java.loadClass("net.minecraft.world.level.block.AbstractFurnaceBlock")
const RandomSource = Java.loadClass("net.minecraft.util.RandomSource")
const ServerPlayer = Java.loadClass("net.minecraft.server.level.ServerPlayer")
const Player = Java.loadClass("net.minecraft.world.entity.player.Player")

const CHECK_RADIUS = 5;
const ITEMS = [Items.DIAMOND_BOOTS, Items.DIAMOND];
const randomSource = RandomSource.create();



/**
                METEL EVENT
 */

let builder = EventBuilder.createEventString("sdmeventslab:metel_event", EventSide.GLOBAL);

builder.addCustomFunction(
    new EventFunctionBuilder()
        .addEventStart(server => {
            server.overworld().setWeatherParameters(0, 24000, true, false);
        })
        .addEventEnd(server => {
            server.overworld().setWeatherParameters(24000, 0, false, false);
        })
);
builder.addGiveEffectAroundBlockFunction(5, (block) => {
        if(block.getBlock() instanceof AbstractFurnaceBlock || block.is(Blocks.CRAFTING_TABLE)) {
            return new MobEffectInstance(MobEffects.DIG_SPEED, 40, 0, true, false);
        }
        return null;
    })
    .addCustomFunction(new EventFunctionBuilder().addLivingDeathEvent((event) => {
         if(event.entity instanceof Player || event.entity.level.isClientSide()) return;

        if(event.entity.isAnimal) return;

        if(!(event.source.entity instanceof Player)) return;

        let chance = randomSource.nextDouble() * 100;

        let level = event.entity.level;

        if(chance <= 10) {
            let ranodmItem = randomSource.nextInt(ITEMS.length);
            let item = ITEMS[ranodmItem];

            let pos = event.entity.blockPosition();

            let dropitem = new ItemEntity(level, pos.x, pos.y, pos.z, item.getDefaultInstance());
            level.addFreshEntity(dropitem);
        }
    }))
builder.addRender(
    new EventRenderBuilder()
        .addLogoRender()
);
builder.addEventProperty(
    new EventPropertyBuilder()
        .setChanceStartEvent(75.0)
        .setRenderTitleInScreenAfterLogo(true)
        .setLogoSize(169, 39)
)
builder.create();

/**
                ORDA EVENT
 */

builder = EventBuilder.createEventString("sdmeventslab:orda_event", EventSide.GLOBAL);
builder.addSpawnReplaceWithCustomFunction(EntityType.ZOMBIE, [], (event, entity) => {
        if(entity.getType() == 'minecraft:zombie') {
            entity.headArmorItem = 'minecraft:diamond_helmet'
            entity.chestArmorItem = 'minecraft:diamond_chestplate'
            entity.legsArmorItem = 'minecraft:diamond_leggings'
            entity.feetArmorItem = 'minecraft:diamond_boots'
        }
    }) 
    .addReplaceAroundEntityFunction(10, (entity) => {
        if(entity.type.equals(EntityType.ZOMBIE)) {
            return EntityType.VILLAGER;
        }
        return null;
    })


builder.addRender(
    new EventRenderBuilder()
        .addLogoRender()
);

builder.addEventProperty(
    new EventPropertyBuilder()
        .setChanceStartEvent(75.0)
        .setRenderTitleInScreenAfterLogo(true)
        .setLogoSize(170, 62)
        .setPlayerCanUseShield(false)
)

builder.create();


/**
                MYSTIC RAIN EVENT
 */

builder = EventBuilder.createEventString("sdmeventslab:mystic_rain", EventSide.GLOBAL);
builder.addCustomFunction(
    new EventFunctionBuilder()
        .addEventStart(server => {
            level.setWeatherParameters(0, 24000, true, false);
        })
);
builder.addManaCuriosGiveFunction(1,5).addManaPoolGiveFunction(1,5).addReplaceAroundBlockFunction(4, (block) => {
    if(block instanceof CropBlock) {
        return block.getStateForAge(block.getMaxAge())
    }
    return null;
}).addGiveEffectFunction(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000))
builder.addRender(
    new EventRenderBuilder()
        .addLogoRender()
);

builder.addEventProperty(
    new EventPropertyBuilder()
        .setChanceStartEvent(75.0)
        .setRenderTitleInScreenAfterLogo(true)
        .setLogoSize(217, 84)
)

builder.create();


/**
                DROUGHT EVENT
 */
builder = EventBuilder.createEventString("sdmeventslab:drought", EventSide.GLOBAL);
builder.addRender(
    new EventRenderBuilder()
        .addSunSize(150.0)
        .addSkyColor(0.5,0,0,1)
        .addFogColor(0.2,0,0)
        .addSunColor(1.0,0,0,1.0)
);

builder.addEventProperty(
    new EventPropertyBuilder()
        .setChanceStartEvent(75.0)
        .setPlayerCanUseShield(false)
        .setPlayerCanStayOnSun(false)
        .setCanCropsSurvivors(false)
        .setCanAnimalsTame(false)
        .setCanZombieBurnInSun(false)
        .setCanSkeletonBurnInSun(false)
        .setCanFriendlyStayOnSun(false)
        .setDamage(-50.0)
        .setProtection(-50.0)
)

builder.create();