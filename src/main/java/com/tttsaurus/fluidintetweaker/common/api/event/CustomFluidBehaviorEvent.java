package com.tttsaurus.fluidintetweaker.common.api.event;

import com.tttsaurus.fluidintetweaker.common.api.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidBehaviorDelegate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class CustomFluidBehaviorEvent extends WorldEvent
{
    private final BlockPos pos;
    private final EntityLivingBase entityLivingBase;
    private final ComplexOutput complexOutput;
    private final FluidBehaviorDelegate delegate;

    public BlockPos getPos() { return pos; }
    public EntityLivingBase getEntityLivingBase() { return entityLivingBase; }
    public ComplexOutput getComplexOutput() { return complexOutput; }
    public FluidBehaviorDelegate getDelegate() { return delegate; }

    public CustomFluidBehaviorEvent(World world, BlockPos pos, EntityLivingBase entityLivingBase, ComplexOutput complexOutput)
    {
        super(world);
        this.pos = pos;
        this.entityLivingBase = entityLivingBase;
        this.complexOutput = complexOutput;
        this.delegate = complexOutput.getOutputDelegate(this);
    }
}
