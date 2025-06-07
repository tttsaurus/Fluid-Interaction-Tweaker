package com.tttsaurus.fluidintetweaker.common.core.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.core.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.core.interaction.FluidInteractionRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IEventCondition
{
    boolean judge(CustomFluidInteractionEvent fluidInteractionEvent);

    @SideOnly(Side.CLIENT)
    default String getDesc(FluidInteractionRecipe recipe) { return null; }
}
