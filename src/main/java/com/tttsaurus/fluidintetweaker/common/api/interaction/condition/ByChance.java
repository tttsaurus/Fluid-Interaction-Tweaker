package com.tttsaurus.fluidintetweaker.common.api.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
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
        return fluidInteractionEvent.getWorld().rand.nextFloat() > probability;
    }

    @Override
    public String getDesc()
    {
        return I18n.format("fluidintetweaker.jefi.condition.by_chance").replace("p",  (int)(probability * 100) + "%");
    }
}
