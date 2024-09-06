package com.tttsaurus.fluidintetweaker.common.api.event;

import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.StringRecipeProtocol;
import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidInteractionDelegate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class CustomFluidInteractionEvent extends BlockEvent
{
    private final boolean isFluidAboveAndBelowCase;
    private final IBlockState fluidBlockStateBeforeInteraction;
    private final Fluid fluidBeforeInteraction;
    private final InteractionIngredient ingredientA;
    private final InteractionIngredient ingredientB;
    private final FluidInteractionDelegate delegate;

    public boolean getIsFluidAboveAndBelowCase() { return isFluidAboveAndBelowCase; }
    public IBlockState getFluidBlockStateBeforeInteraction() { return fluidBlockStateBeforeInteraction; }
    public Fluid getFluidBeforeInteraction() { return fluidBeforeInteraction; }
    public InteractionIngredient getIngredientA() { return ingredientA; }
    public InteractionIngredient getIngredientB() { return ingredientB; }
    public FluidInteractionDelegate getDelegate() { return delegate; }
    public String getFluidInteractionRecipeKey() { return StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB); }

    public CustomFluidInteractionEvent(World world, BlockPos pos, boolean isFluidAboveAndBelowCase, IBlockState fluidBlockStateBeforeInteraction, Fluid fluidBeforeInteraction, InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock, FluidInteractionDelegate delegate)
    {
        super(world, pos, outputBlock.getDefaultState());
        this.isFluidAboveAndBelowCase = isFluidAboveAndBelowCase;
        this.fluidBlockStateBeforeInteraction = fluidBlockStateBeforeInteraction;
        this.fluidBeforeInteraction = fluidBeforeInteraction;
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.delegate = delegate;
    }
}
