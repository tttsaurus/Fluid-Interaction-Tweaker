package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.wrapper.crt.api.event.ICustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.wrapper.crt.impl.event.MCCustomFluidInteractionEvent;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.fluidintetweaker.IEventManager")
@ZenExpansion("crafttweaker.events.IEventManager")
public abstract class CTEventManager
{
    private static final EventList<ICustomFluidInteractionEvent> customFluidInteractionEventList = new EventList<>();

    @ZenMethod
    public static IEventHandle onCustomFluidInteraction(IEventManager manager, IEventHandler<ICustomFluidInteractionEvent> event)
    {
        return customFluidInteractionEventList.add(event);
    }

    public static final class Handler
    {
        @SubscribeEvent
        public static void onCustomFluidInteraction(CustomFluidInteractionEvent event)
        {
            if (customFluidInteractionEventList.hasHandlers())
                customFluidInteractionEventList.publish(new MCCustomFluidInteractionEvent(event));
        }
    }
}
