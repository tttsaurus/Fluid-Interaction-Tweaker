package com.tttsaurus.fluidintetweaker.common.api.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import net.minecraft.client.resources.I18n;

public class IsInitiatorAbove implements IEventCondition
{
    @Override
    public boolean judge(CustomFluidInteractionEvent fluidInteractionEvent)
    {
        return fluidInteractionEvent.getIsInitiatorAbove();
    }

    @Override
    public String getDesc()
    {
        return I18n.format("fluidintetweaker.jefi.condition.is_initiator_above");
    }
}
