package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.Optional;
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
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, IBlock outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, isSourceA, liquidSurrounding, isSourceB, outputBlock, extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, IBlock outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, isSourceA, liquidSurrounding, outputBlock, extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, IBlock outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, liquidSurrounding, outputBlock, extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }

    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, IBlock blockSurrounding, IBlock outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, isSourceA, blockSurrounding, outputBlock, extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, IBlock blockSurrounding, IBlock outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(liquidInitiator, blockSurrounding, outputBlock, extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
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
