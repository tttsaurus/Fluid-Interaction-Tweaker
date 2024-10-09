package com.tttsaurus.fluidintetweaker;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import com.tttsaurus.fluidintetweaker.proxy.CommonProxy;

@Mod(modid = FluidInteractionTweaker.MODID,
     name = FluidInteractionTweaker.NAME,
     version = FluidInteractionTweaker.VERSION,
     dependencies = "after:jei@[4.12,);after:thermalfoundation;after:biomesoplenty")
public final class FluidInteractionTweaker
{
    public static final String MODID = "fluidintetweaker";
    public static final String NAME = "Fluid Interaction Tweaker";
    public static final String VERSION = "1.4.0-preview-1";

    public static boolean IS_JEI_LOADED;
    public static boolean IS_THERMALFOUNDATION_LOADED;
    public static boolean IS_BIOMESOPLENTY_LOADED;

    @SidedProxy(
            clientSide = "com.tttsaurus.fluidintetweaker.proxy.ClientProxy",
            serverSide = "com.tttsaurus.fluidintetweaker.proxy.ServerProxy")
    private static CommonProxy proxy;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event, logger);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event, logger);
        logger.info(FluidInteractionTweaker.NAME + " initialized.");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event, logger);
    }
}
