package com.tttsaurus.fluidintetweaker.wrapper.crt.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.event.IBlockEvent;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.liquid.ILiquidDefinition;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenRegister
@ZenClass("mods.fluidintetweaker.event.CustomFluidInteractionEvent")
public interface ICustomFluidInteractionEvent extends IEventCancelable, IBlockEvent
{
    // it seems that crt doesn't like the field name fluid
    // I follow the pattern

    @ZenGetter("liquidBlockStateBeforeInteraction")
    IBlockState getFluidBlockStateBeforeInteraction();

    @ZenGetter("liquidBeforeInteraction")
    ILiquidDefinition getFluidBeforeInteraction();

    @ZenGetter("liquidInteractionRecipeKey")
    String getFluidInteractionRecipeKey();
}
