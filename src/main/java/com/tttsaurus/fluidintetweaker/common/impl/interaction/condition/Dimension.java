package com.tttsaurus.fluidintetweaker.common.impl.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.core.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.core.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.core.interaction.condition.IEventCondition;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.DimensionType;

public class Dimension implements IEventCondition
{
    private final int dimID;

    public Dimension(int dimID)
    {
        this.dimID = dimID;
    }

    @Override
    public boolean judge(CustomFluidInteractionEvent fluidInteractionEvent)
    {
        return fluidInteractionEvent.getWorld().provider.getDimension() == dimID;
    }

    private String localizedDimName = "";
    @Override
    public String getDesc(FluidInteractionRecipe recipe)
    {
        if (localizedDimName.isEmpty())
        {
            try { localizedDimName = DimensionType.getById(dimID).getName(); }
            catch (Exception ignored) { localizedDimName = "Invalid dimID"; }
        }
        return I18n.format("fluidintetweaker.jefi.condition.dimension", localizedDimName);
    }
}
