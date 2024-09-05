package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.jefi.JustEnoughFluidInteractions;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;

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
    public void setRecipe(IRecipeLayout recipeLayout, JEFIRecipeWrapper jefiWrapper, IIngredients ingredients)
    {
        List<List<ItemStack>> blockInputs = ingredients.getInputs(VanillaTypes.ITEM);

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        // inputs
        guiFluidStacks.init(0, true, 7, 15, 16, 16, 1000, false, null);
        if (blockInputs.size() == 1)
            guiItemStacks.init(0, true, 47, 14);
        else
            guiFluidStacks.init(1, true, 47, 15, 16, 16, 1000, false, null);

        // output
        guiItemStacks.init(1, false, 107, 14);

        guiItemStacks.set(ingredients);
        guiFluidStacks.set(ingredients);
    }
}