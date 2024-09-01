package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import crafttweaker.annotations.ZenRegister;

@ZenRegister
@ZenClass("mods.fluidintetweaker.FITweaker")
public final class AccessibleMethods
{
    //<editor-fold desc="addRecipe">
    @ZenMethod
    public static void addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, IBlock outputBlock)
    {
        CraftTweakerAPI.apply(new Actions.AddRecipeAction(liquidInitiator, isSourceA, liquidSurrounding, isSourceB, outputBlock));
    }
    @ZenMethod
    public static void addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, IBlock outputBlock)
    {
        CraftTweakerAPI.apply(new Actions.AddRecipeAction(liquidInitiator, isSourceA, liquidSurrounding, outputBlock));
    }
    @ZenMethod
    public static void addRecipe(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, IBlock outputBlock)
    {
        CraftTweakerAPI.apply(new Actions.AddRecipeAction(liquidInitiator, liquidSurrounding, outputBlock));
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, IBlock blockSurrounding, IBlock outputBlock)
    {
        CraftTweakerAPI.apply(new Actions.AddRecipeAction(liquidInitiator, isSourceA, blockSurrounding, outputBlock));
    }
    @ZenMethod
    public static void addRecipe(ILiquidStack liquidInitiator, IBlock blockSurrounding, IBlock outputBlock)
    {
        CraftTweakerAPI.apply(new Actions.AddRecipeAction(liquidInitiator, blockSurrounding, outputBlock));
    }
    //</editor-fold>

    //<editor-fold desc="removeAllRecipes">
    @ZenMethod
    public static void removeAllRecipes()
    {
        CraftTweakerAPI.apply(new Actions.RemoveAllRecipesAction());
    }
    //</editor-fold>
}
