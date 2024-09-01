package com.tttsaurus.fluidintetweaker.common.api.event;

import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class CustomFluidInteractionEvent extends BlockEvent
{
    private final Fluid fluidBeforeInteraction;
    private final InteractionIngredient ingredientA;
    private final InteractionIngredient ingredientB;

    public Fluid getFluidBeforeInteraction() { return fluidBeforeInteraction; }
    public InteractionIngredient getIngredientA() { return ingredientA; }
    public InteractionIngredient getIngredientB() { return ingredientB; }

    public CustomFluidInteractionEvent(World world, BlockPos pos, Fluid fluidBeforeInteraction, InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock)
    {
        super(world, pos, outputBlock.getDefaultState());
        this.fluidBeforeInteraction = fluidBeforeInteraction;
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
    }
}
