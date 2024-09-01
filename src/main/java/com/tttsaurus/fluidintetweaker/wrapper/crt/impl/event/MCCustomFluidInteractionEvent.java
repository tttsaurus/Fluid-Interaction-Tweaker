package com.tttsaurus.fluidintetweaker.wrapper.crt.impl.event;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.wrapper.crt.api.event.ICustomFluidInteractionEvent;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.block.MCBlockState;
import crafttweaker.mc1120.liquid.MCLiquidDefinition;
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
    public ILiquidDefinition getFluidBeforeInteraction()
    {
        return new MCLiquidDefinition(event.getFluidBeforeInteraction());
    }

    @Override
    public IWorld getWorld()
    {
        World world = event.getWorld();
        return world == null ? null : new MCWorld(world);
    }

    @Override
    public IBlockState getBlockState()
    {
        net.minecraft.block.state.IBlockState state = event.getState();
        return state == null ? null : new MCBlockState(state);
    }

    @Override
    public boolean isCanceled()
    {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean isCanceled)
    {
        event.setCanceled(isCanceled);
    }

    @Override
    public IBlockPos getPosition()
    {
        BlockPos pos = event.getPos();
        return pos == null ? null : new MCBlockPos(pos);
    }
}
