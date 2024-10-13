package com.tttsaurus.fluidintetweaker.proxy;

import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.common.impl.FluidInteractionEventHandler;
import com.tttsaurus.fluidintetweaker.common.impl.FluidInteractionLogic;
import com.tttsaurus.fluidintetweaker.wrapper.crt.impl.CrTEventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event, Logger logger)
    {

    }

    public void init(FMLInitializationEvent event, Logger logger)
    {
        logger.info(FluidInteractionTweaker.NAME + " starts initializing.");

        MinecraftForge.EVENT_BUS.register(FluidInteractionLogic.class);
        MinecraftForge.EVENT_BUS.register(FluidInteractionEventHandler.class);
        MinecraftForge.EVENT_BUS.register(CrTEventManager.Handler.class);
    }

    public void postInit(FMLPostInitializationEvent event, Logger logger)
    {

    }
}
