package com.tttsaurus.fluidintetweaker.client.impl.jefb;

import com.tttsaurus.fluidintetweaker.common.api.behavior.BehaviorEvent;
import com.tttsaurus.fluidintetweaker.common.api.behavior.BehaviorEventType;
import com.tttsaurus.fluidintetweaker.common.api.behavior.FluidBehaviorRecipe;
import com.tttsaurus.fluidintetweaker.common.api.behavior.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.api.util.BlockUtils;
import com.tttsaurus.fluidintetweaker.common.api.util.RomanNumberUtils;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("all")
@SideOnly(Side.CLIENT)
public class JEFBRecipeWrapper implements IRecipeWrapper
{
    protected FluidBehaviorRecipe recipe;
    protected boolean isAnyFluidState = false;

    public JEFBRecipeWrapper(FluidBehaviorRecipe recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ingredients.setInput(VanillaTypes.FLUID, new FluidStack(recipe.ingredient.getFluid(), 1000));
        ingredients.setOutput(VanillaTypes.FLUID, new FluidStack(recipe.ingredient.getFluid(), 1000));

        List<List<ItemStack>> itemOutputs = new ArrayList<>();
        for (BehaviorEvent event: recipe.complexOutput.getEvents())
        {
            BehaviorEventType eventType = event.getEventType();
            if (eventType == BehaviorEventType.PotionEffect)
            {
                int amplifier = event.getPotionEffect().getAmplifier() + 1;
                float duration = ((float)event.getPotionEffect().getDuration()) / 20f;

                // convert to roman notation
                String amplifierNotation = RomanNumberUtils.toRomanNotation(amplifier);

                ItemStack itemStack = new ItemStack(Items.POTIONITEM);
                itemStack.setStackDisplayName(I18n.format("fluidintetweaker.jefb.behavior.potion_effect") + " " +
                        TextFormatting.DARK_AQUA +
                        I18n.format(event.getPotionEffect().getPotion().getName()) +
                        " (" + amplifierNotation + ") " + TextFormatting.RESET +
                        duration + "s");
                itemOutputs.add(Collections.singletonList(itemStack));
            }
            else if (eventType == BehaviorEventType.EntityConversion)
            {
                String i18nKey1 = "entity." + EntityList.getTranslationName(event.getEntityEntryFrom().getRegistryName()) + ".name";
                String i18nKey2 = "entity." + EntityList.getTranslationName(event.getEntityEntryTo().getRegistryName()) + ".name";

                ItemStack egg1 = new ItemStack(Items.SPAWN_EGG);
                NBTTagCompound nbt1 = new NBTTagCompound();
                nbt1.setString("id", event.getEntityEntryFrom().getRegistryName().toString());
                NBTTagCompound nbt2 = new NBTTagCompound();
                nbt2.setTag("EntityTag", nbt1);
                egg1.setTagCompound(nbt2);
                egg1.setStackDisplayName(I18n.format("fluidintetweaker.jefb.behavior.entity_conversion_0") + " " + TextFormatting.DARK_AQUA + I18n.format(i18nKey1));

                ItemStack egg2 = new ItemStack(Items.SPAWN_EGG);
                nbt1 = new NBTTagCompound();
                nbt1.setString("id", event.getEntityEntryTo().getRegistryName().toString());
                nbt2 = new NBTTagCompound();
                nbt2.setTag("EntityTag", nbt1);
                egg2.setTagCompound(nbt2);
                egg2.setStackDisplayName(I18n.format("fluidintetweaker.jefb.behavior.entity_conversion_1") + " " + TextFormatting.DARK_AQUA + I18n.format(i18nKey2));

                itemOutputs.add(Collections.singletonList(egg1));
                itemOutputs.add(Collections.singletonList(egg2));
            }
            else if (eventType == BehaviorEventType.ExtinguishFire)
            {
                ItemStack itemStack = new ItemStack(Items.SNOWBALL);
                itemStack.setStackDisplayName(I18n.format("fluidintetweaker.jefb.behavior.extinguish_fire"));
                itemOutputs.add(Collections.singletonList(itemStack));
            }
            else if (eventType == BehaviorEventType.SetFire)
            {
                ItemStack itemStack = new ItemStack(Items.FLINT_AND_STEEL);
                itemStack.setStackDisplayName(I18n.format("fluidintetweaker.jefb.behavior.set_fire"));
                itemOutputs.add(Collections.singletonList(itemStack));
            }
            else if (eventType == BehaviorEventType.SetSnow)
            {
                ItemStack itemStack = BlockUtils.getItemStack(Blocks.SNOW_LAYER.getDefaultState());
                itemStack.setStackDisplayName(I18n.format("fluidintetweaker.jefb.behavior.set_snow"));
                itemOutputs.add(Collections.singletonList(itemStack));
            }
            else if (eventType == BehaviorEventType.Teleport)
            {
                ItemStack itemStack = new ItemStack(Items.ENDER_PEARL);
                itemStack.setStackDisplayName(I18n.format("fluidintetweaker.jefb.behavior.teleport"));
                itemOutputs.add(Collections.singletonList(itemStack));
            }
            else if (eventType == BehaviorEventType.BreakSurrounding)
            {
                List<ItemStack> itemStacks = new ArrayList<>();
                for (IBlockState blockState: event.getBlockStates())
                {
                    ItemStack itemStack = BlockUtils.getItemStack(blockState);
                    itemStack.setStackDisplayName(I18n.format("fluidintetweaker.jefb.behavior.break") + " " + TextFormatting.DARK_AQUA + itemStack.getDisplayName());
                    itemStacks.add(itemStack);
                }
                itemOutputs.add(itemStacks);
            }
        }
        ingredients.setOutputLists(VanillaTypes.ITEM, itemOutputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        if (!isAnyFluidState)
            minecraft.fontRenderer.drawString(recipe.ingredient.getIsFluidSource() ?
                    I18n.format("fluidintetweaker.fluid_source") :
                    I18n.format("fluidintetweaker.fluid_flowing"), 7, 33, Color.GRAY.getRGB());

        int length = recipe.complexOutput.getEvents().size();
        if (mouseX >= (98 - length * 9) && mouseX <= (98 + length * 9))
        {
            List<String> tooltip = new ArrayList<>();

            int hoverIndex = (mouseX - 98 + length * 9) / 18;
            hoverIndex = hoverIndex >= length ? length - 1 : hoverIndex;
            BehaviorEvent behaviorEvent = recipe.complexOutput.getEvents().get(hoverIndex);

            if (behaviorEvent.getEventType() == BehaviorEventType.EntityConversion && (mouseY < 14 - 9 || mouseY > 31 + 9))
                return;
            if (behaviorEvent.getEventType() != BehaviorEventType.EntityConversion && (mouseY < 14 || mouseY > 31))
                return;

            //<editor-fold desc="tooltip">
            for (IEventCondition condition: behaviorEvent.getConditions())
            {
                String desc = condition.getDesc(recipe);
                if (desc != null)
                {
                    String[] lines = desc.split("<br>");
                    tooltip.addAll(Arrays.asList(lines));
                }
            }
            if (!tooltip.isEmpty())
                tooltip.add(0, I18n.format("fluidintetweaker.condition_tips"));
            //</editor-fold>

            if (!tooltip.isEmpty())
                OnTooltipEventHandler.addExtraTooltip(tooltip);
        }
    }
}
