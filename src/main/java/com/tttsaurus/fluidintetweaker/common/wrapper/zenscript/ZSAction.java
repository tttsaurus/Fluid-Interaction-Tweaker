package com.tttsaurus.fluidintetweaker.common.wrapper.zenscript;

import com.tttsaurus.fluidintetweaker.common.FluidInteractionRecipeManager;
import com.tttsaurus.fluidintetweaker.common.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.InteractionIngredientType;
import crafttweaker.IAction;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.FluidStack;
import youyihj.zenutils.api.reload.Reloadable;
import youyihj.zenutils.api.util.ReflectionInvoked;
import java.util.*;

public final class ZSAction
{
    @Reloadable
    public static final class AddRecipeAction implements IAction
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

        private InteractionIngredient buildIngredient(ILiquidStack liquidStack, boolean isSource)
        {
            return new InteractionIngredient(
                    InteractionIngredientType.FLUID,
                    ((FluidStack)liquidStack.getInternal()).getFluid(),
                    isSource,
                    null);
        }
        private InteractionIngredient buildIngredient(IBlock block)
        {
            return new InteractionIngredient(
                    InteractionIngredientType.BLOCK,
                    null,
                    false,
                    (Block)block.getDefinition().getInternal());
        }

        //<editor-fold desc="fluid & fluid recipe">
        public AddRecipeAction(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, IBlock outputBlock)
        {
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(liquidSurrounding, isSourceB),
                    (Block)outputBlock.getDefinition().getInternal()));
        }
        public AddRecipeAction(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, IBlock outputBlock)
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
        public AddRecipeAction(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, IBlock outputBlock)
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

        //<editor-fold desc="fluid & block recipe">
        public AddRecipeAction(ILiquidStack liquidInitiator, boolean isSourceA, IBlock blockSurrounding, IBlock outputBlock)
        {
            parametersList.add(new Parameters(
                    buildIngredient(liquidInitiator, isSourceA),
                    buildIngredient(blockSurrounding),
                    (Block)outputBlock.getDefinition().getInternal()));
        }
        public AddRecipeAction(ILiquidStack liquidInitiator, IBlock blockSurrounding, IBlock outputBlock)
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
        public void undo() throws RuntimeException
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
        public void apply() throws RuntimeException
        {
            for (Parameters parameters: parametersList)
            {
                FluidInteractionRecipeManager.addRecipe(
                        parameters.ingredientA,
                        parameters.ingredientB,
                        parameters.outputBlock);
            }
        }
        @Override
        public String describe()
        {
            return "Add fluid interaction recipes";
        }
    }

    @Reloadable
    public static final class RemoveAllRecipesAction implements IAction
    {
        private HashMap<String, Block> recipeDictBackup;

        public RemoveAllRecipesAction()
        {

        }

        @ReflectionInvoked
        public void undo()
        {
            FluidInteractionRecipeManager.setAllRecipes(recipeDictBackup);
            FluidInteractionRecipeManager.refreshIngredientABLists();
        }
        @SuppressWarnings("unchecked")
        @Override
        public void apply()
        {
            recipeDictBackup = (HashMap<String, Block>)FluidInteractionRecipeManager.getAllRecipes().clone();
            FluidInteractionRecipeManager.removeAllRecipes();
        }
        @Override
        public String describe()
        {
            return "Remove all fluid interaction recipes";
        }
    }
}
