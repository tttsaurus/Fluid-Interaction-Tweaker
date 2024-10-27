package com.tttsaurus.fluidintetweaker.common.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("all")
public final class BlockUtils
{
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
    public static IBlockState getBlockState(String id, int meta)
    {
        return getBlock(id).getStateFromMeta(meta);
    }
    public static IBlockState getBlockState(String id)
    {
        return getBlockState(id, 0);
    }
    public static IBlockState getBlockState(ItemStack itemStack)
    {
        return getBlockState(itemStack.getItem().getRegistryName().toString(), itemStack.getMetadata());
    }
}
