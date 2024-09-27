package com.tttsaurus.fluidintetweaker.common.api.interaction;

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

public class InteractionIngredient
{
    public static final String KEYWORD_BLOCK = "BLOCK";
    public static final String KEYWORD_FLUID_SOURCE = "FLUID_SOURCE";
    public static final String KEYWORD_FLUID_FLOWING = "FLUID_FLOWING";

    public static final InteractionIngredient AIR = new InteractionIngredient(Blocks.AIR.getDefaultState());
    public static final InteractionIngredient SOURCE_WATER = new InteractionIngredient(FluidRegistry.WATER, true);
    public static final InteractionIngredient FLOWING_WATER = new InteractionIngredient(FluidRegistry.WATER, false);
    public static final InteractionIngredient SOURCE_LAVA = new InteractionIngredient(FluidRegistry.LAVA, true);
    public static final InteractionIngredient FLOWING_LAVA = new InteractionIngredient(FluidRegistry.LAVA, false);

    private InteractionIngredientType ingredientType;
    private Fluid fluid;
    private boolean isFluidSource;
    private IBlockState blockState;

    //<editor-fold desc="getter">
    public InteractionIngredientType getIngredientType() { return ingredientType; }
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
        if (ingredientType == InteractionIngredientType.BLOCK)
            return KEYWORD_BLOCK + "{" + BlockUtils.toString(blockState) + "}";
        else if (ingredientType == InteractionIngredientType.FLUID)
        {
            if (isFluidSource)
                return KEYWORD_FLUID_SOURCE + "{" + fluid.getName() + "}";
            else
                return KEYWORD_FLUID_FLOWING + "{" + fluid.getName() + "}";
        }
        else
            return null;
    }
    @Override
    public boolean equals(Object object)
    {
        return object instanceof InteractionIngredient && object.toString().equals(this.toString());
    }

    public InteractionIngredient(@Nonnull Fluid fluid, boolean isFluidSource)
    {
        this.ingredientType = InteractionIngredientType.FLUID;
        this.fluid = fluid;
        this.isFluidSource = isFluidSource;
        this.blockState = null;
    }
    public InteractionIngredient(@Nonnull IBlockState blockState)
    {
        this.ingredientType = InteractionIngredientType.BLOCK;
        this.fluid = null;
        this.isFluidSource = false;
        this.blockState = blockState;
    }
    private InteractionIngredient() { }

    public static InteractionIngredient getFrom(@Nonnull World world, @Nonnull BlockPos pos)
    {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        ResourceLocation rl = block.getRegistryName();
        if (rl == null) return AIR;

        InteractionIngredient ingredient = new InteractionIngredient();
        String registryName = rl.toString();

        // air
        if (registryName.equals("minecraft:air")) return AIR;
        // vanilla liquid
        else if (block instanceof BlockLiquid)
        {
            ingredient.ingredientType = InteractionIngredientType.FLUID;
            if (registryName.equals("minecraft:water") || registryName.equals("minecraft:flowing_water"))
                ingredient.fluid = FluidRegistry.WATER;
            else if (registryName.equals("minecraft:lava") || registryName.equals("minecraft:flowing_lava"))
                ingredient.fluid = FluidRegistry.LAVA;

            ingredient.isFluidSource = blockState.getValue(BlockLiquid.LEVEL) == 0;
        }
        // modded fluid
        else if (block instanceof BlockFluidBase fluidBase)
        {
            ingredient.ingredientType = InteractionIngredientType.FLUID;
            ingredient.fluid = fluidBase.getFluid();

            ingredient.isFluidSource = fluidBase.canDrain(world, pos);
        }
        // solid block
        else
        {
            ingredient.ingredientType = InteractionIngredientType.BLOCK;
            ingredient.blockState = blockState;
        }
        return ingredient;
    }
}
