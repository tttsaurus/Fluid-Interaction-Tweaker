package com.tttsaurus.fluidintetweaker.common.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class BlockUtils
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

    public static Block getBlock(String id)
    {
        return Block.REGISTRY.getObject(new ResourceLocation(id));
    }
    @SuppressWarnings("deprecation")
    public static IBlockState getBlockState(String id, int meta)
    {
        return getBlock(id).getStateFromMeta(meta);
    }
}
