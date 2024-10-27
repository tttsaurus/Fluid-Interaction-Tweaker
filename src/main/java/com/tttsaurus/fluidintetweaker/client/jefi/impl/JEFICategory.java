package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.jefi.JustEnoughFluidInteractions;
import com.tttsaurus.fluidintetweaker.common.api.WorldIngredientType;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEventType;
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
import java.util.List;

@SuppressWarnings("all")
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
        if (recipeWrapper.recipe.ingredientA.getIngredientType() == WorldIngredientType.FLUID &&
            recipeWrapper.recipe.ingredientB.getIngredientType() == WorldIngredientType.FLUID)
        {
            guiFluidStacks.init(0, true, 7, 15, 16, 16, 1000, false, null);
            guiFluidStacks.init(1, true, 47, 15, 16, 16, 1000, false, null);

            List<InteractionEvent> events = recipeWrapper.recipe.complexOutput.getEvents();
            int length = events.size();
            int i = 0;
            int j = 0;
            for (InteractionEvent event: events)
            {
                int x = 116 - length * 9 + i++ * 18;
                if (event.getEventType() == InteractionEventType.SetFluid)
                    guiFluidStacks.init(2, false, x, 15, 16, 16, 1000, false, null);
                else
                    guiItemStacks.init(j++, false, x, 14);
            }
        }
        else if (recipeWrapper.recipe.ingredientA.getIngredientType() == WorldIngredientType.FLUID &&
                 recipeWrapper.recipe.ingredientB.getIngredientType() == WorldIngredientType.BLOCK)
        {
            guiFluidStacks.init(0, true, 7, 15, 16, 16, 1000, false, null);
            guiItemStacks.init(0, true, 47, 14);

            List<InteractionEvent> events = recipeWrapper.recipe.complexOutput.getEvents();
            int length = events.size();
            int i = 0;
            int j = 1;
            for (InteractionEvent event: events)
            {
                int x = 116 - length * 9 + i++ * 18;
                if (event.getEventType() == InteractionEventType.SetFluid)
                    guiFluidStacks.init(1, false, x, 15, 16, 16, 1000, false, null);
                else
                    guiItemStacks.init(j++, false, x, 14);
            }
        }
        else if (recipeWrapper.recipe.ingredientA.getIngredientType() == WorldIngredientType.BLOCK &&
                 recipeWrapper.recipe.ingredientB.getIngredientType() == WorldIngredientType.FLUID)
        {
            guiItemStacks.init(0, true, 7, 14);
            guiFluidStacks.init(0, true, 47, 15, 16, 16, 1000, false, null);

            List<InteractionEvent> events = recipeWrapper.recipe.complexOutput.getEvents();
            int length = events.size();
            int i = 0;
            int j = 1;
            for (InteractionEvent event: events)
            {
                int x = 116 - length * 9 + i++ * 18;
                if (event.getEventType() == InteractionEventType.SetFluid)
                    guiFluidStacks.init(1, false, x, 15, 16, 16, 1000, false, null);
                else
                    guiItemStacks.init(j++, false, x, 14);
            }
        }

        guiItemStacks.set(ingredients);
        guiFluidStacks.set(ingredients);
    }
}
