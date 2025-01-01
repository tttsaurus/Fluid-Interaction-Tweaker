package com.tttsaurus.fluidintetweaker.plugin.crt.impl;

import com.tttsaurus.fluidintetweaker.client.impl.jefb.JEFBPlugin;
import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.behavior.BehaviorEvent;
import com.tttsaurus.fluidintetweaker.common.api.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.impl.behavior.condition.ByChance;
import com.tttsaurus.fluidintetweaker.common.api.behavior.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.impl.behavior.FluidBehaviorRecipeManager;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.oredict.OreIngredient;
import stanhebben.zenscript.annotations.*;
import java.util.List;

@ZenRegister
@ZenClass("mods.fluidintetweaker.FBTweaker")
public final class FBTweaker
{
    public enum EnumCondition
    {
        ByChance
    }
    @ZenRegister
    @ZenClass("mods.fluidintetweaker.behavior.Condition")
    public static class EnumConditionWrapper
    {
        protected final EnumCondition enumCondition;
        private EnumConditionWrapper(EnumCondition enumCondition)
        {
            this.enumCondition = enumCondition;
        }

        @ZenProperty
        public static final EnumConditionWrapper byChance = new EnumConditionWrapper(EnumCondition.ByChance);
    }

    @ZenMethod
    public static ComplexOutputBuilder outputBuilder() { return new ComplexOutputBuilder(); }
    @ZenMethod
    public static BehaviorEventBuilder eventBuilder() { return new BehaviorEventBuilder(); }

    //<editor-fold desc="addRecipe">
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquid, boolean isSource, ComplexOutputBuilder complexOutputBuilder)
    {
        FBTActions.AddRecipesAction action = new FBTActions.AddRecipesAction(liquid, isSource, complexOutputBuilder.done());
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquid, ComplexOutputBuilder complexOutputBuilder)
    {
        FBTActions.AddRecipesAction action = new FBTActions.AddRecipesAction(liquid, complexOutputBuilder.done());
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    //</editor-fold>

    //<editor-fold desc="WorldIngredient constructor wrapper">
    private static WorldIngredient buildIngredient(ILiquidStack liquidStack, boolean isSource)
    {
        return new WorldIngredient(((FluidStack)liquidStack.getInternal()).getFluid(), isSource);
    }
    //</editor-fold>

    @ZenMethod
    public static void autoAddJEIRecipe(boolean flag)
    {
        FluidBehaviorRecipeManager.autoAddJEIRecipe = flag;
    }
    //<editor-fold desc="addJEIRecipeWrapper">
    @ZenMethod
    public static void addJEIRecipeWrapper(ILiquidStack liquid, int fluidState, ComplexOutputBuilder complexOutputBuilder)
    {
        if (FMLCommonHandler.instance().getSide().isClient())
            JEFBPlugin.addRecipeWrapper(buildIngredient(liquid, fluidState == 0), fluidState == 2, complexOutputBuilder.done());
    }
    //</editor-fold>

    @ZenRegister
    @ZenClass("mods.fluidintetweaker.behavior.ComplexOutputBuilder")
    public static class ComplexOutputBuilder
    {
        private final ComplexOutput complexOutput = ComplexOutput.create();

        @ZenMethod
        public ComplexOutputBuilder addEvent(BehaviorEventBuilder eventBuilder)
        {
            BehaviorEvent event = eventBuilder.done();
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
    @ZenClass("mods.fluidintetweaker.behavior.BehaviorEventBuilder")
    public static class BehaviorEventBuilder
    {
        private BehaviorEvent behaviorEvent = null;

        @ZenMethod
        public BehaviorEventBuilder createPotionEffectEvent(String id, int duration, int amplifier)
        {
            behaviorEvent = BehaviorEvent.createPotionEffectEvent(id, duration, amplifier);
            return this;
        }
        @ZenMethod
        public BehaviorEventBuilder createEntityConversionEvent(IEntityDefinition entityDefinitionFrom, IEntityDefinition entityDefinitionTo)
        {
            behaviorEvent = BehaviorEvent.createEntityConversionEvent((EntityEntry)entityDefinitionFrom.getInternal(), (EntityEntry)entityDefinitionTo.getInternal());
            return this;
        }
        @ZenMethod
        public BehaviorEventBuilder createExtinguishFireEvent()
        {
            behaviorEvent = BehaviorEvent.createExtinguishFireEvent();
            return this;
        }
        @ZenMethod
        public BehaviorEventBuilder createSetFireEvent()
        {
            behaviorEvent = BehaviorEvent.createSetFireEvent();
            return this;
        }
        @ZenMethod
        public BehaviorEventBuilder createSetSnowEvent()
        {
            behaviorEvent = BehaviorEvent.createSetSnowEvent();
            return this;
        }
        @ZenMethod
        public BehaviorEventBuilder createTeleportEvent(int xRange, int yRange)
        {
            behaviorEvent = BehaviorEvent.createTeleportEvent(xRange, yRange);
            return this;
        }
        @ZenMethod
        public BehaviorEventBuilder createBreakSurroundingEvent(IIngredient ingredient)
        {
            Ingredient mcIngredient = null;
            if (ingredient instanceof IOreDictEntry oreDictEntry)
                mcIngredient = new OreIngredient(oreDictEntry.getName());
            if (mcIngredient != null)
                behaviorEvent = BehaviorEvent.createBreakSurroundingEvent(mcIngredient);
            return this;
        }
        @ZenMethod
        public BehaviorEventBuilder addCondition(EnumConditionWrapper conditionWrapper, Object[] params)
        {
            IEventCondition condition = null;
            EnumCondition enumCondition = conditionWrapper.enumCondition;

            if (enumCondition == EnumCondition.ByChance)
                condition = new ByChance((float)params[0]);

            if (condition == null || behaviorEvent == null) return this;
            behaviorEvent.addCondition(condition);
            return this;
        }
        public BehaviorEvent done()
        {
            return behaviorEvent;
        }
    }
}
