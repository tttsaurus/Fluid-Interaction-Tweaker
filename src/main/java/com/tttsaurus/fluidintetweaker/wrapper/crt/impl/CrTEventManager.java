package com.tttsaurus.fluidintetweaker.wrapper.crt.impl;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidBehaviorEvent;
import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.wrapper.crt.api.event.ICustomFluidBehaviorEvent;
import com.tttsaurus.fluidintetweaker.wrapper.crt.api.event.ICustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.wrapper.crt.impl.event.MCCustomFluidBehaviorEvent;
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
public abstract class CrTEventManager
{
    private static final EventList<ICustomFluidInteractionEvent> customFluidInteractionEventList = new EventList<>();
    private static final EventList<ICustomFluidBehaviorEvent> customFluidBehaviorEventList = new EventList<>();

    @ZenMethod
    public static IEventHandle onCustomFluidInteraction(IEventManager manager, IEventHandler<ICustomFluidInteractionEvent> event)
    {
        return customFluidInteractionEventList.add(event);
    }
    @ZenMethod
    public static IEventHandle onCustomFluidBehavior(IEventManager manager, IEventHandler<ICustomFluidBehaviorEvent> event)
    {
        return customFluidBehaviorEventList.add(event);
    }

    public static final class Handler
    {
        @SubscribeEvent
        public static void onCustomFluidInteraction(CustomFluidInteractionEvent event)
        {
            if (customFluidInteractionEventList.hasHandlers())
                customFluidInteractionEventList.publish(new MCCustomFluidInteractionEvent(event));
        }
        @SubscribeEvent
        public static void onCustomFluidBehavior(CustomFluidBehaviorEvent event)
        {
            if (customFluidBehaviorEventList.hasHandlers())
                customFluidBehaviorEventList.publish(new MCCustomFluidBehaviorEvent(event));
        }
    }
}
