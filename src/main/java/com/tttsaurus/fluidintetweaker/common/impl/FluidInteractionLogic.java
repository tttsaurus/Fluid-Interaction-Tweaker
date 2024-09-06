package com.tttsaurus.fluidintetweaker.common.impl;

import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredientType;
import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidInteractionDelegate;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import java.util.*;

public final class FluidInteractionLogic
{
    @SubscribeEvent
    public static void onNeighborNotify(NeighborNotifyEvent event)
    {
        World world = event.getWorld();

        if (world.isRemote) return;

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

            //<editor-fold desc="ingredient1 reacts with ingredient2">

            // forward notifying
            // when ingredient1 being the ingredientA and ingredient2 being the ingredientB

            // special case: fluid (ingredient2) under another fluid (ingredient1)
            // treat fluid above (ingredient1) as flowing
            if ((ingredient1.getIngredientType() == InteractionIngredientType.FLUID) &&
                (ingredient2.getIngredientType() == InteractionIngredientType.FLUID) &&
                (facing == EnumFacing.DOWN))
            {
                // fluid below (ingredient2) turns to a block
                ingredient1.setIsFluidSource(false);

                InteractionIngredient ingredientA, ingredientB;
                Block output = FluidInteractionRecipeManager.getNullableRecipeOutput(ingredient1, ingredient2);
                if (output == null)
                { ingredientA = ingredient2; ingredientB = ingredient1; }
                else
                { ingredientA = ingredient1; ingredientB = ingredient2; }
                output = output == null ? FluidInteractionRecipeManager.getNullableRecipeOutput(ingredient2, ingredient1) : output;
                if (output == null) continue;

                MinecraftForge.EVENT_BUS.post(new CustomFluidInteractionEvent(
                        world,
                        neighborPos,
                        true,
                        world.getBlockState(neighborPos),
                        ingredient2.getFluid(),
                        ingredientA,
                        ingredientB,
                        output,
                        new FluidInteractionDelegate(world, neighborPos, output.getDefaultState())));
                return;
            }
            // normal case
            else if (FluidInteractionRecipeManager.recipeExists(ingredient1, ingredient2))
            {
                Block output = FluidInteractionRecipeManager.getRecipeOutput(ingredient1, ingredient2);
                // ingredient1 turns to a block
                MinecraftForge.EVENT_BUS.post(new CustomFluidInteractionEvent(
                        world,
                        pos,
                        false,
                        world.getBlockState(pos),
                        ingredient1.getFluid(),
                        ingredient1, // A
                        ingredient2, // B
                        output,
                        new FluidInteractionDelegate(world, pos, output.getDefaultState())));
                return;
            }

            // backward notifying
            // when ingredient2 being the ingredientA and ingredient1 being the ingredientB

            // special case: fluid (ingredient1) under another fluid (ingredient2)
            // treat fluid above (ingredient2) as flowing
            else if ((ingredient2.getIngredientType() == InteractionIngredientType.FLUID) &&
                     (ingredient1.getIngredientType() == InteractionIngredientType.FLUID) &&
                     (facing == EnumFacing.UP))
            {
                // fluid below (ingredient1) turns to a block
                ingredient2.setIsFluidSource(false);

                InteractionIngredient ingredientA, ingredientB;
                Block output = FluidInteractionRecipeManager.getNullableRecipeOutput(ingredient2, ingredient1);
                if (output == null)
                { ingredientA = ingredient1; ingredientB = ingredient2; }
                else
                { ingredientA = ingredient2; ingredientB = ingredient1; }
                output = output == null ? FluidInteractionRecipeManager.getNullableRecipeOutput(ingredient1, ingredient2) : output;
                if (output == null) continue;

                MinecraftForge.EVENT_BUS.post(new CustomFluidInteractionEvent(
                        world,
                        pos,
                        true,
                        world.getBlockState(pos),
                        ingredient1.getFluid(),
                        ingredientA,
                        ingredientB,
                        output,
                        new FluidInteractionDelegate(world, pos, output.getDefaultState())));
                return;
            }
            // normal case
            else if (FluidInteractionRecipeManager.recipeExists(ingredient2, ingredient1))
            {
                Block output = FluidInteractionRecipeManager.getRecipeOutput(ingredient2, ingredient1);
                // ingredient2 turns to a block
                MinecraftForge.EVENT_BUS.post(new CustomFluidInteractionEvent(
                        world,
                        neighborPos,
                        false,
                        world.getBlockState(neighborPos),
                        ingredient2.getFluid(),
                        ingredient2, // A
                        ingredient1, // B
                        output,
                        new FluidInteractionDelegate(world, neighborPos, output.getDefaultState())));
                return;
            }
            //</editor-fold>
        }
    }
}
