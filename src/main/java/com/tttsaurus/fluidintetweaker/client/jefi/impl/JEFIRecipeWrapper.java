package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.client.jefi.impl.delegate.RenderExtraTooltipDelegate;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.api.util.BlockUtils;
import com.tttsaurus.fluidintetweaker.common.api.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEventType;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionIngredientType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<ItemStack> outputs = new ArrayList<>();
        for (InteractionEvent event: complexOutput.getEvents())
        {
            InteractionEventType eventType = event.getEventType();
            if (eventType == InteractionEventType.SetBlock)
            {
                outputs.add(BlockUtils.getItemStack(event.getBlockState()));
            }
            else if (eventType == InteractionEventType.Explosion)
            {
                ItemStack tnt = new ItemStack(Items.TNT_MINECART);
                tnt.setStackDisplayName(I18n.format("fluidintetweaker.jefi.interaction.explosion"));
                outputs.add(tnt);
            }
            else if (eventType == InteractionEventType.SpawnEntity)
            {
                ItemStack egg = new ItemStack(Items.SPAWN_EGG);
                NBTTagCompound nbt1 = new NBTTagCompound();
                nbt1.setString("id", event.getEntityEntry().getEgg().spawnedID.toString());
                NBTTagCompound nbt2 = new NBTTagCompound();
                nbt2.setTag("EntityTag", nbt1);
                egg.setTagCompound(nbt2);
                egg.setStackDisplayName(I18n.format("fluidintetweaker.jefi.interaction.spawn_entity") + " " + egg.getDisplayName());
                outputs.add(egg);
            }
            else if (eventType == InteractionEventType.SpawnEntityItem)
            {
                ItemStack itemStack = new ItemStack(event.getItem(), event.getItemAmount(), event.getItemMeta());
                itemStack.setStackDisplayName(I18n.format("fluidintetweaker.jefi.interaction.spawn_entity_item") + " " + itemStack.getDisplayName());
                outputs.add(itemStack);
            }
        }
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
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

        int length = complexOutput.getEvents().size();
        if (mouseX >= (116 - length * 9) && mouseX <= (116 + length * 9) && mouseY >= 14 && mouseY <= 31)
        {
            FontRenderer fr = minecraft.fontRenderer;
            List<String> tooltip = new ArrayList<>();

            int hoverIndex = (mouseX - 116 + length * 9) / 18;
            hoverIndex = hoverIndex >= length ? length - 1 : hoverIndex;
            InteractionEvent interactionEvent = complexOutput.getEvents().get(hoverIndex);
            int width = 0;
            int height = 0;

            for (IEventCondition condition: interactionEvent.getConditions())
            {
                String desc = condition.getDesc();
                if (desc != null)
                {
                    String[] lines = desc.split("<br>");
                    tooltip.addAll(Arrays.asList(lines));
                }
            }
            if (!tooltip.isEmpty())
                tooltip.add(0, I18n.format("fluidintetweaker.jefi.condition_tips"));
            if (extraInfoLocalizationKey != null)
            {
                String text = I18n.format(extraInfoLocalizationKey);
                String[] lines = text.split("<br>");
                tooltip.addAll(Arrays.asList(lines));
            }

            for (String line : tooltip)
            {
                int lineWidth = fr.getStringWidth(line);
                if (lineWidth > width) width = lineWidth;
                height += 9;
            }

            if (!tooltip.isEmpty())
            {
                RenderTooltipEventHandler.setRenderExtraTooltip(new RenderExtraTooltipDelegate(
                        hoverIndex,
                        interactionEvent,
                        width,
                        height,
                        tooltip));
            }
        }
    }
}
