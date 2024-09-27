package com.tttsaurus.fluidintetweaker.common.api;

import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEventType;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidInteractionDelegate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

public class ComplexOutput
{
    private final List<InteractionEvent> events = new ArrayList<>();
    public List<InteractionEvent> getEvents() { return events; }

    private ComplexOutput() { }

    public static ComplexOutput create()
    {
        return new ComplexOutput();
    }
    public static ComplexOutput createSimpleBlockOutput(IBlockState blockState)
    {
        return create().addEvent(InteractionEvent.createSetBlockEvent(blockState));
    }
    public ComplexOutput addEvent(InteractionEvent event)
    {
        events.add(event);
        return this;
    }

    public FluidInteractionDelegate getOutputDelegate(CustomFluidInteractionEvent fluidInteractionEvent)
    {
        World world = fluidInteractionEvent.getWorld();
        BlockPos pos = fluidInteractionEvent.getPos();
        return new FluidInteractionDelegate()
        {
            @Override
            public void doAction()
            {
                for (InteractionEvent event: events)
                {
                    List<IEventCondition> conditions = event.getConditions();
                    boolean flag = true;
                    for (IEventCondition condition: conditions)
                    {
                        flag = condition.judge(fluidInteractionEvent);
                        if (!flag) break;
                    }
                    if (flag)
                    {
                        InteractionEventType eventType = event.getEventType();
                        if (eventType == InteractionEventType.SetBlock)
                        {
                            world.setBlockState(pos, event.getBlockState());
                        }
                        else if (eventType == InteractionEventType.Explosion)
                        {

                        }
                        else if (eventType == InteractionEventType.SpawnEntity)
                        {

                        }
                        else if (eventType == InteractionEventType.SpawnEntityItem)
                        {

                        }
                    }
                }
            }
        };
    }
}
