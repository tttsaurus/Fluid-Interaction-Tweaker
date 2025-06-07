package com.tttsaurus.fluidintetweaker.common.core.interaction;

import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;
import javax.annotation.Nullable;

public final class StringRecipeProtocol
{
    public static String getRecipeKeyFromTwoIngredients(WorldIngredient ingredientA, WorldIngredient ingredientB)
    {
        return ingredientA.toString() + "+" + ingredientB.toString();
    }
    @Nullable
    public static String[] splitRecipeKeyToTwoRawStrings(String recipeKey)
    {
        String[] strings = recipeKey.split("\\+");
        if (strings.length == 2)
            return strings;
        else
            return null;
    }
}
