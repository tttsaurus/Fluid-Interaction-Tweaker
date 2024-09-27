package com.tttsaurus.fluidintetweaker.common.api.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;

public class IsInitiatorAbove implements IEventCondition
{
    @Override
    public boolean judge(CustomFluidInteractionEvent fluidInteractionEvent)
    {
        return fluidInteractionEvent.getIsInitiatorAbove();
    }
}
