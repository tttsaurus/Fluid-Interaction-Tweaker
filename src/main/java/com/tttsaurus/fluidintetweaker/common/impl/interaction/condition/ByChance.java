package com.tttsaurus.fluidintetweaker.common.impl.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import net.minecraft.client.resources.I18n;

public class ByChance implements IEventCondition
{
    private final float probability;

    public ByChance(float probability)
    {
        this.probability = probability;
    }

    @Override
    public boolean judge(CustomFluidInteractionEvent fluidInteractionEvent)
    {
        return fluidInteractionEvent.getWorld().rand.nextFloat() < probability;
    }

    @Override
    public String getDesc(FluidInteractionRecipe recipe)
    {
        return I18n.format("fluidintetweaker.jefi.condition.by_chance", (int)(probability * 100) + "%");
    }
}
