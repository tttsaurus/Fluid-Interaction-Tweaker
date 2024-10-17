package com.tttsaurus.fluidintetweaker.common.api.behavior.condition;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidBehaviorEvent;

public class ByChance implements IEventCondition
{
    private final float probability;

    public ByChance(float probability)
    {
        this.probability = probability;
    }

    @Override
    public boolean judge(CustomFluidBehaviorEvent fluidBehaviorEvent)
    {
        return fluidBehaviorEvent.getWorld().rand.nextFloat() < probability;
    }
}
