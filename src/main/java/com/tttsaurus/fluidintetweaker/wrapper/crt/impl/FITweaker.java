package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import com.tttsaurus.fluidintetweaker.client.jefi.impl.JEFIPlugin;
import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.interaction.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.ByChance;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.FluidLevel;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IsInitiatorAbove;
import com.tttsaurus.fluidintetweaker.common.impl.interaction.FluidInteractionRecipeManager;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import crafttweaker.annotations.ZenRegister;
import java.util.List;

@ZenRegister
@ZenClass("mods.fluidintetweaker.FITweaker")
public final class FITweaker
{
    @ZenMethod
    public static ComplexOutputBuilder outputBuilder() { return new ComplexOutputBuilder(); }
    @ZenMethod
    public static InteractionEventBuilder eventBuilder() { return new InteractionEventBuilder(); }

    //<editor-fold desc="addRecipe (simple block output)">
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                liquidSurrounding,
                isSourceB,
                ComplexOutput.createSimpleBlockOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                liquidSurrounding,
                ComplexOutput.createSimpleBlockOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                liquidSurrounding,
                ComplexOutput.createSimpleBlockOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }

    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, IBlockState blockSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                blockSurrounding,
                ComplexOutput.createSimpleBlockOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, IBlockState blockSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                blockSurrounding,
                ComplexOutput.createSimpleBlockOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }

    @ZenMethod
    public static List<String> addRecipe(IBlockState blockInitiator, ILiquidStack liquidSurrounding, boolean isSourceB, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                blockInitiator,
                liquidSurrounding,
                isSourceB,
                ComplexOutput.createSimpleBlockOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(IBlockState blockInitiator, ILiquidStack liquidSurrounding, IBlockState outputBlock, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                blockInitiator,
                liquidSurrounding,
                ComplexOutput.createSimpleBlockOutput((net.minecraft.block.state.IBlockState)outputBlock.getInternal()),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    //</editor-fold>

    //<editor-fold desc="addRecipe (complex output)">
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                liquidSurrounding,
                isSourceB,
                output,
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                liquidSurrounding,
                output,
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                liquidSurrounding,
                output,
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }

    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, IBlockState blockSurrounding, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                blockSurrounding,
                output,
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, IBlockState blockSurrounding, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                blockSurrounding,
                output,
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }

    @ZenMethod
    public static List<String> addRecipe(IBlockState blockInitiator, ILiquidStack liquidSurrounding, boolean isSourceB, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                blockInitiator,
                liquidSurrounding,
                isSourceB,
                output,
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(IBlockState blockInitiator, ILiquidStack liquidSurrounding, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                blockInitiator,
                liquidSurrounding,
                output,
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    //</editor-fold>

    //<editor-fold desc="WorldIngredient constructor wrappers">
    private static WorldIngredient buildIngredient(ILiquidStack liquidStack, boolean isSource)
    {
        return new WorldIngredient(((FluidStack)liquidStack.getInternal()).getFluid(), isSource);
    }
    private static WorldIngredient buildIngredient(IBlockState blockState)
    {
        return new WorldIngredient((net.minecraft.block.state.IBlockState)blockState.getInternal());
    }
    //</editor-fold>

    @ZenMethod
    public static void autoAddJEIRecipe(boolean flag)
    {
        FluidInteractionRecipeManager.autoAddJEIRecipe = flag;
    }
    //<editor-fold desc="addJEIRecipeWrapper">
    @ZenMethod
    public static void addJEIRecipeWrapper(ILiquidStack liquidInitiator, int fluidStateA, ILiquidStack liquidSurrounding, int fluidStateB, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        JEFIPlugin.addRecipeWrapper(buildIngredient(liquidInitiator, fluidStateA == 0), fluidStateA == 2, buildIngredient(liquidSurrounding, fluidStateB == 0), fluidStateB == 2, output, extraInfoLocalizationKey);
    }

    @ZenMethod
    public static void addJEIRecipeWrapper(ILiquidStack liquidInitiator, int fluidStateA, IBlockState blockSurrounding, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        JEFIPlugin.addRecipeWrapper(buildIngredient(liquidInitiator, fluidStateA == 0), fluidStateA == 2, buildIngredient(blockSurrounding), output, extraInfoLocalizationKey);
    }

    @ZenMethod
    public static void addJEIRecipeWrapper(IBlockState blockInitiator, ILiquidStack liquidSurrounding, int fluidStateB, ComplexOutput output, @Optional String extraInfoLocalizationKey)
    {
        JEFIPlugin.addRecipeWrapper(buildIngredient(blockInitiator), buildIngredient(liquidSurrounding, fluidStateB == 0), fluidStateB == 2, output, extraInfoLocalizationKey);
    }
    //</editor-fold>

    /*
    //<editor-fold desc="removeAllRecipes">
    @ZenMethod
    public static void removeAllRecipes()
    {
        CraftTweakerAPI.apply(new FITActions.RemoveAllRecipesAction());
    }
    //</editor-fold>
    */

    public static class ComplexOutputBuilder
    {
        private final ComplexOutput complexOutput = ComplexOutput.create();

        @ZenMethod
        public ComplexOutputBuilder addEvent(InteractionEvent event)
        {
            if (event == null) return this;
            complexOutput.addEvent(event);
            return this;
        }
        @ZenMethod
        public ComplexOutput done()
        {
            return complexOutput;
        }
    }
    public static class InteractionEventBuilder
    {
        private InteractionEvent interactionEvent = null;

        @ZenMethod
        public InteractionEventBuilder createSetBlockEvent(IBlockState blockState)
        {
            interactionEvent = InteractionEvent.createSetBlockEvent((net.minecraft.block.state.IBlockState)blockState.getInternal());
            return this;
        }
        @ZenMethod
        public InteractionEventBuilder createExplosionEvent(float strength, boolean damagesTerrain)
        {
            interactionEvent = InteractionEvent.createExplosionEvent(strength, damagesTerrain);
            return this;
        }
        @ZenMethod
        public InteractionEventBuilder createSpawnEntityEvent(String id)
        {
            interactionEvent = InteractionEvent.createSpawnEntityEvent(id);
            return this;
        }
        @ZenMethod
        public InteractionEventBuilder createSpawnEntityItemEvent(IItemStack itemStack, int amount)
        {
            interactionEvent = InteractionEvent.createSpawnEntityItemEvent((ItemStack)itemStack.getInternal(), amount);
            return this;
        }
        @ZenMethod
        public InteractionEventBuilder createSetFluidEvent(ILiquidStack liquidStack, boolean isSpreadingUpward/*, IBlockState limitBarrier*/)
        {
            interactionEvent = InteractionEvent.createSetFluidEvent(((FluidStack)liquidStack.getInternal()).getFluid(), isSpreadingUpward/*, (net.minecraft.block.state.IBlockState)limitBarrier.getInternal()*/);
            return this;
        }
        @ZenMethod
        public InteractionEventBuilder createSetFluidEvent(ILiquidStack liquidStack)
        {
            interactionEvent = InteractionEvent.createSetFluidEvent(((FluidStack)liquidStack.getInternal()).getFluid());
            return this;
        }
        @ZenMethod
        public InteractionEventBuilder addCondition(String className, Object[] params)
        {
            IEventCondition condition = null;

            if (className.equals("ByChance"))
            {
                condition = new ByChance((float)params[0]);
            }
            else if (className.equals("IsInitiatorAbove"))
            {
                condition = new IsInitiatorAbove();
            }
            else if (className.equals("FluidLevel"))
            {
                condition = new FluidLevel((int)params[0], (int)params[0]);
            }

            if (condition == null || interactionEvent == null) return this;
            interactionEvent.addCondition(condition);
            return this;
        }
        @ZenMethod
        public InteractionEvent done()
        {
            return interactionEvent;
        }
    }
}
