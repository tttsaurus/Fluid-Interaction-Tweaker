package com.tttsaurus.fluidintetweaker.common.api.event;

import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidBehaviorDelegate;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class CustomFluidBehaviorEvent extends WorldEvent
{
    private final FluidBehaviorDelegate delegate;

    public FluidBehaviorDelegate getDelegate() { return delegate; }

    public CustomFluidBehaviorEvent(World world)
    {
        super(world);
        delegate = null;
    }
}
