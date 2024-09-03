package com.tttsaurus.fluidintetweaker.common.impl;

import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.StringRecipeProtocol;
import com.tttsaurus.fluidintetweaker.common.api.exception.FluidInteractionTweakerRuntimeException;
import net.minecraft.block.Block;
import javax.annotation.Nullable;
import java.util.*;

// this class is totally internal
// use methods in wrappers to manage recipes
public final class FluidInteractionRecipeManager
{
    // ingredientA is the initiator
    // it's always that ingredientA turns to an output block
    // except the fluid above & fluid below case
    // it's always that fluid below turns to a block

    // these two lists are just for quick recipe search
    private static final List<String> recipeIngredientAList = new ArrayList<>();
    private static final List<String> recipeIngredientBList = new ArrayList<>();

    private static HashMap<String, Block> recipeDict = new HashMap<>();

    //<editor-fold desc="methods for FluidEventHandler">
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
        return recipeDict.containsKey(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB));
    }
    public static Block getRecipeOutput(InteractionIngredient ingredientA, InteractionIngredient ingredientB)
    {
        return recipeDict.get(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB));
    }
    @Nullable
    public static Block getNullableRecipeOutput(InteractionIngredient ingredientA, InteractionIngredient ingredientB)
    {
        if (recipeExists(ingredientA, ingredientB))
            return recipeDict.get(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB));
        else
            return null;
    }
    //</editor-fold>

    //<editor-fold desc="methods for wrappers">
    public static HashMap<String, Block> getAllRecipes() { return recipeDict; }
    public static void setAllRecipes(HashMap<String, Block> anotherRecipeDict) { recipeDict = anotherRecipeDict; }

    public static void refreshIngredientABLists()
    {
        recipeIngredientAList.clear();
        recipeIngredientBList.clear();
        for (String key: recipeDict.keySet())
        {
            String[] strings = StringRecipeProtocol.splitRecipeKeyToTwoRawStrings(key);
            if (!recipeIngredientAList.contains(strings[0])) recipeIngredientAList.add(strings[0]);
            if (!recipeIngredientBList.contains(strings[1])) recipeIngredientBList.add(strings[1]);
        }
    }
    public static void addRecipe(InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock) throws FluidInteractionTweakerRuntimeException
    {
        String ingredientAKey = ingredientA.toString();
        String ingredientBKey = ingredientB.toString();
        String key = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
        if (recipeDict.containsKey(key))
            throw new FluidInteractionTweakerRuntimeException("FluidInteractionRecipeManager.addRecipe() fails to execute. The same fluid interaction recipe " + key + " has been added.");
        else
        {
            if (!recipeIngredientAList.contains(ingredientAKey)) recipeIngredientAList.add(ingredientAKey);
            if (!recipeIngredientBList.contains(ingredientBKey)) recipeIngredientBList.add(ingredientBKey);
            recipeDict.put(key, outputBlock);
        }
    }
    public static void removeRecipe(InteractionIngredient ingredientA, InteractionIngredient ingredientB) throws FluidInteractionTweakerRuntimeException
    {
        String key = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
        if (recipeDict.containsKey(key))
            recipeDict.remove(key);
        else
            throw new FluidInteractionTweakerRuntimeException("FluidInteractionRecipeManager.removeRecipe() fails to execute. The key being removed doesn't exist.");
    }
    public static void removeAllRecipes()
    {
        recipeIngredientAList.clear();
        recipeIngredientBList.clear();
        recipeDict.clear();
    }
    //</editor-fold>
}
