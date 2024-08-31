package com.tttsaurus.fluidintetweaker.common;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import java.util.*;

public final class FluidEventHandler
{
    @SubscribeEvent
    public static void onNeighborNotifyEvent(NeighborNotifyEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Block block = event.getState().getBlock();

        InteractionIngredient ingredient1 = new InteractionIngredient(world, pos, block);

        // the recipe doesn't exist
        // so early escape
        if (!(FluidInteractionRecipeManager.ingredientAExists(ingredient1) ||
              FluidInteractionRecipeManager.ingredientBExists(ingredient1))) return;

        EnumSet<EnumFacing> sides = event.getNotifiedSides();
        for (EnumFacing facing : sides)
        {
            Vec3i vec3 = facing.getDirectionVec();
            int x = vec3.getX();
            int y = vec3.getY();
            int z = vec3.getZ();

            BlockPos neighborPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
            Block neighborBlock = world.getBlockState(neighborPos).getBlock();

            InteractionIngredient ingredient2 = new InteractionIngredient(world, neighborPos, neighborBlock);

            if (FluidInteractionRecipeManager.recipeExists(ingredient1, ingredient2))
            {
                Block output = FluidInteractionRecipeManager.getRecipeOutput(ingredient1, ingredient2);
                world.setBlockState(pos, output.getDefaultState());
            }
            else if (FluidInteractionRecipeManager.recipeExists(ingredient2, ingredient1))
            {
                Block output = FluidInteractionRecipeManager.getRecipeOutput(ingredient2, ingredient1);
                world.setBlockState(neighborPos, output.getDefaultState());
            }
        }
    }
}
