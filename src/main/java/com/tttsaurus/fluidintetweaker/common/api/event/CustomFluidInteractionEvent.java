package com.tttsaurus.fluidintetweaker.common.api.event;

import com.tttsaurus.fluidintetweaker.common.api.interaction.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.util.StringRecipeProtocol;
import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidInteractionDelegate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class CustomFluidInteractionEvent extends WorldEvent
{
    private final BlockPos pos;
    private final boolean isFluidAboveAndBelowCase;
    private final boolean isInitiatorAbove;
    private final IBlockState blockStateBeforeInteraction;
    private final InteractionIngredient ingredientA;
    private final InteractionIngredient ingredientB;
    private final ComplexOutput complexOutput;
    private final FluidInteractionDelegate delegate;

    public BlockPos getPos() { return pos; }
    public boolean getIsFluidAboveAndBelowCase() { return isFluidAboveAndBelowCase; }
    public boolean getIsInitiatorAbove() { return isInitiatorAbove; }
    public IBlockState getBlockStateBeforeInteraction() { return blockStateBeforeInteraction; }
    public InteractionIngredient getIngredientA() { return ingredientA; }
    public InteractionIngredient getIngredientB() { return ingredientB; }
    public ComplexOutput getComplexOutput() { return complexOutput; }
    public FluidInteractionDelegate getDelegate() { return delegate; }
    public String getFluidInteractionRecipeKey() { return StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB); }

    public CustomFluidInteractionEvent(World world, BlockPos pos, boolean isFluidAboveAndBelowCase, boolean isInitiatorAbove, IBlockState blockStateBeforeInteraction, InteractionIngredient ingredientA, InteractionIngredient ingredientB, ComplexOutput complexOutput)
    {
        super(world);
        this.pos = pos;
        this.isFluidAboveAndBelowCase = isFluidAboveAndBelowCase;
        this.isInitiatorAbove = isInitiatorAbove;
        this.blockStateBeforeInteraction = blockStateBeforeInteraction;
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.complexOutput = complexOutput;
        this.delegate = complexOutput.getOutputDelegate(this);
    }
}
