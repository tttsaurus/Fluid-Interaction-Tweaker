package com.tttsaurus.fluidintetweaker.proxy;

import com.tttsaurus.fluidintetweaker.client.jefi.impl.OnTooltipEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.Logger;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init(FMLInitializationEvent event, Logger logger)
    {
        super.init(event, logger);
        MinecraftForge.EVENT_BUS.register(OnTooltipEventHandler.class);
    }
}
