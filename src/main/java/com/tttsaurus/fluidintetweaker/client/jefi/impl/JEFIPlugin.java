package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.client.jefi.JustEnoughFluidInteractions;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
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
    @Override
    public void register(IModRegistry registry)
    {
        List<JEFIRecipeWrapper> recipeWrapperList = new ArrayList<>();

        recipeWrapperList.add(new JEFIRecipeWrapper(InteractionIngredient.SOURCE_LAVA, InteractionIngredient.SOURCE_WATER, Blocks.OBSIDIAN));
        recipeWrapperList.add(new JEFIRecipeWrapper(InteractionIngredient.SOURCE_LAVA, InteractionIngredient.FLOWING_WATER, Blocks.OBSIDIAN));

        registry.addRecipes(recipeWrapperList, JustEnoughFluidInteractions.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new JEFICategory(registry.getJeiHelpers().getGuiHelper()));
    }
}
