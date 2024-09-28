package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.client.jefi.impl.delegate.RenderExtraTooltipDelegate;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderTooltipEvent.PostText;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public final class RenderTooltipEventHandler
{
    private static RenderExtraTooltipDelegate renderExtraTooltip = null;
    public static void setRenderExtraTooltip(RenderExtraTooltipDelegate delegate) { renderExtraTooltip = delegate; }

    @SubscribeEvent
    public static void onRenderTooltipPostText(PostText event)
    {
        if (renderExtraTooltip != null)
        {
            if (GuiScreen.isShiftKeyDown())
            {
                renderExtraTooltip.setX(event.getX());
                renderExtraTooltip.setY(event.getY() + event.getHeight() - renderExtraTooltip.getHeight() - 9);
                renderExtraTooltip.doAction();
            }
            renderExtraTooltip = null;
        }
    }
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event)
    {
        if (renderExtraTooltip != null)
        {
            List<String> tooltip = event.getToolTip();
            if (GuiScreen.isShiftKeyDown())
            {
                float height_count = renderExtraTooltip.getHeight() / 9f;
                height_count = height_count > (int)height_count ? (int)height_count + 1 : height_count;
                String[] strings = new String[(int)height_count];
                Arrays.fill(strings, "");

                StringBuilder builder = new StringBuilder();
                float width_count = renderExtraTooltip.getWidth() / 4f;
                width_count = width_count > (int)width_count ? (int)width_count + 1 : width_count;
                for (int i = 0; i < width_count; i++) builder.append(" ");

                strings[0] = builder.toString();
                tooltip.addAll(Arrays.asList(strings));
            }
            else
                tooltip.add(I18n.format("fluidintetweaker.jefi.shift_tips"));
        }
    }
}
