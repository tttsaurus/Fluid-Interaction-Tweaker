package com.tttsaurus.fluidintetweaker.common.api.task;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TaskScheduler
{
    // scheduled tasks
    private static final List<BaseTask> tasks = new ArrayList<>();
    // tasks to be run at this tick
    private static final List<BaseTask> toRun = new ArrayList<>();
    // stores indexes referring to `tasks`
    private static final List<Integer> toRunIndexes = new ArrayList<>();
    // stores some elements, indexes referring to `tasks`, from `toRunIndexes`
    // then remove some tasks from `tasks` based on the `toRemove`
    private static final List<Integer> toRemove = new ArrayList<>();

    // a task can be deferred to the next `maxDeferTickNum` ticks
    private static final int maxDeferTickNum = 3;
    // if the amount of tasks to be run at this tick is greater than `triggerDeferTaskNum`
    // then defer
    private static final int triggerDeferTaskNum = 10;

    public static void scheduleTask(BaseTask task)
    {
        tasks.add(task);
    }

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event)
    {
        for (int i = 0; i < tasks.size(); i++)
        {
            BaseTask task = tasks.get(i);
            task.delay--;
            if (task.delay <= 0)
            {
                toRun.add(task);
                toRunIndexes.add(i);
            }
        }

        // eliminate redundant tasks
        // and defer some tasks to other ticks
        int length = toRun.size();
        if (length > 1)
        {
            int redundantCount = 0;
            int uniqueTaskCount = 0;
            int deferredCount = 0;

            // find redundant tasks
            boolean[] used = new boolean[length];
            Arrays.fill(used, false);
            int i = 0;
            int j = 0;
            for (i = 0; i < length; i++)
            {
                BaseTask task = toRun.get(i);
                if (!used[i])
                    for (j = i; j < length; j++)
                        if (j != i && !used[j] && task.deferrable && task.compare(toRun.get(j)))
                        {
                            used[j] = true;
                            redundantCount++;
                        }
            }
            uniqueTaskCount = length - redundantCount;

            // run or defer tasks
            j = 0;
            for (i = 0; i < length; i++)
            {
                BaseTask task = toRun.get(i);
                if (!used[i])
                {
                    if (maxDeferTickNum <= 0 || j < triggerDeferTaskNum || !task.deferrable)
                    {
                        task.run();
                        toRemove.add(toRunIndexes.get(i));
                    }
                    else
                    {
                        task.deferrable = false;
                        task.delay += (int)(((float)(j + 1) / (float)(uniqueTaskCount)) * maxDeferTickNum);
                        deferredCount++;
                    }
                    j++;
                }
                else
                    toRemove.add(toRunIndexes.get(i));
            }
            for (i = toRemove.size() - 1; i >= 0; i--)
                tasks.remove((int)toRemove.get(i));
            toRemove.clear();
        }
        else if (length == 1)
        {
            toRun.get(0).run();
            tasks.remove((int)toRunIndexes.get(0));
        }

        toRun.clear();
        toRunIndexes.clear();
    }
}
