package com.tttsaurus.fluidintetweaker.common.impl.behavior;

import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidBehaviorEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class FluidBehaviorLogic
{
    @SubscribeEvent
    public static void onNeighborNotify(NeighborNotifyEvent event)
    {
        World world = event.getWorld();

        if (world.isRemote) return;

        BlockPos pos = event.getPos();
        WorldIngredient ingredient = WorldIngredient.getFrom(world, pos);

        // early escape
        if (!FluidBehaviorRecipeManager.recipeExists(ingredient)) return;

        ComplexOutput complexOutput = FluidBehaviorRecipeManager.getRecipeOutput(ingredient);
        MinecraftForge.EVENT_BUS.post(new CustomFluidBehaviorEvent(world, event.getPos(), complexOutput));
    }
}
