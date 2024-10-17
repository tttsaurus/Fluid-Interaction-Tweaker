package com.tttsaurus.fluidintetweaker.wrapper.crt.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.event.IEventPositionable;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenRegister
@ZenClass("mods.fluidintetweaker.event.CustomFluidInteractionEvent")
public interface ICustomFluidInteractionEvent extends IEventCancelable, IEventPositionable
{
    // it seems that crt doesn't like the field name fluid
    // I follow the pattern

    @ZenGetter("isLiquidAboveAndBelowCase")
    boolean getIsFluidAboveAndBelowCase();

    @ZenGetter("isInitiatorAbove")
    boolean getIsInitiatorAbove();

    @ZenGetter("blockStateBeforeInteraction")
    IBlockState getBlockStateBeforeInteraction();

    @ZenGetter("liquidInteractionRecipeKey")
    String getFluidInteractionRecipeKey();

    @ZenGetter("world")
    IWorld getWorld();
}
