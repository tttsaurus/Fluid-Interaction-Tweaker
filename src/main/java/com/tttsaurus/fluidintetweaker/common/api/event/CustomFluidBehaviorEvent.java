package com.tttsaurus.fluidintetweaker.common.api.event;

import com.tttsaurus.fluidintetweaker.common.api.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidBehaviorDelegate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class CustomFluidBehaviorEvent extends WorldEvent
{
    private final BlockPos pos;
    private final ComplexOutput complexOutput;
    private final FluidBehaviorDelegate delegate;

    public ComplexOutput getComplexOutput() { return complexOutput; }
    public FluidBehaviorDelegate getDelegate() { return delegate; }

    public CustomFluidBehaviorEvent(World world, BlockPos pos, ComplexOutput complexOutput)
    {
        super(world);
        this.pos = pos;
        this.complexOutput = complexOutput;
        this.delegate = complexOutput.getOutputDelegate(this);
    }
}
