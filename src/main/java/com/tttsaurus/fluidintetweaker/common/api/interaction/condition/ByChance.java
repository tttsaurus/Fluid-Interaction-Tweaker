package com.tttsaurus.fluidintetweaker.common.api.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;

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
        return fluidInteractionEvent.getWorld().rand.nextFloat() > probability;
    }
}
