package com.tttsaurus.fluidintetweaker.client.jefb.impl;

import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.jefb.JustEnoughFluidBehavior;
import com.tttsaurus.fluidintetweaker.common.api.behavior.BehaviorEvent;
import com.tttsaurus.fluidintetweaker.common.api.behavior.BehaviorEventType;
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
public class JEFBCategory implements IRecipeCategory<JEFBRecipeWrapper>
{
    public static final ResourceLocation BACKGROUND = new ResourceLocation(FluidInteractionTweaker.MODID, "textures/gui/jefb/background.png");
    public static final ResourceLocation ICON = new ResourceLocation(FluidInteractionTweaker.MODID, "textures/gui/jefb/icon.png");

    private final IDrawable backgroud, icon;

    public JEFBCategory(IGuiHelper guiHelper)
    {
        backgroud = guiHelper.drawableBuilder(BACKGROUND, 0, 0, 140, 32).addPadding(0, 12, 0, 0).build();
        icon = guiHelper.drawableBuilder(ICON, 0, 0, 16, 16).setTextureSize(16, 16).build();
    }

    @Override
    public String getUid()
    {
        return JustEnoughFluidBehavior.UID;
    }

    @Override
    public String getTitle()
    {
        return I18n.format(JustEnoughFluidBehavior.CATEGORY);
    }

    @Override
    public String getModName()
    {
        return JustEnoughFluidBehavior.NAME;
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
    public void setRecipe(IRecipeLayout recipeLayout, JEFBRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiFluidStacks.init(0, true, 7, 15, 16, 16, 1000, false, null);
        guiFluidStacks.init(1, false, 7, 15, 16, 16, 1000, false, null);

        List<BehaviorEvent> events = recipeWrapper.recipe.complexOutput.getEvents();
        int length = events.size();
        int i = 0;
        int j = 0;
        for (BehaviorEvent event: events)
        {
            int x = 98 - length * 9 + i++ * 18;
            if (event.getEventType() == BehaviorEventType.EntityConversion)
            {
                guiItemStacks.init(j++, false, x, 14 - 9);
                guiItemStacks.init(j++, false, x, 14 + 9);
            }
            else
                guiItemStacks.init(j++, false, x, 14);
        }

        guiItemStacks.set(ingredients);
        guiFluidStacks.set(ingredients);
    }
}
