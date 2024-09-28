package com.tttsaurus.fluidintetweaker.client.jefi.impl.delegate;

import com.cleanroommc.modularui.drawable.GuiDraw;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.common.api.delegate.IDelegate;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.util.BlockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
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

    private final int hoverIndex;
    private final InteractionEvent interactionEvent;
    private final int width;
    private final int height;
    private final List<String> lines;
    public int getHoverIndex() { return hoverIndex; }
    public List<String> getLines() { return lines; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public RenderExtraTooltipDelegate(int hoverIndex, InteractionEvent interactionEvent, int width, int height, List<String> lines)
    {
        this.hoverIndex = hoverIndex;
        this.interactionEvent = interactionEvent;
        this.width = width;
        this.height = height;
        this.lines = lines;
    }

    @Override
    public void doAction()
    {
        if (FluidInteractionTweaker.IS_MODULARUI_LOADED)
        {
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
