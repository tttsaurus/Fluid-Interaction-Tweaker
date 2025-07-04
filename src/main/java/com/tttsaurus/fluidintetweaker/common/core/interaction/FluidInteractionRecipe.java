package com.tttsaurus.fluidintetweaker.common.core.interaction;

import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;

public class FluidInteractionRecipe
{
    public WorldIngredient ingredientA;
    public WorldIngredient ingredientB;
    public ComplexOutput complexOutput;
    public String extraInfoLocalizationKey;

    public FluidInteractionRecipe(WorldIngredient ingredientA, WorldIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.complexOutput = complexOutput;
        this.extraInfoLocalizationKey = extraInfoLocalizationKey;
    }
}
