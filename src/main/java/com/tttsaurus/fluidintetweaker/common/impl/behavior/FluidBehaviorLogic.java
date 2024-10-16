package com.tttsaurus.fluidintetweaker.common.impl.behavior;

import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidBehaviorEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.EnumSet;

public final class FluidBehaviorLogic
{
    private static final EnumSet<EnumFacing> surrounding = EnumSet.of(EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.UP, EnumFacing.DOWN);
    @SubscribeEvent
    public static void onNeighborNotify(NeighborNotifyEvent event)
    {
        World world = event.getWorld();

        if (world.isRemote) return;

        BlockPos pos = event.getPos();
        WorldIngredient ingredient = WorldIngredient.getFrom(world, pos);
        if (FluidBehaviorRecipeManager.recipeExists(ingredient))
        {
            ComplexOutput complexOutput = FluidBehaviorRecipeManager.getRecipeOutput(ingredient);
            MinecraftForge.EVENT_BUS.post(new CustomFluidBehaviorEvent(
                    world,
                    pos,
                    null,
                    complexOutput));
        }

        // backward notifying
        for (EnumFacing facing : surrounding)
        {
            Vec3i vec3 = facing.getDirectionVec();
            int x = vec3.getX();
            int y = vec3.getY();
            int z = vec3.getZ();

            BlockPos neighborPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
            WorldIngredient neighborIngredient = WorldIngredient.getFrom(world, neighborPos);

            if (!FluidBehaviorRecipeManager.recipeExists(neighborIngredient)) continue;
            ComplexOutput complexOutput = FluidBehaviorRecipeManager.getRecipeOutput(neighborIngredient);
            MinecraftForge.EVENT_BUS.post(new CustomFluidBehaviorEvent(
                    world,
                    neighborPos,
                    null,
                    complexOutput));
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingUpdateEvent event)
    {
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        World world = entityLivingBase.world;

        if (world.isRemote) return;

        BlockPos pos = new BlockPos(entityLivingBase);
        WorldIngredient ingredient = WorldIngredient.getFrom(world, pos);

        // early escape
        if (!FluidBehaviorRecipeManager.recipeExists(ingredient)) return;

        ComplexOutput complexOutput = FluidBehaviorRecipeManager.getRecipeOutput(ingredient);
        MinecraftForge.EVENT_BUS.post(new CustomFluidBehaviorEvent(
                world,
                pos,
                entityLivingBase,
                complexOutput));
    }
}
