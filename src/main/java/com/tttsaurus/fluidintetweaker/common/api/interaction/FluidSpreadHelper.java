package com.tttsaurus.fluidintetweaker.common.api.interaction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import javax.annotation.Nullable;
import java.util.HashMap;

// aborted
public final class FluidSpreadHelper
{
    public static final PropertyInteger FLUID_SPREAD_COUNTER = PropertyInteger.create("fluid_spread_counter", 0, 15);
    public static final IBlockState SPREAD_COUNTABLE_WATER = (new BlockStateContainer(Blocks.WATER, BlockLiquid.LEVEL, FLUID_SPREAD_COUNTER)).getBaseState();
    public static final IBlockState SPREAD_COUNTABLE_LAVA = (new BlockStateContainer(Blocks.LAVA, BlockLiquid.LEVEL, FLUID_SPREAD_COUNTER)).getBaseState();
    private static final HashMap<Block, IBlockState> SPREAD_COUNTABLE_FLUID = new HashMap<>()
    {
        {
            put(Blocks.WATER, SPREAD_COUNTABLE_WATER);
            put(Blocks.LAVA, SPREAD_COUNTABLE_LAVA);
        }
    };

    // problematic
    // doesn't work for most of modded fluids
    @Nullable
    public static IBlockState getSpreadCountableFluid(Fluid fluid)
    {
        Block key = fluid.getBlock();
        if (key == null) return null;
        IBlockState blockState = SPREAD_COUNTABLE_FLUID.get(key);
        if (blockState != null) return blockState;
        IBlockState value = (new BlockStateContainer(key, BlockLiquid.LEVEL, FLUID_SPREAD_COUNTER)).getBaseState();
        SPREAD_COUNTABLE_FLUID.put(key, value);
        return value;
    }
}
