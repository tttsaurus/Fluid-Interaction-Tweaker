package com.tttsaurus.fluidintetweaker.client.impl.jefi;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public final class OnTooltipEventHandler
{
    private static final List<String> extraTooltip = new ArrayList<>();
    public static void addExtraTooltip(List<String> tooltip) { extraTooltip.addAll(tooltip); }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event)
    {
        if (!extraTooltip.isEmpty())
        {
            List<String> tooltip = event.getToolTip();
            if (GuiScreen.isShiftKeyDown())
                tooltip.addAll(extraTooltip);
            else
                tooltip.add(I18n.format("fluidintetweaker.shift_tips"));
            extraTooltip.clear();
        }
    }
}
