package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.client.api.render.EntityRenderer;
import com.tttsaurus.fluidintetweaker.common.api.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.api.util.BlockUtils;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEventType;
import com.tttsaurus.fluidintetweaker.common.api.WorldIngredientType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
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
    protected FluidInteractionRecipe recipe;
    protected boolean isAnyFluidStateA = false;
    protected boolean isAnyFluidStateB = false;

    public JEFIRecipeWrapper(FluidInteractionRecipe recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        if (recipe.ingredientA.getIngredientType() == WorldIngredientType.FLUID &&
            recipe.ingredientB.getIngredientType() == WorldIngredientType.FLUID)
        {
            ingredients.setInputs(VanillaTypes.FLUID, Arrays.asList(
                    new FluidStack(recipe.ingredientA.getFluid(), 1000),
                    new FluidStack(recipe.ingredientB.getFluid(), 1000)));
        }
        else if (recipe.ingredientA.getIngredientType() == WorldIngredientType.FLUID &&
                 recipe.ingredientB.getIngredientType() == WorldIngredientType.BLOCK)
        {
            ingredients.setInput(VanillaTypes.FLUID, new FluidStack(recipe.ingredientA.getFluid(), 1000));
            ingredients.setInput(VanillaTypes.ITEM, BlockUtils.getItemStack(recipe.ingredientB.getBlockState()));
        }
        else if (recipe.ingredientA.getIngredientType() == WorldIngredientType.BLOCK &&
                 recipe.ingredientB.getIngredientType() == WorldIngredientType.FLUID)
        {
            ingredients.setInput(VanillaTypes.ITEM, BlockUtils.getItemStack(recipe.ingredientA.getBlockState()));
            ingredients.setInput(VanillaTypes.FLUID, new FluidStack(recipe.ingredientB.getFluid(), 1000));
        }

        List<ItemStack> outputs = new ArrayList<>();
        for (InteractionEvent event: recipe.complexOutput.getEvents())
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
                ItemStack itemStack = event.getItemStack();
                String i18nText = I18n.format("fluidintetweaker.jefi.interaction.spawn_entity_item");
                if (!itemStack.getDisplayName().contains(i18nText)) itemStack.setStackDisplayName(i18nText + " " + itemStack.getDisplayName());
                outputs.add(itemStack);
            }
        }
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    private EntityRenderer entityRenderer = null;
    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        if (recipe.ingredientA.getIngredientType() == WorldIngredientType.FLUID)
            if (!isAnyFluidStateA)
                minecraft.fontRenderer.drawString(recipe.ingredientA.getIsFluidSource() ?
                        I18n.format("fluidintetweaker.jefi.fluid_source") :
                        I18n.format("fluidintetweaker.jefi.fluid_flowing"), 7, 35, Color.GRAY.getRGB());

        if (recipe.ingredientB.getIngredientType() == WorldIngredientType.FLUID)
            if (!isAnyFluidStateB)
                minecraft.fontRenderer.drawString(recipe.ingredientB.getIsFluidSource() ?
                        I18n.format("fluidintetweaker.jefi.fluid_source") :
                        I18n.format("fluidintetweaker.jefi.fluid_flowing"), 47, 35, Color.GRAY.getRGB());

        int length = recipe.complexOutput.getEvents().size();
        if (mouseX >= (116 - length * 9) && mouseX <= (116 + length * 9) && mouseY >= 14 && mouseY <= 31)
        {
            List<String> tooltip = new ArrayList<>();

            int hoverIndex = (mouseX - 116 + length * 9) / 18;
            hoverIndex = hoverIndex >= length ? length - 1 : hoverIndex;
            InteractionEvent interactionEvent = recipe.complexOutput.getEvents().get(hoverIndex);

            //<editor-fold desc="entity rendering">
            if (interactionEvent.getEventType() == InteractionEventType.SpawnEntity)
            {
                float x = 116 - length * 9 + hoverIndex * 18 + 9;
                float y = 14 - 1;
                if (entityRenderer == null) entityRenderer = new EntityRenderer(minecraft, interactionEvent.getEntityEntry());
                // entity is behind the screen
                // dist describes how far the entity is
                float dist = 9f;
                double angle = Math.atan((mouseX - x)/dist) / (2f * 3.14159f) * 360f;
                entityRenderer.render(x, y, 0, (float)angle, 0);
            }
            //</editor-fold>

            //<editor-fold desc="tooltip">
            for (IEventCondition condition: interactionEvent.getConditions())
            {
                String desc = condition.getDesc(recipe);
                if (desc != null)
                {
                    String[] lines = desc.split("<br>");
                    tooltip.addAll(Arrays.asList(lines));
                }
            }
            if (!tooltip.isEmpty())
                tooltip.add(0, I18n.format("fluidintetweaker.jefi.condition_tips"));
            if (!(recipe.extraInfoLocalizationKey == null || recipe.extraInfoLocalizationKey.isEmpty()))
            {
                String text = I18n.format(recipe.extraInfoLocalizationKey);
                String[] lines = text.split("<br>");
                tooltip.addAll(Arrays.asList(lines));
            }
            //</editor-fold>

            if (!tooltip.isEmpty())
                OnTooltipEventHandler.addExtraTooltip(tooltip);
        }
    }
}
