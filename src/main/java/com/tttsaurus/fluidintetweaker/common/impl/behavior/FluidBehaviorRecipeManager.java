package com.tttsaurus.fluidintetweaker.common.impl.behavior;

import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.behavior.FluidBehaviorRecipe;
import com.tttsaurus.fluidintetweaker.common.api.exception.FluidInteractionTweakerRuntimeException;
import java.util.HashMap;

public final class FluidBehaviorRecipeManager
{
    private static final HashMap<String, ComplexOutput> recipeDict = new HashMap<>();

    //<editor-fold desc="methods for FluidBehaviorLogic">
    static boolean recipeExists(WorldIngredient ingredient)
    {
        return recipeDict.containsKey(ingredient.toString());
    }
    static ComplexOutput getRecipeOutput(WorldIngredient ingredient)
    {
        return recipeDict.get(ingredient.toString());
    }
    //</editor-fold>

    //<editor-fold desc="methods for wrappers">
    public static String addRecipe(FluidBehaviorRecipe recipe)
    {
        return addRecipe(recipe.ingredient, recipe.complexOutput);
    }
    public static String addRecipe(WorldIngredient ingredient, ComplexOutput complexOutput) throws FluidInteractionTweakerRuntimeException
    {
        String key = ingredient.toString();
        if (recipeDict.containsKey(key))
            throw new FluidInteractionTweakerRuntimeException("FluidBehaviorRecipeManager.addRecipe() fails to execute. The same recipe key " + key + " has been added.");
        else
        {
            recipeDict.put(key, complexOutput);
            return key;
        }
    }
    public static void removeRecipe(FluidBehaviorRecipe recipe)
    {
        removeRecipe(recipe.ingredient);
    }
    public static void removeRecipe(WorldIngredient ingredient) throws FluidInteractionTweakerRuntimeException
    {
        String key = ingredient.toString();
        if (recipeDict.containsKey(key))
            recipeDict.remove(key);
        else
            throw new FluidInteractionTweakerRuntimeException("FluidBehaviorRecipeManager.removeRecipe() fails to execute. The recipe key " + key + " being removed doesn't exist.");
    }
    public static void removeAllRecipes()
    {
        recipeDict.clear();
    }
    //</editor-fold>
}
