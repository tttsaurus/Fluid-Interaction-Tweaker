package com.tttsaurus.fluidintetweaker.common.impl.delegate;

import com.tttsaurus.fluidintetweaker.common.api.delegate.IDelegate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidInteractionDelegate implements IDelegate
{
    private final World world;
    private final BlockPos pos;

    public FluidInteractionDelegate(World world, BlockPos pos)
    {
        this.world = world;
        this.pos = pos;
    }

    @Override
    public void doAction() { }
}
