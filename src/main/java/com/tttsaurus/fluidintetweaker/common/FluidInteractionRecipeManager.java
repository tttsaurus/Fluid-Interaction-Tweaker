package com.tttsaurus.fluidintetweaker.common;

import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.block.IBlock;
import java.util.*;

@ZenRegister
@ZenClass("mods.fluidintetweaker.FluidInteractionRecipeManager")
public final class FluidInteractionRecipeManager
{
    // IngredientA must be the block that notifies its neighbor blocks, which starts the interaction event
    private static List<String> recipeIngredientAList = new ArrayList<>();
    private static List<String> recipeIngredientBList = new ArrayList<>();
    private static HashMap<String, Block> recipeDict = new HashMap<String, Block>();

    public static boolean ingredientAExists(InteractionIngredient ingredientA)
    {
        return recipeIngredientAList.contains(ingredientA.toString());
    }
    public static boolean ingredientBExists(InteractionIngredient ingredientB)
    {
        return recipeIngredientBList.contains(ingredientB.toString());
    }
    public static boolean recipeExists(InteractionIngredient ingredientA, InteractionIngredient ingredientB)
    {
        return recipeDict.containsKey(ingredientA.toString() + "+" + ingredientB.toString());
    }
    public static Block getRecipeOutput(InteractionIngredient ingredientA, InteractionIngredient ingredientB)
    {
        return recipeDict.get(ingredientA.toString() + "+" + ingredientB.toString());
    }

    private static void addRecipe(InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock)
    {
        String ingredientAKey = ingredientA.toString();
        String ingredientBKey = ingredientB.toString();
        String key = ingredientAKey + "+" + ingredientBKey;
        if (recipeDict.containsKey(key))
        {
            FluidInteractionTweaker.logger.info("The same fluid interaction recipe " + key + " has been added");
        }
        else
        {
            if (!recipeIngredientAList.contains(ingredientAKey)) recipeIngredientAList.add(ingredientAKey);
            if (!recipeIngredientBList.contains(ingredientBKey)) recipeIngredientBList.add(ingredientBKey);
            recipeDict.put(key, outputBlock);
        }
    }

    @ZenMethod
    public static void removeAllRecipes()
    {
        recipeIngredientAList.clear();
        recipeIngredientBList.clear();
        recipeDict.clear();
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack liquidThatNotifiesOthers, boolean isSourceA, ILiquidStack liquidBeingNotified, boolean isSourceB, IBlock outputBlock)
    {
        InteractionIngredient ingredientA = new InteractionIngredient(
                InteractionIngredientType.FLUID,
                ((FluidStack)liquidThatNotifiesOthers.getInternal()).getFluid(),
                isSourceA,
                null);
        InteractionIngredient ingredientB = new InteractionIngredient(
                InteractionIngredientType.FLUID,
                ((FluidStack)liquidBeingNotified.getInternal()).getFluid(),
                isSourceB,
                null);
        addRecipe(ingredientA, ingredientB, (Block)outputBlock.getDefinition().getInternal());
    }
    @ZenMethod
    public static void addRecipe(ILiquidStack liquidThatNotifiesOthers, boolean isSource, ILiquidStack liquidBeingNotified, IBlock outputBlock)
    {
        addRecipe(liquidThatNotifiesOthers, isSource, liquidBeingNotified, true, outputBlock);
        addRecipe(liquidThatNotifiesOthers, isSource, liquidBeingNotified, false, outputBlock);
    }
    @ZenMethod
    public static void addRecipe(ILiquidStack liquidThatNotifiesOthers, ILiquidStack liquidBeingNotified, IBlock outputBlock)
    {
        addRecipe(liquidThatNotifiesOthers, true, liquidBeingNotified, outputBlock);
        addRecipe(liquidThatNotifiesOthers, false, liquidBeingNotified, outputBlock);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack liquidThatNotifiesOthers, boolean isSource, IBlock blockBeingNotified, IBlock outputBlock)
    {
        InteractionIngredient ingredientA = new InteractionIngredient(
                InteractionIngredientType.FLUID,
                ((FluidStack)liquidThatNotifiesOthers.getInternal()).getFluid(),
                isSource,
                null);
        InteractionIngredient ingredientB = new InteractionIngredient(
                InteractionIngredientType.BLOCK,
                null,
                false,
                (Block)blockBeingNotified.getDefinition().getInternal());
        addRecipe(ingredientA, ingredientB, (Block)outputBlock.getDefinition().getInternal());
    }
    @ZenMethod
    public static void addRecipe(ILiquidStack liquidThatNotifiesOthers, IBlock blockBeingNotified, IBlock outputBlock)
    {
        addRecipe(liquidThatNotifiesOthers, true, blockBeingNotified, outputBlock);
        addRecipe(liquidThatNotifiesOthers, false, blockBeingNotified, outputBlock);
    }
}
