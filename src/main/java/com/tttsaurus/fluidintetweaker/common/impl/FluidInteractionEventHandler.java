package com.tttsaurus.fluidintetweaker.common.impl;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class FluidInteractionEventHandler
{
    @SubscribeEvent
    public static void onCustomFluidInteraction(CustomFluidInteractionEvent event)
    {
        if (!event.isCanceled()) event.getDelegate().doAction();
    }
}
