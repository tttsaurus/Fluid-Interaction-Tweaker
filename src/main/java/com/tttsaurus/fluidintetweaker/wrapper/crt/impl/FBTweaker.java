package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import com.tttsaurus.fluidintetweaker.client.jefb.impl.JEFBPlugin;
import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.behavior.BehaviorEvent;
import com.tttsaurus.fluidintetweaker.common.api.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.behavior.condition.ByChance;
import com.tttsaurus.fluidintetweaker.common.api.behavior.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.impl.behavior.FluidBehaviorRecipeManager;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import java.util.List;

@ZenRegister
@ZenClass("mods.fluidintetweaker.FBTweaker")
public final class FBTweaker
{
    @ZenMethod
    public static ComplexOutputBuilder outputBuilder() { return new ComplexOutputBuilder(); }
    @ZenMethod
    public static BehaviorEventBuilder eventBuilder() { return new BehaviorEventBuilder(); }

    //<editor-fold desc="addRecipe">
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquid, boolean isSource, ComplexOutput complexOutput)
    {
        FBTActions.AddRecipesAction action = new FBTActions.AddRecipesAction(liquid, isSource, complexOutput);
        CraftTweakerAPI.apply(action);
        return action.recipeKeys;
    }
    @ZenMethod
    public static List<String> addRecipe(ILiquidStack liquid, ComplexOutput complexOutput)
    {
        FBTActions.AddRecipesAction action = new FBTActions.AddRecipesAction(liquid, complexOutput);
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
    public static void addJEIRecipeWrapper(ILiquidStack liquid, int fluidState, ComplexOutput complexOutput)
    {
        JEFBPlugin.addRecipeWrapper(buildIngredient(liquid, fluidState == 0), fluidState == 2, complexOutput);
    }
    //</editor-fold>

    @ZenRegister
    @ZenClass("mods.fluidintetweaker.behavior.ComplexOutputBuilder")
    public static class ComplexOutputBuilder
    {
        private final ComplexOutput complexOutput = ComplexOutput.create();

        @ZenMethod
        public ComplexOutputBuilder addEvent(BehaviorEvent event)
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
        public BehaviorEventBuilder createEntityConversionEvent(String idFrom, String idTo)
        {
            behaviorEvent = BehaviorEvent.createEntityConversionEvent(idFrom, idTo);
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
        public BehaviorEventBuilder addCondition(String className, Object[] params)
        {
            IEventCondition condition = null;

            if (className.equals("ByChance"))
            {
                condition = new ByChance((float)params[0]);
            }

            if (condition == null || behaviorEvent == null) return this;
            behaviorEvent.addCondition(condition);
            return this;
        }
        @ZenMethod
        public BehaviorEvent done()
        {
            return behaviorEvent;
        }
    }
}
