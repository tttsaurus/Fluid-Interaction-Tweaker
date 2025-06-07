package com.tttsaurus.fluidintetweaker.common.core.event;

import com.tttsaurus.fluidintetweaker.common.core.interaction.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.core.interaction.StringRecipeProtocol;
import com.tttsaurus.fluidintetweaker.common.core.delegate.IFluidInteractionDelegate;
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
    private final WorldIngredient ingredientA;
    private final WorldIngredient ingredientB;
    private final ComplexOutput complexOutput;
    private final IFluidInteractionDelegate delegate;

    public BlockPos getPos() { return pos; }
    public boolean getIsFluidAboveAndBelowCase() { return isFluidAboveAndBelowCase; }
    public boolean getIsInitiatorAbove() { return isInitiatorAbove; }
    public IBlockState getBlockStateBeforeInteraction() { return blockStateBeforeInteraction; }
    public WorldIngredient getIngredientA() { return ingredientA; }
    public WorldIngredient getIngredientB() { return ingredientB; }
    public ComplexOutput getComplexOutput() { return complexOutput; }
    public IFluidInteractionDelegate getDelegate() { return delegate; }
    public String getFluidInteractionRecipeKey() { return StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB); }

    public CustomFluidInteractionEvent(World world, BlockPos pos, boolean isFluidAboveAndBelowCase, boolean isInitiatorAbove, IBlockState blockStateBeforeInteraction, WorldIngredient ingredientA, WorldIngredient ingredientB, ComplexOutput complexOutput)
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
