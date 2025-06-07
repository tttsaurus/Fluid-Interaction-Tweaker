package com.tttsaurus.fluidintetweaker.common.core.behavior.condition;

import com.tttsaurus.fluidintetweaker.common.core.behavior.FluidBehaviorRecipe;
import com.tttsaurus.fluidintetweaker.common.core.event.CustomFluidBehaviorEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IEventCondition
{
    boolean judge(CustomFluidBehaviorEvent fluidBehaviorEvent);

    @SideOnly(Side.CLIENT)
    default String getDesc(FluidBehaviorRecipe recipe) { return null; }
}
