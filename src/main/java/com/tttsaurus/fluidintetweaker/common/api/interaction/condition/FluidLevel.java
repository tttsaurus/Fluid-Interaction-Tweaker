package com.tttsaurus.fluidintetweaker.common.api.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.WorldIngredientType;
import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.FluidInteractionRecipe;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.resources.I18n;

public class FluidLevel implements IEventCondition
{
    private final int fluidIndex;
    private final int level;

    public FluidLevel(int fluidIndex, int level)
    {
        this.fluidIndex = fluidIndex;
        this.level = level;
    }

    @Override
    public boolean judge(CustomFluidInteractionEvent fluidInteractionEvent)
    {
        if (fluidIndex == 0 && fluidInteractionEvent.getIngredientA().getIngredientType() == WorldIngredientType.FLUID)
            return fluidInteractionEvent.getIngredientA().getBlockState().getValue(BlockLiquid.LEVEL) == level;
        else if (fluidIndex == 1 && fluidInteractionEvent.getIngredientB().getIngredientType() == WorldIngredientType.FLUID)
            return fluidInteractionEvent.getIngredientB().getBlockState().getValue(BlockLiquid.LEVEL) == level;

        return false;
    }

    @Override
    public String getDesc(FluidInteractionRecipe recipe)
    {
        if (fluidIndex == 0 && recipe.ingredientA.getIngredientType() == WorldIngredientType.FLUID)
            return I18n.format(
                    "fluidintetweaker.jefi.condition.fluid_level_0",
                    I18n.format(recipe.ingredientA.getFluid().getUnlocalizedName()),
                    level);
        else if (fluidIndex == 1 && recipe.ingredientB.getIngredientType() == WorldIngredientType.FLUID)
            return I18n.format(
                    "fluidintetweaker.jefi.condition.fluid_level_1",
                    I18n.format(recipe.ingredientB.getFluid().getUnlocalizedName()),
                    level);
        return null;
    }
}
