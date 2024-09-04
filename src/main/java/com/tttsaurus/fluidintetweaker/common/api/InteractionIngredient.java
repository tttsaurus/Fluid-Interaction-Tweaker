package com.tttsaurus.fluidintetweaker.common.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class InteractionIngredient
{
    public static final String KEYWORD_BLOCK = "BLOCK";
    public static final String KEYWORD_FLUID_SOURCE = "FLUID_SOURCE";
    public static final String KEYWORD_FLUID_FLOWING = "FLUID_FLOWING";

    public static final InteractionIngredient SOURCE_WATER = new InteractionIngredient(FluidRegistry.WATER, true);
    public static final InteractionIngredient FLOWING_WATER = new InteractionIngredient(FluidRegistry.WATER, false);
    public static final InteractionIngredient SOURCE_LAVA = new InteractionIngredient(FluidRegistry.LAVA, true);
    public static final InteractionIngredient FLOWING_LAVA = new InteractionIngredient(FluidRegistry.LAVA, false);

    private InteractionIngredientType ingredientType;
    private Fluid fluid;
    private boolean isFluidSource;
    private Block block;

    //<editor-fold desc="getter">
    public InteractionIngredientType getIngredientType() { return ingredientType; }
    public Fluid getFluid() { return fluid; }
    public boolean getIsFluidSource() { return isFluidSource; }
    public Block getBlock() { return block; }
    //</editor-fold>

    //<editor-fold desc="setter">
    public void setIsFluidSource(boolean isFluidSource) { this.isFluidSource = isFluidSource; }
    //</editor-fold>

    @Override
    public String toString()
    {
        if (ingredientType == InteractionIngredientType.BLOCK)
            return KEYWORD_BLOCK + ":" + block.getRegistryName().toString();
        else if (ingredientType == InteractionIngredientType.FLUID)
        {
            if (isFluidSource)
                return KEYWORD_FLUID_SOURCE + ":" + fluid.getName();
            else
                return KEYWORD_FLUID_FLOWING + ":" + fluid.getName();
        }
        else
            return null;
    }
    @Override
    public boolean equals(Object object)
    {
        return object instanceof InteractionIngredient && object.toString().equals(this.toString());
    }

    public InteractionIngredient(Fluid fluid, boolean isFluidSource)
    {
        this.ingredientType = InteractionIngredientType.FLUID;
        this.fluid = fluid;
        this.isFluidSource = isFluidSource;
        this.block = null;
    }
    public InteractionIngredient(Block block)
    {
        this.ingredientType = InteractionIngredientType.BLOCK;
        this.fluid = null;
        this.isFluidSource = false;
        this.block = block;
    }
    public InteractionIngredient(World world, BlockPos pos, Block block)
    {
        // vanilla liquid
        if (block instanceof BlockLiquid)
        {
            ingredientType = InteractionIngredientType.FLUID;
            String name = block.getRegistryName().toString();
            if (name.equals("minecraft:water") || name.equals("minecraft:flowing_water"))
                fluid = FluidRegistry.WATER;
            else if (name.equals("minecraft:lava") || name.equals("minecraft:flowing_lava"))
                fluid = FluidRegistry.LAVA;

            isFluidSource = world.getBlockState(pos).getValue(BlockLiquid.LEVEL) == 0;
        }
        // modded fluid
        else if (block instanceof BlockFluidBase)
        {
            ingredientType = InteractionIngredientType.FLUID;
            BlockFluidBase fluidBase = ((BlockFluidBase)block);
            fluid = fluidBase.getFluid();

            isFluidSource = fluidBase.canDrain(world, pos);
        }
        // solid block
        else
        {
            ingredientType = InteractionIngredientType.BLOCK;
            this.block = block;
        }
    }
}
