package com.tttsaurus.fluidintetweaker.plugin.crt.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.event.IEventPositionable;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.entity.MCEntityLivingBase;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenRegister
@ZenClass("mods.fluidintetweaker.event.CustomFluidBehaviorEvent")
public interface ICustomFluidBehaviorEvent extends IEventCancelable, IEventPositionable
{
    // it seems that crt doesn't like the field name fluid
    // I follow the pattern

    @ZenGetter("entityLivingBase")
    MCEntityLivingBase getEntityLivingBase();

    @ZenGetter("liquidBehaviorRecipeKey")
    String getFluidBehaviorRecipeKey();

    @ZenGetter("world")
    IWorld getWorld();
}
