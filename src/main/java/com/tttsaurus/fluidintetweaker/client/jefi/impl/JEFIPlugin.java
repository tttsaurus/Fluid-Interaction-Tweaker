package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.Configuration;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.jefi.JustEnoughFluidInteractions;
import com.tttsaurus.fluidintetweaker.common.api.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.*;

@SideOnly(Side.CLIENT)
@JEIPlugin
public class JEFIPlugin implements IModPlugin
{
    private static final LinkedHashMap<String, JEFIRecipeWrapper> recipeWrapperDict = new LinkedHashMap<>();

    private static void addRecipeWrapper(InteractionIngredient ingredientA, boolean isAnyFluidStateA, InteractionIngredient ingredientB, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredientA, isAnyFluidStateA, ingredientB, complexOutput, null);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, boolean isAnyFluidStateA, InteractionIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        addRecipeWrapper(ingredientA, isAnyFluidStateA, ingredientB, false, complexOutput, extraInfoLocalizationKey);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, InteractionIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredientA, ingredientB, isAnyFluidStateB, complexOutput, null);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, InteractionIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        addRecipeWrapper(ingredientA, false, ingredientB, isAnyFluidStateB, complexOutput, extraInfoLocalizationKey);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, boolean isAnyFluidStateA, InteractionIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredientA, isAnyFluidStateA, ingredientB, isAnyFluidStateB, complexOutput, null);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, boolean isAnyFluidStateA, InteractionIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        JEFIRecipeWrapper recipeWrapper = new JEFIRecipeWrapper(ingredientA, ingredientB, complexOutput, extraInfoLocalizationKey);
        recipeWrapper.isAnyFluidStateA = isAnyFluidStateA;
        recipeWrapper.isAnyFluidStateB = isAnyFluidStateB;

        recipeWrapperDict.put(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB), recipeWrapper);
    }

    public static void addRecipeWrapper(String recipeKey, InteractionIngredient ingredientA, InteractionIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        JEFIRecipeWrapper recipeWrapper = new JEFIRecipeWrapper(ingredientA, ingredientB, complexOutput, extraInfoLocalizationKey);
        recipeWrapperDict.put(recipeKey, recipeWrapper);

        //<editor-fold desc="combine `source` and `flowing` if they both exist">
        if (ingredientA.getIngredientType() == InteractionIngredientType.FLUID)
        {
            ingredientA.setIsFluidSource(!ingredientA.getIsFluidSource());
            String newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
            if (recipeWrapperDict.containsKey(newKey) &&
                BlockUtil.toString(recipeWrapperDict.get(newKey).complexOutput.getSimpleBlockOutput()).equals(BlockUtil.toString(complexOutput.getSimpleBlockOutput())))
            {
                recipeWrapperDict.remove(newKey);
                recipeWrapper.isAnyFluidStateA = true;
            }
            ingredientA.setIsFluidSource(!ingredientA.getIsFluidSource());
        }

        if (ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
        {
            ingredientB.setIsFluidSource(!ingredientB.getIsFluidSource());
            String newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
            if (recipeWrapperDict.containsKey(newKey) &&
                BlockUtil.toString(recipeWrapperDict.get(newKey).complexOutput.getSimpleBlockOutput()).equals(BlockUtil.toString(complexOutput.getSimpleBlockOutput())))
            {
                recipeWrapperDict.remove(newKey);
                recipeWrapper.isAnyFluidStateB = true;
            }
            ingredientB.setIsFluidSource(!ingredientB.getIsFluidSource());
        }
        //</editor-fold>
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void register(IModRegistry registry)
    {
        if (Configuration.enableLavaAndWaterRecipeInJEI)
        {
            addRecipeWrapper(InteractionIngredient.SOURCE_LAVA, InteractionIngredient.FLOWING_WATER, true, new ComplexOutput(Blocks.OBSIDIAN.getDefaultState()));
            addRecipeWrapper(InteractionIngredient.FLOWING_LAVA, InteractionIngredient.FLOWING_WATER, true, new ComplexOutput(Blocks.COBBLESTONE.getDefaultState()), "fluidintetweaker.jefi.extra_info_for_lava_and_water");
        }
        if (Configuration.enableThermalFoundationJEICompat && FluidInteractionTweaker.IS_THERMALFOUNDATION_LOADED)
        {
            /*
                Primal Mana Todos:
                adjacent blocks are set on fire or covered with snow layers;
                dirt is turned into grass blocks; ✅
                coarse dirt is turned into podzol; ✅
                farmland is turned into mycelium;
                glass is turned into sand;
                redstone ore lights up;
                lapis lazuli ore is turned into lapis lazuli blocks;
                silver ore is turned into mana infused ore;
                lead ore is turned into gold ore;
                blocks of silver are turned into blocks of mana infused metal;
                blocks of lead are turned into blocks of gold.
            */

            InteractionIngredient FLOWING_MANA = new InteractionIngredient(FluidRegistry.getFluid("mana"), false);
            addRecipeWrapper(new InteractionIngredient(Blocks.DIRT.getDefaultState()), FLOWING_MANA, true, new ComplexOutput(Blocks.GRASS_PATH.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.DIRT.getStateFromMeta(1)), FLOWING_MANA, true, new ComplexOutput(Blocks.GRASS_PATH.getStateFromMeta(1)));
            
        }
        if (Configuration.enableBiomesOPlentyJEICompat && FluidInteractionTweaker.IS_BIOMESOPLENTY_LOADED)
        {

        }

        registry.addRecipes(recipeWrapperDict.values(), JustEnoughFluidInteractions.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new JEFICategory(registry.getJeiHelpers().getGuiHelper()));
    }
}
