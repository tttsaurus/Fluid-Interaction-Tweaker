package com.tttsaurus.fluidintetweaker.plugin.crt.impl;

import com.tttsaurus.fluidintetweaker.client.impl.jefi.JEFIPlugin;
import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.core.interaction.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.core.interaction.InteractionEvent;
import com.tttsaurus.fluidintetweaker.common.impl.interaction.condition.*;
import com.tttsaurus.fluidintetweaker.common.core.interaction.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.impl.interaction.FluidInteractionRecipeManager;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityEntry;
import stanhebben.zenscript.annotations.*;
import crafttweaker.annotations.ZenRegister;
import java.util.List;

@ZenRegister
@ZenClass("mods.fluidintetweaker.FITweaker")
public final class FITweaker
{
    public enum EnumCondition
    {
        ByChance,
        IsInitiatorAbove,
        FluidLevel,
        Biome,
        Dimension
    }
    @ZenRegister
    @ZenClass("mods.fluidintetweaker.interaction.Condition")
    public static class EnumConditionWrapper
    {
        protected final EnumCondition enumCondition;
        private EnumConditionWrapper(EnumCondition enumCondition)
        {
            this.enumCondition = enumCondition;
        }

        @ZenProperty
        public static final EnumConditionWrapper byChance = new EnumConditionWrapper(EnumCondition.ByChance);
        @ZenProperty
        public static final EnumConditionWrapper isInitiatorAbove = new EnumConditionWrapper(EnumCondition.IsInitiatorAbove);
        @ZenProperty
        public static final EnumConditionWrapper fluidLevel = new EnumConditionWrapper(EnumCondition.FluidLevel);
        @ZenProperty
        public static final EnumConditionWrapper biome = new EnumConditionWrapper(EnumCondition.Biome);
        @ZenProperty
        public static final EnumConditionWrapper dimension = new EnumConditionWrapper(EnumCondition.Dimension);
    }

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
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, boolean isSourceB, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                liquidSurrounding,
                isSourceB,
                complexOutputBuilder.done(),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                liquidSurrounding,
                complexOutputBuilder.done(),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, ILiquidStack liquidSurrounding, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                liquidSurrounding,
                complexOutputBuilder.done(),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }

    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, IBlockState blockSurrounding, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                isSourceA,
                blockSurrounding,
                complexOutputBuilder.done(),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquidInitiator, IBlockState blockSurrounding, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                liquidInitiator,
                blockSurrounding,
                complexOutputBuilder.done(),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }

    @ZenMethod
    public static List<String> addRecipe(IBlockState blockInitiator, ILiquidStack liquidSurrounding, boolean isSourceB, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                blockInitiator,
                liquidSurrounding,
                isSourceB,
                complexOutputBuilder.done(),
                extraInfoLocalizationKey);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(IBlockState blockInitiator, ILiquidStack liquidSurrounding, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        FITActions.AddRecipesAction action = new FITActions.AddRecipesAction(
                blockInitiator,
                liquidSurrounding,
                complexOutputBuilder.done(),
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
    public static void addJEIRecipeWrapper(ILiquidStack liquidInitiator, int fluidStateA, ILiquidStack liquidSurrounding, int fluidStateB, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        if (FMLCommonHandler.instance().getSide().isClient())
            JEFIPlugin.addRecipeWrapper(buildIngredient(liquidInitiator, fluidStateA == 0), fluidStateA == 2, buildIngredient(liquidSurrounding, fluidStateB == 0), fluidStateB == 2, complexOutputBuilder.done(), extraInfoLocalizationKey);
    }

    @ZenMethod
    public static void addJEIRecipeWrapper(ILiquidStack liquidInitiator, int fluidStateA, IBlockState blockSurrounding, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        if (FMLCommonHandler.instance().getSide().isClient())
            JEFIPlugin.addRecipeWrapper(buildIngredient(liquidInitiator, fluidStateA == 0), fluidStateA == 2, buildIngredient(blockSurrounding), complexOutputBuilder.done(), extraInfoLocalizationKey);
    }

    @ZenMethod
    public static void addJEIRecipeWrapper(IBlockState blockInitiator, ILiquidStack liquidSurrounding, int fluidStateB, ComplexOutputBuilder complexOutputBuilder, @Optional String extraInfoLocalizationKey)
    {
        if (FMLCommonHandler.instance().getSide().isClient())
            JEFIPlugin.addRecipeWrapper(buildIngredient(blockInitiator), buildIngredient(liquidSurrounding, fluidStateB == 0), fluidStateB == 2, complexOutputBuilder.done(), extraInfoLocalizationKey);
    }
    //</editor-fold>

    @ZenRegister
    @ZenClass("mods.fluidintetweaker.interaction.ComplexOutputBuilder")
    public static class ComplexOutputBuilder
    {
        private final ComplexOutput complexOutput = ComplexOutput.create();

        @ZenMethod
        public ComplexOutputBuilder addEvent(InteractionEventBuilder eventBuilder)
        {
            InteractionEvent event = eventBuilder.done();
            if (event == null) return this;
            complexOutput.addEvent(event);
            return this;
        }
        public ComplexOutput done()
        {
            return complexOutput;
        }
    }
    @ZenRegister
    @ZenClass("mods.fluidintetweaker.interaction.InteractionEventBuilder")
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
        public InteractionEventBuilder createExplosionEvent(float strength, boolean damagesTerrain, boolean hasParticles)
        {
            interactionEvent = InteractionEvent.createExplosionEvent(strength, damagesTerrain, hasParticles);
            return this;
        }
        @ZenMethod
        public InteractionEventBuilder createSpawnEntityEvent(IEntityDefinition entityDefinition)
        {
            interactionEvent = InteractionEvent.createSpawnEntityEvent((EntityEntry)entityDefinition.getInternal());
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
        public InteractionEventBuilder addCondition(EnumConditionWrapper conditionWrapper, Object[] params)
        {
            IEventCondition condition = null;
            EnumCondition enumCondition = conditionWrapper.enumCondition;

            if (enumCondition == EnumCondition.ByChance)
                condition = new ByChance((float)params[0]);
            else if (enumCondition == EnumCondition.IsInitiatorAbove)
                condition = new IsInitiatorAbove();
            else if (enumCondition == EnumCondition.FluidLevel)
                condition = new FluidLevel((int)params[0], (int)params[0]);
            else if (enumCondition == EnumCondition.Biome)
                condition = new Biome((String)params[0]);
            else if (enumCondition == EnumCondition.Dimension)
                condition = new Dimension((Integer)params[0]);

            if (condition == null || interactionEvent == null) return this;
            interactionEvent.addCondition(condition);
            return this;
        }
        @ZenMethod
        public InteractionEventBuilder setSound(String sound)
        {
            if (interactionEvent == null) return this;
            SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(new ResourceLocation(sound));
            if (soundEvent != null) interactionEvent.setSoundEvent(soundEvent);
            return this;
        }
        @ZenMethod
        public InteractionEventBuilder setParticle(String particle)
        {
            if (interactionEvent == null) return this;
            EnumParticleTypes particleType = EnumParticleTypes.getByName(particle);
            if (particleType != null) interactionEvent.setParticleType(particleType);
            return this;
        }

        public InteractionEvent done()
        {
            return interactionEvent;
        }
    }
}
