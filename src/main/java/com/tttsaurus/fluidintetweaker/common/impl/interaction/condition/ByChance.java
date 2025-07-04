package com.tttsaurus.fluidintetweaker.common.impl.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.core.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.core.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.core.interaction.condition.IEventCondition;
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
        String p;
        if (probability < 0.01f)
            p = String.format("%.2f", probability * 100f) + "%";
        else
            p = (int)(probability * 100) + "%";

        return I18n.format("fluidintetweaker.jefi.condition.by_chance", p);
    }
}
