package com.tttsaurus.fluidintetweaker.common.api.interaction;

import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import net.minecraft.block.state.IBlockState;
import java.util.ArrayList;
import java.util.List;

public class InteractionEvent
{
    private final InteractionEventType eventType;
    private final List<IEventCondition> conditions = new ArrayList<>();
    public InteractionEventType getEventType() { return eventType; }
    public List<IEventCondition> getConditions() { return conditions; }

    // InteractionEventType.SetBlock
    private IBlockState blockState;
    public IBlockState getBlockState() { return blockState; }

    private InteractionEvent(InteractionEventType eventType)
    {
        this.eventType = eventType;
    }

    public static InteractionEvent createSetBlockEvent(IBlockState blockState)
    {
        InteractionEvent event = new InteractionEvent(InteractionEventType.SetBlock);
        event.blockState = blockState;
        return event;
    }
    public InteractionEvent addCondition(IEventCondition condition)
    {
        conditions.add(condition);
        return this;
    }
}
