package com.tttsaurus.fluidintetweaker.common.api;

import net.minecraft.block.Block;

public class FluidInteractionRecipe
{
    public InteractionIngredient ingredientA;
    public InteractionIngredient ingredientB;
    public Block outputBlock;
    public String extraInfoLocalizationKey;

    public FluidInteractionRecipe(InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock, String extraInfoLocalizationKey)
    {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.outputBlock = outputBlock;
        this.extraInfoLocalizationKey = extraInfoLocalizationKey;
    }
}
