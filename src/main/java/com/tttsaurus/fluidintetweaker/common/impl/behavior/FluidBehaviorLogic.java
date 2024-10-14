package com.tttsaurus.fluidintetweaker.common.impl.behavior;

import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FluidBehaviorLogic
{
    @SubscribeEvent
    public static void onNeighborNotify(NeighborNotifyEvent event)
    {
        World world = event.getWorld();

        if (world.isRemote) return;

    }
}
