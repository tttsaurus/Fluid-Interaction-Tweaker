package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import crafttweaker.annotations.ZenRegister;
import java.util.List;

@ZenRegister
@ZenClass("mods.fluidintetweaker.FITweaker")
public final class AccessibleMethods
{
    //<editor-fold desc="addRecipe">
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, IBlock outputBlock)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, isSourceA, liquidSurrounding, isSourceB, outputBlock);
        CraftTweakerAPI.apply(action);
        return action.recipes;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, IBlock outputBlock)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, isSourceA, liquidSurrounding, outputBlock);
        CraftTweakerAPI.apply(action);
        return action.recipes;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, IBlock outputBlock)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, liquidSurrounding, outputBlock);
        CraftTweakerAPI.apply(action);
        return action.recipes;
    }

    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, IBlock blockSurrounding, IBlock outputBlock)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, isSourceA, blockSurrounding, outputBlock);
        CraftTweakerAPI.apply(action);
        return action.recipes;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, IBlock blockSurrounding, IBlock outputBlock)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, blockSurrounding, outputBlock);
        CraftTweakerAPI.apply(action);
        return action.recipes;
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
