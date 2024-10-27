package com.tttsaurus.fluidintetweaker.client.jefb.impl;

import com.tttsaurus.fluidintetweaker.client.jefb.JustEnoughFluidBehavior;
import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.behavior.FluidBehaviorRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import java.util.LinkedHashMap;

@SuppressWarnings("all")
@SideOnly(Side.CLIENT)
@JEIPlugin
public class JEFBPlugin implements IModPlugin
{
    private static final LinkedHashMap<String, JEFBRecipeWrapper> recipeWrapperDict = new LinkedHashMap<>();

    //<editor-fold desc="adding recipe wrappers manually">
    public static void addRecipeWrapper(WorldIngredient ingredient, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredient, false, complexOutput);
    }
    public static void addRecipeWrapper(WorldIngredient ingredient, boolean isAnyFluidState, ComplexOutput complexOutput)
    {
        JEFBRecipeWrapper recipeWrapper = new JEFBRecipeWrapper(new FluidBehaviorRecipe(ingredient, complexOutput));
        recipeWrapper.isAnyFluidState = isAnyFluidState;

        recipeWrapperDict.put(ingredient.toString(), recipeWrapper);
    }
    //</editor-fold>

    public static void addRecipeWrapper(String recipeKey, FluidBehaviorRecipe recipe)
    {
        JEFBRecipeWrapper recipeWrapper = new JEFBRecipeWrapper(recipe);
        recipeWrapperDict.put(recipeKey, recipeWrapper);

        //<editor-fold desc="combine `source` and `flowing` if they both exist">
        recipe.ingredient.setIsFluidSource(!recipe.ingredient.getIsFluidSource());
        String newKey = recipe.ingredient.toString();
        if (recipeWrapperDict.containsKey(newKey) &&
            recipeWrapperDict.get(newKey).recipe.complexOutput.equals(recipe.complexOutput))
        {
            recipeWrapperDict.remove(newKey);
            recipeWrapper.isAnyFluidState = true;
        }
        recipe.ingredient.setIsFluidSource(!recipe.ingredient.getIsFluidSource());
        //</editor-fold>
    }

    @Override
    public void register(@NotNull IModRegistry registry)
    {
        registry.addRecipes(recipeWrapperDict.values(), JustEnoughFluidBehavior.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new JEFBCategory(registry.getJeiHelpers().getGuiHelper()));
    }
}
