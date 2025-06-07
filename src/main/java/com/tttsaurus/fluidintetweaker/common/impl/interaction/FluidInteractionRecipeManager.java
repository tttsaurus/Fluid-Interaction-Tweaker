package com.tttsaurus.fluidintetweaker.common.impl.interaction;

import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.impl.jefi.JEFIPlugin;
import com.tttsaurus.fluidintetweaker.common.core.interaction.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.core.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.core.interaction.StringRecipeProtocol;
import com.tttsaurus.fluidintetweaker.common.core.exception.FITweakerRuntimeException;
import com.tttsaurus.fluidintetweaker.common.core.util.CachedContainsKeyMap;
import net.minecraftforge.fml.common.FMLCommonHandler;
import java.util.*;

// this class is totally internal
// use methods for plugins to manage recipes
@SuppressWarnings("all")
public final class FluidInteractionRecipeManager
{
    public static boolean autoAddJEIRecipe = true;

    // ingredientA is the initiator
    // it's always that ingredientA turns to an output block
    // except the fluid above & fluid below case
    // it's always that fluid below turns to a block

    // these two lists are just for quick recipe search
    private static final List<String> recipeIngredientAList = new ArrayList<>();
    private static final List<String> recipeIngredientBList = new ArrayList<>();

    private static final CachedContainsKeyMap<String, ComplexOutput> recipeDict = new CachedContainsKeyMap<>();

    //<editor-fold desc="methods for FluidInteractionLogic">
    static boolean ingredientAExists(WorldIngredient ingredientA)
    {
        return recipeIngredientAList.contains(ingredientA.toString());
    }
    static boolean ingredientBExists(WorldIngredient ingredientB)
    {
        return recipeIngredientBList.contains(ingredientB.toString());
    }
    static boolean recipeExists(WorldIngredient ingredientA, WorldIngredient ingredientB)
    {
        return recipeDict.containsKey(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB));
    }
    static ComplexOutput getRecipeOutput(WorldIngredient ingredientA, WorldIngredient ingredientB)
    {
        return recipeDict.get(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB));
    }
    //</editor-fold>

    //<editor-fold desc="methods for plugins">
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
    public static String addRecipe(FluidInteractionRecipe recipe) throws FITweakerRuntimeException
    {
        String ingredientAKey = recipe.ingredientA.toString();
        String ingredientBKey = recipe.ingredientB.toString();
        String key = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(recipe.ingredientA, recipe.ingredientB);
        if (recipeDict.containsKey(key))
            throw new FITweakerRuntimeException("FluidInteractionRecipeManager.addRecipe() fails to execute. The same recipe key " + key + " has been added.");
        else
        {
            if (!recipeIngredientAList.contains(ingredientAKey)) recipeIngredientAList.add(ingredientAKey);
            if (!recipeIngredientBList.contains(ingredientBKey)) recipeIngredientBList.add(ingredientBKey);
            recipeDict.put(key, recipe.complexOutput);

            // jei compat
            if (FluidInteractionTweaker.IS_JEI_LOADED && autoAddJEIRecipe && FMLCommonHandler.instance().getSide().isClient())
                JEFIPlugin.addRecipeWrapper(recipe);

            return key;
        }
    }
    public static void removeRecipe(FluidInteractionRecipe recipe) throws FITweakerRuntimeException
    {
        String key = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(recipe.ingredientA, recipe.ingredientB);
        if (recipeDict.containsKey(key))
            recipeDict.remove(key);
        else
            throw new FITweakerRuntimeException("FluidInteractionRecipeManager.removeRecipe() fails to execute. The recipe key " + key + " being removed doesn't exist.");
    }
    public static void removeAllRecipes()
    {
        recipeIngredientAList.clear();
        recipeIngredientBList.clear();
        recipeDict.clear();
    }
    //</editor-fold>
}
