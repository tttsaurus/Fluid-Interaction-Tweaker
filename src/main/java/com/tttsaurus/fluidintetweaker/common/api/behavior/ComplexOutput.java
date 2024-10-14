package com.tttsaurus.fluidintetweaker.common.api.behavior;

import com.tttsaurus.fluidintetweaker.common.api.behavior.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidBehaviorEvent;
import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidBehaviorDelegate;
import java.util.ArrayList;
import java.util.List;

public class ComplexOutput
{
    private final List<BehaviorEvent> events = new ArrayList<>();
    public List<BehaviorEvent> getEvents() { return events; }

    private ComplexOutput() { }
    public static ComplexOutput create()
    {
        return new ComplexOutput();
    }

    public ComplexOutput addEvent(BehaviorEvent event)
    {
        events.add(event);
        return this;
    }

    public FluidBehaviorDelegate getOutputDelegate(CustomFluidBehaviorEvent fluidBehaviorEvent)
    {
        return new FluidBehaviorDelegate()
        {
            @Override
            public void doAction()
            {
                for (BehaviorEvent event: events)
                {
                    List<IEventCondition> conditions = event.getConditions();
                    boolean flag = true;
                    for (IEventCondition condition : conditions)
                    {
                        flag = condition.judge(fluidBehaviorEvent);
                        if (!flag) break;
                    }
                    if (flag)
                    {
                        BehaviorEventType eventType = event.getEventType();
                        if (eventType == BehaviorEventType.PotionEffect)
                        {

                        }
                        else if (eventType == BehaviorEventType.EntityConversion)
                        {

                        }
                        else if (eventType == BehaviorEventType.ExtinguishFire)
                        {

                        }
                        else if (eventType == BehaviorEventType.SetFire)
                        {

                        }
                        else if (eventType == BehaviorEventType.SetSnow)
                        {

                        }
                        else if (eventType == BehaviorEventType.Teleport)
                        {

                        }
                        else if (eventType == BehaviorEventType.BreakSurrounding)
                        {

                        }
                    }
                }
            }
        };
    }
}
