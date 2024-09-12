package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import com.tttsaurus.fluidintetweaker.common.api.ComplexOutput;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlockState;
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
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                liquidSurrounding,
                isSourceB,
                new ComplexOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                liquidSurrounding,
                new ComplexOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(
                liquidInitiator,
                liquidSurrounding,
                new ComplexOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }

    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, IBlockState blockSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                blockSurrounding,
                new ComplexOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, IBlockState blockSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(
                liquidInitiator,
                blockSurrounding,
                new ComplexOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(IBlockState blockInitiator, ILiquidStack liquidSurrounding, boolean isSourceB, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(
                blockInitiator,
                liquidSurrounding,
                isSourceB,
                new ComplexOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(IBlockState blockInitiator, ILiquidStack liquidSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        Actions.AddRecipesAction action = new Actions.AddRecipesAction(
                blockInitiator,
                liquidSurrounding,
                new ComplexOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
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
