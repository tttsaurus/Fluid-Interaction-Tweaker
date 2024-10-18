package com.tttsaurus.fluidintetweaker.common.api.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.WorldIngredientType;
import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.api.util.BlockUtils;
import net.minecraft.client.resources.I18n;

public class IsInitiatorAbove implements IEventCondition
{
    @Override
    public boolean judge(CustomFluidInteractionEvent fluidInteractionEvent)
    {
        return fluidInteractionEvent.getIsInitiatorAbove();
    }

    @Override
    public String getDesc(FluidInteractionRecipe recipe)
    {
        if (recipe.ingredientA.getIngredientType() == WorldIngredientType.BLOCK)
            return I18n.format(
                    "fluidintetweaker.jefi.condition.is_initiator_above",
                    I18n.format(BlockUtils.getItemStack(recipe.ingredientA.getBlockState()).getDisplayName()));
        else if (recipe.ingredientA.getIngredientType() == WorldIngredientType.FLUID)
            return I18n.format(
                    "fluidintetweaker.jefi.condition.is_initiator_above",
                    I18n.format(recipe.ingredientB.getFluid().getUnlocalizedName()));
        return null;
    }
}
