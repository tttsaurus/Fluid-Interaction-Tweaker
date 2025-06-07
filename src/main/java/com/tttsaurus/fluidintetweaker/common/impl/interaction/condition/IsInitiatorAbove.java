package com.tttsaurus.fluidintetweaker.common.impl.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.core.WorldIngredientType;
import com.tttsaurus.fluidintetweaker.common.core.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.core.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.core.interaction.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.core.util.BlockUtils;
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
