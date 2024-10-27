package com.tttsaurus.fluidintetweaker.common.api.behavior;

import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;

public class FluidBehaviorRecipe
{
    // this ingredient must be fluid
    public WorldIngredient ingredient;
    public ComplexOutput complexOutput;

    public FluidBehaviorRecipe(WorldIngredient ingredient, ComplexOutput complexOutput)
    {
        this.ingredient = ingredient;
        this.complexOutput = complexOutput;
    }
}
