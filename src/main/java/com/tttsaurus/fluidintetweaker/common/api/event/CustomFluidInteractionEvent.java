package com.tttsaurus.fluidintetweaker.common.api.event;

import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.StringRecipeProtocol;
import com.tttsaurus.fluidintetweaker.common.api.delegate.IDelegate;
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
    private final IBlockState fluidBlockStateBeforeInteraction;
    private final Fluid fluidBeforeInteraction;
    private final InteractionIngredient ingredientA;
    private final InteractionIngredient ingredientB;
    private final IDelegate delegate;

    public IBlockState getFluidBlockStateBeforeInteraction() { return fluidBlockStateBeforeInteraction; }
    public Fluid getFluidBeforeInteraction() { return fluidBeforeInteraction; }
    public InteractionIngredient getIngredientA() { return ingredientA; }
    public InteractionIngredient getIngredientB() { return ingredientB; }
    public IDelegate getDelegate() { return delegate; }
    public String getFluidInteractionRecipeKey() { return StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB); }

    public CustomFluidInteractionEvent(World world, BlockPos pos, IBlockState fluidBlockStateBeforeInteraction, Fluid fluidBeforeInteraction, InteractionIngredient ingredientA, InteractionIngredient ingredientB, Block outputBlock, IDelegate delegate)
    {
        super(world, pos, outputBlock.getDefaultState());
        this.fluidBlockStateBeforeInteraction = fluidBlockStateBeforeInteraction;
        this.fluidBeforeInteraction = fluidBeforeInteraction;
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.delegate = delegate;
    }
}
