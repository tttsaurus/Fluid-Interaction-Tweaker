package com.tttsaurus.fluidintetweaker.plugin.crt.impl.event;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidBehaviorEvent;
import com.tttsaurus.fluidintetweaker.plugin.crt.api.event.ICustomFluidBehaviorEvent;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.entity.MCEntityLivingBase;
import crafttweaker.mc1120.world.MCBlockPos;
import crafttweaker.mc1120.world.MCWorld;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MCCustomFluidBehaviorEvent implements ICustomFluidBehaviorEvent
{
    private final CustomFluidBehaviorEvent event;

    public MCCustomFluidBehaviorEvent(CustomFluidBehaviorEvent event)
    {
        this.event = event;
    }

    @Override
    public MCEntityLivingBase getEntityLivingBase()
    {
        EntityLivingBase entityLivingBase = event.getEntityLivingBase();
        return entityLivingBase == null ? null : new MCEntityLivingBase(entityLivingBase);
    }

    @Override
    public String getFluidBehaviorRecipeKey()
    {
        return event.getFluidBehaviorRecipeKey();
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
    public void setCanceled(boolean canceled)
    {
        event.setCanceled(canceled);
    }

    @Override
    public IBlockPos getPosition()
    {
        BlockPos pos = event.getPos();
        return pos == null ? null : new MCBlockPos(pos);
    }
}
