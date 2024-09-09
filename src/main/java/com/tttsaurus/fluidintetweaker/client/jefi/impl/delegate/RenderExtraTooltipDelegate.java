package com.tttsaurus.fluidintetweaker.client.jefi.impl.delegate;

import com.cleanroommc.modularui.drawable.GuiDraw;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.common.api.delegate.IDelegate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.awt.*;
import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderExtraTooltipDelegate implements IDelegate
{
    private int x;
    private int y;
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    private final ItemStack itemStack;
    private final int width;
    private final int height;
    private final List<String> lines;

    public RenderExtraTooltipDelegate(ItemStack itemStack, int width, int height, List<String> lines)
    {
        this.itemStack = itemStack;
        this.width = width;
        this.height = height;
        this.lines = lines;
    }

    @Override
    public void doAction()
    {
        if (FluidInteractionTweaker.IS_MODULARUI_LOADED)
        {
            GuiDraw.drawTooltipBackground(itemStack, lines, x, y, width, height);
            for (int i = 0; i < lines.size(); i++)
                GuiDraw.drawText(lines.get(i), x, y + 9 * i, 1, Color.GRAY.getRGB(), true);
        }
        else
        {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            for (int i = 0; i < lines.size(); i++)
                fontRenderer.drawString(lines.get(i), x, y + 9 * i, Color.GRAY.getRGB(), true);
        }
    }
}
