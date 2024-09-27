package com.tttsaurus.fluidintetweaker.common.api.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;

public interface IEventCondition
{
    boolean judge(CustomFluidInteractionEvent fluidInteractionEvent);
}
