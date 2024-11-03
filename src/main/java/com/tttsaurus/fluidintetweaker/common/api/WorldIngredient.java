package com.tttsaurus.fluidintetweaker.common.api;

import com.tttsaurus.fluidintetweaker.common.api.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import javax.annotation.Nonnull;

public class WorldIngredient
{
    public static final String KEYWORD_BLOCK = "BLOCK";
    public static final String KEYWORD_FLUID_SOURCE = "FLUID_SOURCE";
    public static final String KEYWORD_FLUID_FLOWING = "FLUID_FLOWING";

    public static final WorldIngredient AIR = new WorldIngredient(Blocks.AIR.getDefaultState());
    public static final WorldIngredient SOURCE_WATER = new WorldIngredient(FluidRegistry.WATER, true);
    public static final WorldIngredient FLOWING_WATER = new WorldIngredient(FluidRegistry.WATER, false);
    public static final WorldIngredient SOURCE_LAVA = new WorldIngredient(FluidRegistry.LAVA, true);
    public static final WorldIngredient FLOWING_LAVA = new WorldIngredient(FluidRegistry.LAVA, false);

    private WorldIngredientType ingredientType;
    private Fluid fluid;
    private boolean isFluidSource;
    private IBlockState blockState;

    //<editor-fold desc="getter">
    public WorldIngredientType getIngredientType() { return ingredientType; }
    public Fluid getFluid() { return fluid; }
    public boolean getIsFluidSource() { return isFluidSource; }
    public IBlockState getBlockState() { return blockState; }
    //</editor-fold>

    //<editor-fold desc="setter">
    public void setIsFluidSource(boolean isFluidSource) { this.isFluidSource = isFluidSource; }
    //</editor-fold>

    @Override
    public String toString()
    {
        if (ingredientType == WorldIngredientType.BLOCK && blockState != null)
            return KEYWORD_BLOCK + "{" + BlockUtils.toString(blockState) + "}";
        else if (ingredientType == WorldIngredientType.FLUID && fluid != null)
        {
            if (isFluidSource)
                return KEYWORD_FLUID_SOURCE + "{" + fluid.getName() + "}";
            else
                return KEYWORD_FLUID_FLOWING + "{" + fluid.getName() + "}";
        }
        else
            return "";
    }
    @Override
    public boolean equals(Object object)
    {
        return object instanceof WorldIngredient && object.toString().equals(this.toString());
    }

    public WorldIngredient(@Nonnull Fluid fluid, boolean isFluidSource)
    {
        this.ingredientType = WorldIngredientType.FLUID;
        this.fluid = fluid;
        this.isFluidSource = isFluidSource;
        this.blockState = null;
    }
    public WorldIngredient(@Nonnull IBlockState blockState)
    {
        this.ingredientType = WorldIngredientType.BLOCK;
        this.fluid = null;
        this.isFluidSource = false;
        this.blockState = blockState;
    }
    private WorldIngredient() { }

    public static WorldIngredient getFrom(@Nonnull IBlockState blockState)
    {
        Block block = blockState.getBlock();
        ResourceLocation rl = block.getRegistryName();
        if (rl == null) return AIR;

        String registryName = rl.toString();

        // air
        if (registryName.equals("minecraft:air")) return AIR;

        WorldIngredient ingredient = new WorldIngredient();
        ingredient.blockState = blockState;

        // vanilla liquid
        if (block instanceof BlockLiquid)
        {
            ingredient.ingredientType = WorldIngredientType.FLUID;
            if (registryName.equals("minecraft:water") || registryName.equals("minecraft:flowing_water"))
                ingredient.fluid = FluidRegistry.WATER;
            else if (registryName.equals("minecraft:lava") || registryName.equals("minecraft:flowing_lava"))
                ingredient.fluid = FluidRegistry.LAVA;

            ingredient.isFluidSource = blockState.getValue(BlockLiquid.LEVEL) == 0;
        }
        // modded fluid
        else if (block instanceof BlockFluidBase fluidBase)
        {
            ingredient.ingredientType = WorldIngredientType.FLUID;
            ingredient.fluid = fluidBase.getFluid();

            ingredient.isFluidSource = blockState.getValue(BlockLiquid.LEVEL) == 0;
        }
        // solid block
        else
        {
            ingredient.ingredientType = WorldIngredientType.BLOCK;
        }
        return ingredient;
    }
    public static WorldIngredient getFrom(@Nonnull World world, @Nonnull BlockPos pos)
    {
        IBlockState blockState = world.getBlockState(pos);
        return getFrom(blockState);
    }
}
