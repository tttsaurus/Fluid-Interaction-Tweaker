package com.tttsaurus.fluidintetweaker.plugin.crt.impl;

import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.behavior.FluidBehaviorRecipe;
import com.tttsaurus.fluidintetweaker.common.api.exception.FluidInteractionTweakerRuntimeException;
import com.tttsaurus.fluidintetweaker.common.api.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.impl.behavior.FluidBehaviorRecipeManager;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import youyihj.zenutils.api.reload.Reloadable;
import youyihj.zenutils.api.util.ReflectionInvoked;
import java.util.ArrayList;
import java.util.List;

public final class FBTActions
{
    @Reloadable
    public static final class AddRecipesAction implements IAction
    {
        private final List<FluidBehaviorRecipe> recipeList = new ArrayList<>();
        public final List<String> recipeKeys = new ArrayList<>();

        //<editor-fold desc="WorldIngredient constructor wrapper">
        private static WorldIngredient buildIngredient(ILiquidStack liquidStack, boolean isSource)
        {
            return new WorldIngredient(((FluidStack)liquidStack.getInternal()).getFluid(), isSource);
        }
        //</editor-fold>

        public AddRecipesAction(ILiquidStack liquid, boolean isSource, ComplexOutput complexOutput)
        {
            recipeList.add(new FluidBehaviorRecipe(
                    buildIngredient(liquid, isSource),
                    complexOutput));
        }
        public AddRecipesAction(ILiquidStack liquid, ComplexOutput complexOutput)
        {
            recipeList.add(new FluidBehaviorRecipe(
                    buildIngredient(liquid, true),
                    complexOutput));
            recipeList.add(new FluidBehaviorRecipe(
                    buildIngredient(liquid, false),
                    complexOutput));
        }

        @ReflectionInvoked
        public void undo() throws FluidInteractionTweakerRuntimeException
        {
            for (FluidBehaviorRecipe recipe: recipeList)
                FluidBehaviorRecipeManager.removeRecipe(recipe);
        }
        @Override
        public void apply() throws FluidInteractionTweakerRuntimeException
        {
            for (FluidBehaviorRecipe recipe: recipeList)
                recipeKeys.add(FluidBehaviorRecipeManager.addRecipe(recipe));
        }
        @Override
        public String describe()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Added fluid behavior recipe(s): ");
            int length = recipeList.size();
            for (int i = 0; i < length; i++)
            {
                FluidBehaviorRecipe recipe = recipeList.get(i);
                builder.append(recipe.ingredient.toString());
                if (i != length - 1) builder.append(", ");
            }
            return builder.toString();
        }
    }

    /*
    @Reloadable
    public static final class RemoveAllRecipesAction implements IAction
    {

    }
    */
}
