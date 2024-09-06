package com.tttsaurus.fluidintetweaker.common.impl.delegate;

import com.tttsaurus.fluidintetweaker.common.api.delegate.IDelegate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidInteractionDelegate implements IDelegate
{
    private final World world;
    private final BlockPos pos;
    private final IBlockState blockState;

    public FluidInteractionDelegate(World world, BlockPos pos, IBlockState blockState)
    {
        this.world = world;
        this.pos = pos;
        this.blockState = blockState;
    }

    @Override
    public void doAction()
    {
        world.setBlockState(pos, blockState);
    }
}
