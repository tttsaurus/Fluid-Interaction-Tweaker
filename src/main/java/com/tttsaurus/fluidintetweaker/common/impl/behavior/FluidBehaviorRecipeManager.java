package com.tttsaurus.fluidintetweaker.common.impl.behavior;

import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.impl.jefb.JEFBPlugin;
import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.core.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.core.behavior.FluidBehaviorRecipe;
import com.tttsaurus.fluidintetweaker.common.core.exception.FITweakerRuntimeException;
import com.tttsaurus.fluidintetweaker.common.core.util.CachedContainsKeyMap;
import net.minecraftforge.fml.common.FMLCommonHandler;

// this class is totally internal
// use methods for plugins to manage recipes
@SuppressWarnings("all")
public final class FluidBehaviorRecipeManager
{
    public static boolean autoAddJEIRecipe = true;

    private static final CachedContainsKeyMap<String, ComplexOutput> recipeDict = new CachedContainsKeyMap<>();

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

    //<editor-fold desc="methods for plugins">
    public static String addRecipe(FluidBehaviorRecipe recipe) throws FITweakerRuntimeException
    {
        String key = recipe.ingredient.toString();
        if (recipeDict.containsKey(key))
            throw new FITweakerRuntimeException("FluidBehaviorRecipeManager.addRecipe() fails to execute. The same recipe key " + key + " has been added.");
        else
        {
            recipeDict.put(key, recipe.complexOutput);

            // jei compat
            if (FluidInteractionTweaker.IS_JEI_LOADED && autoAddJEIRecipe && FMLCommonHandler.instance().getSide().isClient())
                JEFBPlugin.addRecipeWrapper(recipe);

            return key;
        }
    }
    public static void removeRecipe(FluidBehaviorRecipe recipe) throws FITweakerRuntimeException
    {
        String key = recipe.ingredient.toString();
        if (recipeDict.containsKey(key))
            recipeDict.remove(key);
        else
            throw new FITweakerRuntimeException("FluidBehaviorRecipeManager.removeRecipe() fails to execute. The recipe key " + key + " being removed doesn't exist.");
    }
    public static void removeAllRecipes()
    {
        recipeDict.clear();
    }
    //</editor-fold>
}
