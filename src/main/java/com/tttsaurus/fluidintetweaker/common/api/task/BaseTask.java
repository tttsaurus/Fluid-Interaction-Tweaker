package com.tttsaurus.fluidintetweaker.common.api.task;

public class BaseTask
{
    public int delay;
    public boolean deferrable;

    protected BaseTask(int delay)
    {
        this.delay = delay;
        deferrable = true;
    }
    public void run()
    {

    }
    public boolean compare(BaseTask task)
    {
        return this.equals(task);
    }
}