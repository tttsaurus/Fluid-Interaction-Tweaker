package com.tttsaurus.fluidintetweaker.common.api.behavior;

import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;

public class FluidBehaviorRecipe
{
    public WorldIngredient ingredient;
    public ComplexOutput complexOutput;

    public FluidBehaviorRecipe(WorldIngredient ingredient, ComplexOutput complexOutput)
    {
        this.ingredient = ingredient;
        this.complexOutput = complexOutput;
    }
}
