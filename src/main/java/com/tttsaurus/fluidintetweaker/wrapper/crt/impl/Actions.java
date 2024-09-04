package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import com.tttsaurus.fluidintetweaker.common.api.StringRecipeProtocol;
import com.tttsaurus.fluidintetweaker.common.impl.FluidInteractionRecipeManager;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.exception.FluidInteractionTweakerRuntimeException;
import crafttweaker.IAction;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.FluidStack;
import youyihj.zenutils.api.reload.Reloadable;
import youyihj.zenutils.api.util.ReflectionInvoked;
import java.util.*;

public final class Actions
{
    @Reloadable
    public static final class AddRecipesAction implements IAction
    {
        private static final class Parameters
        {
            public InteractionIngredient ingredientA;
            public InteractionIngredient ingredientB;
            public Block outputBlock;

            public Parameters(InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock)
            {
                this.ingredientA = ingredientA;
                this.ingredientB = ingredientB;
                this.outputBlock = outputBlock;
            }
        }
        private final List<Parameters> parametersList = new ArrayList<>();
        public final List<String> recipeKeys = new ArrayList<>();

        //<editor-fold desc="InteractionIngredient constructor wrappers">
        private InteractionIngredient buildIngredient(ILiquidStack liquidStack, boolean isSource)
        {
            return new InteractionIngredient(((FluidStack)liquidStack.getInternal()).getFluid(), isSource);
        }
        private InteractionIngredient buildIngredient(IBlock block)
        {
            return new InteractionIngredient((Block)block.getDefinition().getInternal());
        }
        //</editor-fold>

        //<editor-fold desc="fluid & fluid recipes">
        public AddRecipesAction(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, IBlock outputBlock)
        {
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(liquidSurrounding, isSourceB),
                    (Block)outputBlock.getDefinition().getInternal()));
        }
        public AddRecipesAction(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, IBlock outputBlock)
        {
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(liquidSurrounding, true),
                    (Block)outputBlock.getDefinition().getInternal()));
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(liquidSurrounding, false),
                    (Block)outputBlock.getDefinition().getInternal()));
        }
        public AddRecipesAction(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, IBlock outputBlock)
        {
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, true),
                    buildIngredient(liquidSurrounding, true),
                    (Block)outputBlock.getDefinition().getInternal()));
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, true),
                    buildIngredient(liquidSurrounding, false),
                    (Block)outputBlock.getDefinition().getInternal()));
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, false),
                    buildIngredient(liquidSurrounding, true),
                    (Block)outputBlock.getDefinition().getInternal()));
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, false),
                    buildIngredient(liquidSurrounding, false),
                    (Block)outputBlock.getDefinition().getInternal()));
        }
        //</editor-fold>

        //<editor-fold desc="fluid & block recipes">
        public AddRecipesAction(ILiquidStack liquidInitiator, boolean isSourceA, IBlock blockSurrounding, IBlock outputBlock)
        {
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(blockSurrounding),
                    (Block)outputBlock.getDefinition().getInternal()));
        }
        public AddRecipesAction(ILiquidStack liquidInitiator, IBlock blockSurrounding, IBlock outputBlock)
        {
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, true),
                    buildIngredient(blockSurrounding),
                    (Block)outputBlock.getDefinition().getInternal()));
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, false),
                    buildIngredient(blockSurrounding),
                    (Block)outputBlock.getDefinition().getInternal()));
        }
        //</editor-fold>

        @ReflectionInvoked
        public void undo() throws FluidInteractionTweakerRuntimeException
        {
            for (Parameters parameters: parametersList)
            {
                FluidInteractionRecipeManager.removeRecipe(
                        parameters.ingredientA,
                        parameters.ingredientB);
            }
            FluidInteractionRecipeManager.refreshIngredientABLists();
        }
        @Override
        public void apply() throws FluidInteractionTweakerRuntimeException
        {
            for (Parameters parameters: parametersList)
            {
                String recipe = FluidInteractionRecipeManager.addRecipe(
                        parameters.ingredientA,
                        parameters.ingredientB,
                        parameters.outputBlock);
                recipeKeys.add(recipe);
            }
        }
        @Override
        public String describe()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Added fluid interaction recipe(s): ");
            int length = parametersList.size();
            for (int i = 0; i < length; i++)
            {
                Parameters p = parametersList.get(i);
                builder.append(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(p.ingredientA, p.ingredientB))
                       .append("->")
                       .append(p.outputBlock.toString());
                if (i != length - 1) builder.append(", ");
            }
            return builder.toString();
        }
    }

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
}
