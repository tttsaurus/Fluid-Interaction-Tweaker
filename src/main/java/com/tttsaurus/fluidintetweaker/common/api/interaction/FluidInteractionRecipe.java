package com.tttsaurus.fluidintetweaker.common.api.interaction;

public class FluidInteractionRecipe
{
    public InteractionIngredient ingredientA;
    public InteractionIngredient ingredientB;
    public ComplexOutput complexOutput;
    public String extraInfoLocalizationKey;

    public FluidInteractionRecipe(InteractionIngredient ingredientA, InteractionIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.complexOutput = complexOutput;
        this.extraInfoLocalizationKey = extraInfoLocalizationKey;
    }
}
