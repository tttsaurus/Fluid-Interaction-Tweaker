package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.client.jefi.JustEnoughFluidInteractions;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.StringRecipeProtocol;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.*;

@SideOnly(Side.CLIENT)
@JEIPlugin
public class JEFIPlugin implements IModPlugin
{
    private static final HashMap<String, JEFIRecipeWrapper> recipeWrapperDict = new HashMap<>();

    public static void addRecipeWrapper(String recipeKey, InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock)
    {
        JEFIRecipeWrapper recipeWrapper = new JEFIRecipeWrapper(ingredientA, ingredientB, outputBlock);
        recipeWrapperDict.put(recipeKey, recipeWrapper);

        ingredientA.setIsFluidSource(!ingredientA.getIsFluidSource());
        String newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
        if (recipeWrapperDict.containsKey(newKey) &&
            recipeWrapperDict.get(newKey).outputBlock.toString().equals(outputBlock.toString()))
        {
            recipeWrapperDict.remove(newKey);
            recipeWrapper.isAnyFluidStateA = true;
        }
        ingredientA.setIsFluidSource(!ingredientA.getIsFluidSource());

        ingredientB.setIsFluidSource(!ingredientB.getIsFluidSource());
        newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
        if (recipeWrapperDict.containsKey(newKey) &&
            recipeWrapperDict.get(newKey).outputBlock.toString().equals(outputBlock.toString()))
        {
            recipeWrapperDict.remove(newKey);
            recipeWrapper.isAnyFluidStateB = true;
        }
        ingredientB.setIsFluidSource(!ingredientB.getIsFluidSource());
    }

    @Override
    public void register(IModRegistry registry)
    {
        //recipeWrapperList.add(new JEFIRecipeWrapper(InteractionIngredient.SOURCE_LAVA, InteractionIngredient.SOURCE_WATER, Blocks.OBSIDIAN));
        //recipeWrapperList.add(new JEFIRecipeWrapper(InteractionIngredient.SOURCE_LAVA, InteractionIngredient.FLOWING_WATER, Blocks.OBSIDIAN));

        registry.addRecipes(recipeWrapperDict.values(), JustEnoughFluidInteractions.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new JEFICategory(registry.getJeiHelpers().getGuiHelper()));
    }
}
