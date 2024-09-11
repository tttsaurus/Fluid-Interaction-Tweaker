package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.Configuration;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.jefi.JustEnoughFluidInteractions;
import com.tttsaurus.fluidintetweaker.common.api.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredientType;
import com.tttsaurus.fluidintetweaker.common.api.StringRecipeProtocol;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.*;

@SideOnly(Side.CLIENT)
@JEIPlugin
public class JEFIPlugin implements IModPlugin
{
    private static final LinkedHashMap<String, JEFIRecipeWrapper> recipeWrapperDict = new LinkedHashMap<>();

    private static void addRecipeWrapper(InteractionIngredient ingredientA, InteractionIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        addRecipeWrapper(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB), ingredientA, ingredientB, complexOutput, extraInfoLocalizationKey);
    }
    public static void addRecipeWrapper(String recipeKey, InteractionIngredient ingredientA, InteractionIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        JEFIRecipeWrapper recipeWrapper = new JEFIRecipeWrapper(ingredientA, ingredientB, complexOutput, extraInfoLocalizationKey);
        recipeWrapperDict.put(recipeKey, recipeWrapper);

        ingredientA.setIsFluidSource(!ingredientA.getIsFluidSource());
        String newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
        if (recipeWrapperDict.containsKey(newKey) &&
            recipeWrapperDict.get(newKey).complexOutput.getLegacyOutputBlock().toString().equals(complexOutput.getLegacyOutputBlock().toString()))
        {
            recipeWrapperDict.remove(newKey);
            recipeWrapper.isAnyFluidStateA = true;
        }
        ingredientA.setIsFluidSource(!ingredientA.getIsFluidSource());

        if (ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
        {
            ingredientB.setIsFluidSource(!ingredientB.getIsFluidSource());
            newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
            if (recipeWrapperDict.containsKey(newKey) &&
                recipeWrapperDict.get(newKey).complexOutput.getLegacyOutputBlock().toString().equals(complexOutput.getLegacyOutputBlock().toString()))
            {
                recipeWrapperDict.remove(newKey);
                recipeWrapper.isAnyFluidStateB = true;
            }
            ingredientB.setIsFluidSource(!ingredientB.getIsFluidSource());
        }
    }

    @Override
    public void register(IModRegistry registry)
    {
        if (Configuration.enableLavaAndWaterRecipeInJEI)
        {
            addRecipeWrapper(InteractionIngredient.SOURCE_LAVA, InteractionIngredient.SOURCE_WATER, new ComplexOutput(Blocks.OBSIDIAN), null);
            addRecipeWrapper(InteractionIngredient.SOURCE_LAVA, InteractionIngredient.FLOWING_WATER, new ComplexOutput(Blocks.OBSIDIAN), null);
            addRecipeWrapper(InteractionIngredient.FLOWING_LAVA, InteractionIngredient.SOURCE_WATER, new ComplexOutput(Blocks.COBBLESTONE), "fluidintetweaker.jefi.extra_info_for_lava_and_water");
            addRecipeWrapper(InteractionIngredient.FLOWING_LAVA, InteractionIngredient.FLOWING_WATER, new ComplexOutput(Blocks.COBBLESTONE), "fluidintetweaker.jefi.extra_info_for_lava_and_water");
        }
        if (Configuration.enableThermalFoundationJEICompat && FluidInteractionTweaker.IS_THERMALFOUNDATION_LOADED)
        {

        }
        if (Configuration.enableBiomesOPlentyJEICompat && FluidInteractionTweaker.IS_THERMALFOUNDATION_LOADED)
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
