package com.tttsaurus.fluidintetweaker.common.api.interaction.condition;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IEventCondition
{
    boolean judge(CustomFluidInteractionEvent fluidInteractionEvent);

    @SideOnly(Side.CLIENT)
    default String getDesc() { return null; }
}
