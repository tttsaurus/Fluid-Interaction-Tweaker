package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.jefi.JustEnoughFluidInteractions;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredientType;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JEFICategory implements IRecipeCategory<JEFIRecipeWrapper>
{
    public static final ResourceLocation BACKGROUND = new ResourceLocation(FluidInteractionTweaker.MODID, "textures/gui/jefi/background.png");
    public static final ResourceLocation ICON = new ResourceLocation(FluidInteractionTweaker.MODID, "textures/gui/jefi/icon.png");

    private final IDrawable backgroud, icon;

    public JEFICategory(IGuiHelper guiHelper)
    {
        backgroud = guiHelper.drawableBuilder(BACKGROUND, 0, 0, 140, 32).addPadding(0, 12, 0, 0).build();
        icon = guiHelper.drawableBuilder(ICON, 0, 0, 16, 16).setTextureSize(16, 16).build();
    }

    @Override
    public String getUid()
    {
        return JustEnoughFluidInteractions.UID;
    }

    @Override
    public String getTitle()
    {
        return I18n.format(JustEnoughFluidInteractions.CATEGORY);
    }

    @Override
    public String getModName()
    {
        return JustEnoughFluidInteractions.NAME;
    }

    @Override
    public IDrawable getBackground()
    {
        return backgroud;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEFIRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        // inputs & outputs
        if (recipeWrapper.ingredientA.getIngredientType() == InteractionIngredientType.FLUID &&
            recipeWrapper.ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
        {
            guiFluidStacks.init(0, true, 7, 15, 16, 16, 1000, false, null);
            guiFluidStacks.init(1, true, 47, 15, 16, 16, 1000, false, null);

            int length = recipeWrapper.complexOutput.getEvents().size();
            for (int i = 0; i < length; i++)
                guiItemStacks.init(i, false, 116 - length * 9 + i * 18, 14);
        }
        else if (recipeWrapper.ingredientA.getIngredientType() == InteractionIngredientType.FLUID &&
                 recipeWrapper.ingredientB.getIngredientType() == InteractionIngredientType.BLOCK)
        {
            guiFluidStacks.init(0, true, 7, 15, 16, 16, 1000, false, null);
            guiItemStacks.init(0, true, 47, 14);

            int length = recipeWrapper.complexOutput.getEvents().size();
            for (int i = 0; i < length; i++)
                guiItemStacks.init(i + 1, false, 116 - length * 9 + i * 18, 14);
        }
        else if (recipeWrapper.ingredientA.getIngredientType() == InteractionIngredientType.BLOCK &&
                 recipeWrapper.ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
        {
            guiItemStacks.init(0, true, 7, 14);
            guiFluidStacks.init(0, true, 47, 15, 16, 16, 1000, false, null);

            int length = recipeWrapper.complexOutput.getEvents().size();
            for (int i = 0; i < length; i++)
                guiItemStacks.init(i + 1, false, 116 - length * 9 + i * 18, 14);
        }

        guiItemStacks.set(ingredients);
        guiFluidStacks.set(ingredients);
    }
}
