package com.tttsaurus.fluidintetweaker.common.impl;

import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.jefi.impl.JEFIPlugin;
import com.tttsaurus.fluidintetweaker.common.api.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.StringRecipeProtocol;
import com.tttsaurus.fluidintetweaker.common.api.exception.FluidInteractionTweakerRuntimeException;
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

    private static final HashMap<String, ComplexOutput> recipeDict = new HashMap<>();

    //<editor-fold desc="methods for FluidInteractionLogic">
    static boolean ingredientAExists(InteractionIngredient ingredientA)
    {
        return recipeIngredientAList.contains(ingredientA.toString());
    }
    static boolean ingredientBExists(InteractionIngredient ingredientB)
    {
        return recipeIngredientBList.contains(ingredientB.toString());
    }
    static boolean recipeExists(InteractionIngredient ingredientA, InteractionIngredient ingredientB)
    {
        return recipeDict.containsKey(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB));
    }
    static ComplexOutput getRecipeOutput(InteractionIngredient ingredientA, InteractionIngredient ingredientB)
    {
        return recipeDict.get(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB));
    }
    @Nullable
    static ComplexOutput getNullableRecipeOutput(InteractionIngredient ingredientA, InteractionIngredient ingredientB)
    {
        if (recipeExists(ingredientA, ingredientB))
            return recipeDict.get(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB));
        else
            return null;
    }
    //</editor-fold>

    //<editor-fold desc="methods for wrappers">
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
    public static String addRecipe(FluidInteractionRecipe recipe)
    {
        return addRecipe(recipe.ingredientA, recipe.ingredientB, recipe.complexOutput, recipe.extraInfoLocalizationKey);
    }
    public static String addRecipe(InteractionIngredient ingredientA, InteractionIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey) throws FluidInteractionTweakerRuntimeException
    {
        String ingredientAKey = ingredientA.toString();
        String ingredientBKey = ingredientB.toString();
        String key = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
        if (recipeDict.containsKey(key))
            throw new FluidInteractionTweakerRuntimeException("FluidInteractionRecipeManager.addRecipe() fails to execute. The same recipe key " + key + " has been added.");
        else
        {
            if (!recipeIngredientAList.contains(ingredientAKey)) recipeIngredientAList.add(ingredientAKey);
            if (!recipeIngredientBList.contains(ingredientBKey)) recipeIngredientBList.add(ingredientBKey);
            recipeDict.put(key, complexOutput);

            // jei compat
            if (FluidInteractionTweaker.IS_JEI_LOADED)
                JEFIPlugin.addRecipeWrapper(key, ingredientA, ingredientB, complexOutput, extraInfoLocalizationKey);

            return key;
        }
    }
    public static void removeRecipe(FluidInteractionRecipe recipe)
    {
        removeRecipe(recipe.ingredientA, recipe.ingredientB);
    }
    public static void removeRecipe(InteractionIngredient ingredientA, InteractionIngredient ingredientB) throws FluidInteractionTweakerRuntimeException
    {
        String key = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
        if (recipeDict.containsKey(key))
            recipeDict.remove(key);
        else
            throw new FluidInteractionTweakerRuntimeException("FluidInteractionRecipeManager.removeRecipe() fails to execute. The recipe key " + key + " being removed doesn't exist.");
    }
    public static void removeAllRecipes()
    {
        recipeIngredientAList.clear();
        recipeIngredientBList.clear();
        recipeDict.clear();
    }
    //</editor-fold>
}
