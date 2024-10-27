package com.tttsaurus.fluidintetweaker.common.api.behavior.condition;

import com.tttsaurus.fluidintetweaker.common.api.behavior.FluidBehaviorRecipe;
import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidBehaviorEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IEventCondition
{
    boolean judge(CustomFluidBehaviorEvent fluidBehaviorEvent);

    @SideOnly(Side.CLIENT)
    default String getDesc(FluidBehaviorRecipe recipe) { return null; }
}
