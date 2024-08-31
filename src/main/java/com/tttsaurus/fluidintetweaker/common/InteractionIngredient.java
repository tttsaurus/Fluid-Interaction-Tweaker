package com.tttsaurus.fluidintetweaker.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;

public class InteractionIngredient
{
    private static final Fluid FLUID_WATER = new Fluid("water", new ResourceLocation("minecraft:water"), new ResourceLocation("minecraft:flowing_water"));
    private static final Fluid FLUID_LAVA = new Fluid("lava", new ResourceLocation("minecraft:lava"), new ResourceLocation("minecraft:flowing_lava"));

    private InteractionIngredientType ingredientType;
    private Fluid fluid;
    private boolean isFluidSource;
    private Block block;

    public InteractionIngredientType getIngredientType() { return ingredientType; }
    public Fluid getFluid() { return fluid; }
    public boolean getIsFluidSource() { return isFluidSource; }
    public Block getBlock() { return block; }

    @Override
    public String toString()
    {
        if (ingredientType == InteractionIngredientType.BLOCK)
            return "BLOCK:" + block.getRegistryName().toString();
        else if (ingredientType == InteractionIngredientType.FLUID)
        {
            if (isFluidSource)
                return "FLUID_SOURCE:" + fluid.getName();
            else
                return "FLUID_FLOWING:" + fluid.getName();
        }
        else
            return null;
    }

    public InteractionIngredient(InteractionIngredientType ingredientType, Fluid fluid, boolean isFluidSource, Block block)
    {
        this.ingredientType = ingredientType;
        this.fluid = fluid;
        this.isFluidSource = isFluidSource;
        this.block = block;
    }
    public InteractionIngredient(World world, BlockPos pos, Block block)
    {
        // vanilla liquid
        if (block instanceof BlockLiquid)
        {
            ingredientType = InteractionIngredientType.FLUID;
            String name = block.getRegistryName().toString();
            Material mat = null;
            if (name.equals("minecraft:water") || name.equals("minecraft:flowing_water"))
            {
                fluid = FLUID_WATER;
                mat = Material.WATER;
            }
            else if (name.equals("minecraft:lava") || name.equals("minecraft:flowing_lava"))
            {
                fluid = FLUID_LAVA;
                mat = Material.LAVA;
            }

            // todo: fluid source detection is problematic
            isFluidSource = block.getBlockLiquidHeight(world, pos, world.getBlockState(pos), mat) >= 0.88f;
        }
        // modded fluid
        else if (block instanceof BlockFluidBase)
        {
            ingredientType = InteractionIngredientType.FLUID;
            BlockFluidBase fluidBase = ((BlockFluidBase)block);
            fluid = fluidBase.getFluid();

            // todo: fluid source detection is problematic
            isFluidSource = fluidBase.getFilledPercentage(world, pos) == 1.0f;
        }
        // solid block
        else
        {
            ingredientType = InteractionIngredientType.BLOCK;
            this.block = block;
        }
    }
}
