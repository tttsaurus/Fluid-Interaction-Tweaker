package com.tttsaurus.fluidintetweaker.common.impl.behavior.condition;

import com.tttsaurus.fluidintetweaker.common.core.behavior.FluidBehaviorRecipe;
import com.tttsaurus.fluidintetweaker.common.core.behavior.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.core.event.CustomFluidBehaviorEvent;
import net.minecraft.client.resources.I18n;

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

    @Override
    public String getDesc(FluidBehaviorRecipe recipe)
    {
        return I18n.format("fluidintetweaker.jefb.condition.by_chance", (int)(probability * 100) + "%");
    }
}
