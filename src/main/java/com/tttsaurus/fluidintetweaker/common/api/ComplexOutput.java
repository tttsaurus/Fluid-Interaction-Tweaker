package com.tttsaurus.fluidintetweaker.common.api;

import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidInteractionDelegate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import javax.annotation.Nullable;

public class ComplexOutput
{
    private final OutputMode outputMode;
    private final IBlockState simpleBlockOutput;

    @Nullable
    public IBlockState getSimpleBlockOutput()
    {
        if (outputMode == OutputMode.SimpleBlock)
            return simpleBlockOutput;
        else
            return null;
    }

    public ComplexOutput(IBlockState simpleBlockOutput)
    {
        outputMode = OutputMode.SimpleBlock;
        this.simpleBlockOutput = simpleBlockOutput;
    }

    public FluidInteractionDelegate getOutputDelegate(World world, BlockPos pos)
    {
        if (outputMode == OutputMode.SimpleBlock)
        {
            return new FluidInteractionDelegate(world, pos)
            {
                @Override
                public void doAction()
                {
                    world.setBlockState(pos, simpleBlockOutput);
                }
            };
        }
        else
            return null;
    }
}
