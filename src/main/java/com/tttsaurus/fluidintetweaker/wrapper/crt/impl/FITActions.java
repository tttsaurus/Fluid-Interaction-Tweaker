package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import com.tttsaurus.fluidintetweaker.common.api.interaction.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.interaction.StringRecipeProtocol;
import com.tttsaurus.fluidintetweaker.common.impl.interaction.FluidInteractionRecipeManager;
import com.tttsaurus.fluidintetweaker.common.api.exception.FluidInteractionTweakerRuntimeException;
import crafttweaker.IAction;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import youyihj.zenutils.api.reload.Reloadable;
import youyihj.zenutils.api.util.ReflectionInvoked;
import java.util.*;

public final class FITActions
{
    @Reloadable
    public static final class AddRecipesAction implements IAction
    {
        private final List<FluidInteractionRecipe> recipeList = new ArrayList<>();
        public final List<String> recipeKeys = new ArrayList<>();

        //<editor-fold desc="WorldIngredient constructor wrappers">
        private WorldIngredient buildIngredient(ILiquidStack liquidStack, boolean isSource)
        {
            return new WorldIngredient(((FluidStack)liquidStack.getInternal()).getFluid(), isSource);
        }
        private WorldIngredient buildIngredient(IBlockState blockState)
        {
            return new WorldIngredient((net.minecraft.block.state.IBlockState)blockState.getInternal());
        }
        //</editor-fold>

        //<editor-fold desc="fluid & fluid recipes">
        public AddRecipesAction(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
        {
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(liquidSurrounding, isSourceB),
                    complexOutput,
                    extraInfoLocalizationKey));
        }
        public AddRecipesAction(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, ComplexOutput complexOutput, String extraInfoLocalizationKey)
        {
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(liquidSurrounding, true),
                    complexOutput,
                    extraInfoLocalizationKey));
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(liquidSurrounding, false),
                    complexOutput,
                    extraInfoLocalizationKey));
        }
        public AddRecipesAction(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, ComplexOutput complexOutput, String extraInfoLocalizationKey)
        {
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, true),
                    buildIngredient(liquidSurrounding, true),
                    complexOutput,
                    extraInfoLocalizationKey));
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, true),
                    buildIngredient(liquidSurrounding, false),
                    complexOutput,
                    extraInfoLocalizationKey));
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, false),
                    buildIngredient(liquidSurrounding, true),
                    complexOutput,
                    extraInfoLocalizationKey));
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, false),
                    buildIngredient(liquidSurrounding, false),
                    complexOutput,
                    extraInfoLocalizationKey));
        }
        //</editor-fold>

        //<editor-fold desc="fluid & block recipes">
        public AddRecipesAction(ILiquidStack liquidInitiator, boolean isSourceA, IBlockState blockSurrounding, ComplexOutput complexOutput, String extraInfoLocalizationKey)
        {
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(blockSurrounding),
                    complexOutput,
                    extraInfoLocalizationKey));
        }
        public AddRecipesAction(ILiquidStack liquidInitiator, IBlockState blockSurrounding, ComplexOutput complexOutput, String extraInfoLocalizationKey)
        {
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, true),
                    buildIngredient(blockSurrounding),
                    complexOutput,
                    extraInfoLocalizationKey));
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(liquidInitiator, false),
                    buildIngredient(blockSurrounding),
                    complexOutput,
                    extraInfoLocalizationKey));
        }
        //</editor-fold>

        //<editor-fold desc="block & fluid recipes">
        public AddRecipesAction(IBlockState blockInitiator, ILiquidStack liquidSurrounding, boolean isSourceB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
        {
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(blockInitiator),
                    buildIngredient(liquidSurrounding, isSourceB),
                    complexOutput,
                    extraInfoLocalizationKey));
        }
        public AddRecipesAction(IBlockState blockInitiator, ILiquidStack liquidSurrounding, ComplexOutput complexOutput, String extraInfoLocalizationKey)
        {
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(blockInitiator),
                    buildIngredient(liquidSurrounding, true),
                    complexOutput,
                    extraInfoLocalizationKey));
            recipeList.add(new FluidInteractionRecipe(
                    buildIngredient(blockInitiator),
                    buildIngredient(liquidSurrounding, false),
                    complexOutput,
                    extraInfoLocalizationKey));
        }
        //</editor-fold>

        @ReflectionInvoked
        public void undo() throws FluidInteractionTweakerRuntimeException
        {
            for (FluidInteractionRecipe recipe: recipeList)
                FluidInteractionRecipeManager.removeRecipe(recipe);
            FluidInteractionRecipeManager.refreshIngredientABLists();
        }
        @Override
        public void apply() throws FluidInteractionTweakerRuntimeException
        {
            for (FluidInteractionRecipe recipe: recipeList)
                recipeKeys.add(FluidInteractionRecipeManager.addRecipe(recipe));
        }
        @Override
        public String describe()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Added fluid interaction recipe(s): ");
            int length = recipeList.size();
            for (int i = 0; i < length; i++)
            {
                FluidInteractionRecipe recipe = recipeList.get(i);
                builder.append(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(recipe.ingredientA, recipe.ingredientB));
                       //.append("->")
                       //.append(BlockUtils.toString(recipe.complexOutput.getSimpleBlockOutput()));
                if (i != length - 1) builder.append(", ");
            }
            return builder.toString();
        }
    }

    /*
    @Reloadable
    public static final class RemoveAllRecipesAction implements IAction
    {
        //private HashMap<String, Block> recipeDictBackup;

        public RemoveAllRecipesAction()
        {

        }

        // don't need an undo
        @ReflectionInvoked
        public void undo()
        {
            //FluidInteractionRecipeManager.setAllRecipes(recipeDictBackup);
            //FluidInteractionRecipeManager.refreshIngredientABLists();
        }
        @Override
        public void apply()
        {
            //recipeDictBackup = (HashMap<String, Block>)FluidInteractionRecipeManager.getAllRecipes().clone();
            FluidInteractionRecipeManager.removeAllRecipes();
        }
        @Override
        public String describe()
        {
            return "Removed all fluid interaction recipes";
        }
    }
    */
}
