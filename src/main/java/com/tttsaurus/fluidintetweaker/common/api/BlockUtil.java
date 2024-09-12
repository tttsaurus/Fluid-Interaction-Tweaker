package com.tttsaurus.fluidintetweaker.common.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public final class BlockUtil
{
    @SuppressWarnings("ConstantConditions")
    public static String toString(IBlockState blockState)
    {
        if (blockState == null)
            return "";
        else
            return blockState.getBlock().getRegistryName().toString() + ":" + blockState.getBlock().getMetaFromState(blockState);
    }

    public static int getMeta(IBlockState blockState)
    {
        if (blockState == null)
            return 0;
        else
            return blockState.getBlock().getMetaFromState(blockState);
    }

    public static ItemStack getItemStack(IBlockState blockState)
    {
        return getItemStack(blockState, 1);
    }
    public static ItemStack getItemStack(IBlockState blockState, int amount)
    {
        return new ItemStack(blockState.getBlock(), amount, getMeta(blockState));
    }
}
