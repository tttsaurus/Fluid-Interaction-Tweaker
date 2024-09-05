package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.client.jefi.impl.delegate.RenderExtraTooltipDelegate;
import net.minecraftforge.client.event.RenderTooltipEvent.PostBackground;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class RenderTooltipEventHandler
{
    private static RenderExtraTooltipDelegate renderExtraTooltip = null;
    public static void setRenderExtraTooltip(RenderExtraTooltipDelegate delegate) { renderExtraTooltip = delegate; }

    @SubscribeEvent
    public static void onRenderTooltipPostBackground(PostBackground event)
    {
        if (renderExtraTooltip != null)
        {
            renderExtraTooltip.setX(event.getX());
            renderExtraTooltip.setY(event.getY() + event.getHeight() + 7);
            renderExtraTooltip.doAction();
            renderExtraTooltip = null;
        }
    }
}
