package com.tttsaurus.fluidintetweaker.plugin.crt.impl.event;

import com.tttsaurus.fluidintetweaker.common.core.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.plugin.crt.api.event.ICustomFluidInteractionEvent;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.block.MCBlockState;
import crafttweaker.mc1120.world.MCBlockPos;
import crafttweaker.mc1120.world.MCWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MCCustomFluidInteractionEvent implements ICustomFluidInteractionEvent
{
    private final CustomFluidInteractionEvent event;

    public MCCustomFluidInteractionEvent(CustomFluidInteractionEvent event)
    {
        this.event = event;
    }

    @Override
    public boolean getIsFluidAboveAndBelowCase()
    {
        return event.getIsFluidAboveAndBelowCase();
    }

    @Override
    public boolean getIsInitiatorAbove()
    {
        return event.getIsInitiatorAbove();
    }

    @Override
    public IBlockState getBlockStateBeforeInteraction()
    {
        net.minecraft.block.state.IBlockState state = event.getBlockStateBeforeInteraction();
        return state == null ? null : new MCBlockState(state);
    }

    @Override
    public String getFluidInteractionRecipeKey()
    {
        return event.getFluidInteractionRecipeKey();
    }

    @Override
    public IWorld getWorld()
    {
        World world = event.getWorld();
        return world == null ? null : new MCWorld(world);
    }

    @Override
    public boolean isCanceled()
    {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean cancel)
    {
        event.setCanceled(cancel);
    }

    @Override
    public IBlockPos getPosition()
    {
        BlockPos pos = event.getPos();
        return pos == null ? null : new MCBlockPos(pos);
    }
}
