package com.tttsaurus.fluidintetweaker.common;

import net.minecraft.block.Block;
import java.util.*;

// this class is totally internal
// use methods in wrappers to manage recipes
public final class FluidInteractionRecipeManager
{
    // it's always that ingredientA turns to an output block
    // ingredientA is the initiator

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
        return recipeDict.containsKey(ingredientA.toString() + "+" + ingredientB.toString());
    }
    public static Block getRecipeOutput(InteractionIngredient ingredientA, InteractionIngredient ingredientB)
    {
        return recipeDict.get(ingredientA.toString() + "+" + ingredientB.toString());
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
            String[] strings = key.split("\\+");
            if (!recipeIngredientAList.contains(strings[0])) recipeIngredientAList.add(strings[0]);
            if (!recipeIngredientBList.contains(strings[1])) recipeIngredientBList.add(strings[1]);
        }
    }
    public static void addRecipe(InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock) throws RuntimeException
    {
        String ingredientAKey = ingredientA.toString();
        String ingredientBKey = ingredientB.toString();
        String key = ingredientAKey + "+" + ingredientBKey;
        if (recipeDict.containsKey(key))
            throw new RuntimeException("FluidInteractionRecipeManager.addRecipe fails to execute. The same fluid interaction recipe " + key + " has been added");
        else
        {
            if (!recipeIngredientAList.contains(ingredientAKey)) recipeIngredientAList.add(ingredientAKey);
            if (!recipeIngredientBList.contains(ingredientBKey)) recipeIngredientBList.add(ingredientBKey);
            recipeDict.put(key, outputBlock);
        }
    }
    public static void removeRecipe(InteractionIngredient ingredientA, InteractionIngredient ingredientB) throws RuntimeException
    {
        String key = ingredientA.toString() + "+" + ingredientB.toString();
        if (recipeDict.containsKey(key))
            recipeDict.remove(key);
        else
            throw new RuntimeException("FluidInteractionRecipeManager.removeRecipe fails to execute. The key being removed doesn't exist");
    }
    public static void removeAllRecipes()
    {
        recipeIngredientAList.clear();
        recipeIngredientBList.clear();
        recipeDict.clear();
    }
    //</editor-fold>
}
