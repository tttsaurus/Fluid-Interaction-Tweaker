package com.tttsaurus.fluidintetweaker.common.api;

import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidInteractionDelegate;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import javax.annotation.Nullable;

public class ComplexOutput
{
    private final OutputMode outputMode;
    private final Block legacyOutputBlock;

    @Nullable
    public Block getLegacyOutputBlock()
    {
        if (outputMode == OutputMode.LegacyOneBlockMode)
            return legacyOutputBlock;
        else
            return null;
    }

    public ComplexOutput(Block legacyOutputBlock)
    {
        outputMode = OutputMode.LegacyOneBlockMode;
        this.legacyOutputBlock = legacyOutputBlock;
    }

    public FluidInteractionDelegate getOutputDelegate(World world, BlockPos pos)
    {
        if (outputMode == OutputMode.LegacyOneBlockMode)
        {
            return new FluidInteractionDelegate(world, pos)
            {
                @Override
                public void doAction()
                {
                    world.setBlockState(pos, legacyOutputBlock.getDefaultState());
                }
            };
        }
        else
            return null;
    }
}
