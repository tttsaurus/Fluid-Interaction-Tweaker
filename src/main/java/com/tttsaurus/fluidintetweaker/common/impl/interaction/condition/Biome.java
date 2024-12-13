package com.tttsaurus.fluidintetweaker.common.impl.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class Biome implements IEventCondition
{
    private final String biomeName;

    public Biome(String biomeName)
    {
        this.biomeName = biomeName;
    }

    @Override
    public boolean judge(CustomFluidInteractionEvent fluidInteractionEvent)
    {
        ResourceLocation rl = fluidInteractionEvent.getWorld().getBiome(fluidInteractionEvent.getPos()).getRegistryName();
        if (rl == null) return false;
        return rl.toString().equals(biomeName);
    }

    @Override
    public String getDesc(FluidInteractionRecipe recipe)
    {
        return I18n.format("fluidintetweaker.jefi.condition.biome");
    }
}
