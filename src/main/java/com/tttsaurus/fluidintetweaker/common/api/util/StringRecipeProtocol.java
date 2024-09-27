package com.tttsaurus.fluidintetweaker.common.api.util;

import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionIngredient;
import javax.annotation.Nullable;

public final class StringRecipeProtocol
{
    public static String getRecipeKeyFromTwoIngredients(InteractionIngredient ingredientA, InteractionIngredient ingredientB)
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
