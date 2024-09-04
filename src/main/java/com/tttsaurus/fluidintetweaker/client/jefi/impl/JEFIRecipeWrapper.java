package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredientType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.awt.*;
import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class JEFIRecipeWrapper implements IRecipeWrapper
{
    public InteractionIngredient ingredientA;
    public boolean isAnyFluidStateA = false;
    public InteractionIngredient ingredientB;
    public boolean isAnyFluidStateB = false;
    public Block outputBlock;

    public JEFIRecipeWrapper(InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock)
    {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.outputBlock = outputBlock;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        if (ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
        {
            ingredients.setInputs(VanillaTypes.FLUID, Arrays.asList(
                    new FluidStack(ingredientA.getFluid(), 1000),
                    new FluidStack(ingredientB.getFluid(), 1000)));
        }
        else if (ingredientB.getIngredientType() == InteractionIngredientType.BLOCK)
        {
            ingredients.setInput(VanillaTypes.FLUID, new FluidStack(ingredientA.getFluid(), 1000));
            ingredients.setInput(VanillaTypes.ITEM, new ItemStack(ingredientB.getBlock()));
        }

        ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(outputBlock));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        if (!isAnyFluidStateA)
            minecraft.fontRenderer.drawString(ingredientA.getIsFluidSource() ?
                    I18n.format("fluidintetweaker.jefi.fluid_source") :
                    I18n.format("fluidintetweaker.jefi.fluid_flowing"), 7, 35, Color.GRAY.getRGB());

        if (ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
            if (!isAnyFluidStateB)
                minecraft.fontRenderer.drawString(ingredientB.getIsFluidSource() ?
                        I18n.format("fluidintetweaker.jefi.fluid_source") :
                        I18n.format("fluidintetweaker.jefi.fluid_flowing"), 47, 35, Color.GRAY.getRGB());
    }
}
