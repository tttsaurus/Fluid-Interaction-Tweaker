package com.tttsaurus.fluidintetweaker.proxy;

import com.tttsaurus.fluidintetweaker.common.impl.FluidInteractionEventHandler;
import com.tttsaurus.fluidintetweaker.common.impl.FluidInteractionLogic;
import com.tttsaurus.fluidintetweaker.wrapper.crt.impl.CTEventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event, Logger logger)
    {

    }

    public void init(FMLInitializationEvent event, Logger logger)
    {
        logger.info("Fluid Interaction Tweaker starts initializing.");

        MinecraftForge.EVENT_BUS.register(FluidInteractionLogic.class);
        MinecraftForge.EVENT_BUS.register(FluidInteractionEventHandler.class);
        MinecraftForge.EVENT_BUS.register(CTEventManager.Handler.class);
    }
}
