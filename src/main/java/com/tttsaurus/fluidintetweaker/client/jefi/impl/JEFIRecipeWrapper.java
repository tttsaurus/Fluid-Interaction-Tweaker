package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.client.jefi.impl.delegate.RenderExtraTooltipDelegate;
import com.tttsaurus.fluidintetweaker.common.api.BlockUtils;
import com.tttsaurus.fluidintetweaker.common.api.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredientType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.awt.*;
import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class JEFIRecipeWrapper implements IRecipeWrapper
{
    protected InteractionIngredient ingredientA;
    protected boolean isAnyFluidStateA = false;
    protected InteractionIngredient ingredientB;
    protected boolean isAnyFluidStateB = false;
    protected ComplexOutput complexOutput;

    private String extraInfoLocalizationKey = null;

    public JEFIRecipeWrapper(InteractionIngredient ingredientA, InteractionIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.complexOutput = complexOutput;
        if (!(extraInfoLocalizationKey == null || extraInfoLocalizationKey.isEmpty()))
            this.extraInfoLocalizationKey = extraInfoLocalizationKey;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        if (ingredientA.getIngredientType() == InteractionIngredientType.FLUID &&
            ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
        {
            ingredients.setInputs(VanillaTypes.FLUID, Arrays.asList(
                    new FluidStack(ingredientA.getFluid(), 1000),
                    new FluidStack(ingredientB.getFluid(), 1000)));
        }
        else if (ingredientA.getIngredientType() == InteractionIngredientType.FLUID &&
                 ingredientB.getIngredientType() == InteractionIngredientType.BLOCK)
        {
            ingredients.setInput(VanillaTypes.FLUID, new FluidStack(ingredientA.getFluid(), 1000));
            ingredients.setInput(VanillaTypes.ITEM, BlockUtils.getItemStack(ingredientB.getBlockState()));
        }
        else if (ingredientA.getIngredientType() == InteractionIngredientType.BLOCK &&
                 ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
        {
            ingredients.setInput(VanillaTypes.ITEM, BlockUtils.getItemStack(ingredientA.getBlockState()));
            ingredients.setInput(VanillaTypes.FLUID, new FluidStack(ingredientB.getFluid(), 1000));
        }

        ingredients.setOutput(VanillaTypes.ITEM, BlockUtils.getItemStack(complexOutput.getSimpleBlockOutput()));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        if (ingredientA.getIngredientType() == InteractionIngredientType.FLUID)
            if (!isAnyFluidStateA)
                minecraft.fontRenderer.drawString(ingredientA.getIsFluidSource() ?
                        I18n.format("fluidintetweaker.jefi.fluid_source") :
                        I18n.format("fluidintetweaker.jefi.fluid_flowing"), 7, 35, Color.GRAY.getRGB());

        if (ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
            if (!isAnyFluidStateB)
                minecraft.fontRenderer.drawString(ingredientB.getIsFluidSource() ?
                        I18n.format("fluidintetweaker.jefi.fluid_source") :
                        I18n.format("fluidintetweaker.jefi.fluid_flowing"), 47, 35, Color.GRAY.getRGB());

        if (extraInfoLocalizationKey != null)
            if (mouseX >= 107 && mouseX <= 124 && mouseY >= 14 && mouseY <= 31)
            {
                FontRenderer fr = minecraft.fontRenderer;
                String text = I18n.format(extraInfoLocalizationKey);
                String[] lines = text.split("\\\\n");

                int width = 0;
                int height = 0;

                for (String line: lines)
                {
                    int lineWidth = fr.getStringWidth(line);
                    if (lineWidth > width) width = lineWidth;
                    height += 9;
                }

                RenderTooltipEventHandler.setRenderExtraTooltip(new RenderExtraTooltipDelegate(BlockUtils.getItemStack(complexOutput.getSimpleBlockOutput()), width, height, Arrays.asList(lines)));
            }
    }
}
