package com.tttsaurus.fluidintetweaker.common.impl.task;

import com.tttsaurus.fluidintetweaker.common.core.task.BaseTask;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SetBlockStateTask extends BaseTask
{
    private final World world;
    private final BlockPos pos;
    private IBlockState blockState;
    private int flags;

    public SetBlockStateTask(int delay, World world, BlockPos pos, IBlockState blockState, int flags)
    {
        super(delay);
        this.world = world;
        this.pos = pos;
        this.blockState = blockState;
        this.flags = flags;
    }
    @Override
    public void run()
    {
        world.setBlockState(pos, blockState, flags);
    }
    @Override
    public boolean compare(BaseTask task)
    {
        return task instanceof SetBlockStateTask t && pos.equals(t.pos);
    }
}
