package com.tttsaurus.fluidintetweaker.common.impl.behavior;

import com.tttsaurus.fluidintetweaker.common.core.event.CustomFluidBehaviorEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class FluidBehaviorEventHandler
{
    @SubscribeEvent
    public static void onCustomFluidBehavior(CustomFluidBehaviorEvent event)
    {
        if (!event.isCanceled()) event.getDelegate().doAction();
    }
}
