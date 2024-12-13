package com.tttsaurus.fluidintetweaker.common.api.interaction;

import com.tttsaurus.fluidintetweaker.common.api.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.api.WorldIngredientType;
import com.tttsaurus.fluidintetweaker.common.api.event.CustomFluidInteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.impl.task.SetBlockStateTask;
import com.tttsaurus.fluidintetweaker.common.impl.task.TaskScheduler;
import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidInteractionDelegate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
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
                if (world.isRemote) return;
                for (InteractionEvent event: events)
                {
                    List<IEventCondition> conditions = event.getConditions();
                    boolean flag = true;
                    for (IEventCondition condition : conditions)
                    {
                        flag = condition.judge(fluidInteractionEvent);
                        if (!flag) break;
                    }
                    if (!flag) continue;

                    if (event.getSoundEvent() != null)
                        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), event.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (event.getParticleType() != null)
                        for (int i = 0; i < 10; i++)
                            world.spawnParticle(event.getParticleType(),
                                    pos.getX() + world.rand.nextFloat(), pos.getY(), pos.getZ() + world.rand.nextFloat(),
                                    0.0D, 0.1D, 0.0D);

                    InteractionEventType eventType = event.getEventType();
                    if (eventType == InteractionEventType.SetBlock)
                    {
                        world.setBlockState(pos, event.getBlockState());
                    }
                    else if (eventType == InteractionEventType.Explosion)
                    {
                        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), event.getStrength(), event.getDamagesTerrain()).doExplosionB(event.getHasParticles());
                    }
                    else if (eventType == InteractionEventType.SpawnEntity)
                    {
                        Entity entity = event.getEntityEntry().newInstance(world);
                        entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
                        world.spawnEntity(entity);
                    }
                    else if (eventType == InteractionEventType.SpawnEntityItem)
                    {
                        EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), event.getItemStack().copy());
                        world.spawnEntity(entityItem);
                    }
                    else if (eventType == InteractionEventType.SetFluid)
                    {
                        Fluid fluid = event.getFluid();
                        String fluidName = fluid.getName();
                        IBlockState blockState = fluid.getBlock().getDefaultState();

                        // check fluid spreading recipe
                        WorldIngredient ingredientB = fluidInteractionEvent.getIngredientB();
                        if (ingredientB.getIngredientType() == WorldIngredientType.FLUID &&
                            ingredientB.getFluid().getName().equals(fluidName) &&
                            (event.getIsSpreadingUpward() || !fluidInteractionEvent.getIsInitiatorAbove()))
                        {
                            // todo: spreading limit
                            /*
                            IBlockState blockStateSurrounding = ingredientB.getBlockState();

                            int spreadCounter = 0;
                            if (blockStateSurrounding.getPropertyKeys().contains(FluidSpreadHelper.FLUID_SPREAD_COUNTER))
                                spreadCounter = blockStateSurrounding.getValue(FluidSpreadHelper.FLUID_SPREAD_COUNTER) + 1;

                            if (spreadCounter < 16)
                            {
                                blockState = FluidSpreadHelper.getSpreadCountableFluid(fluid);
                                blockState = blockState.withProperty(FluidSpreadHelper.FLUID_SPREAD_COUNTER, spreadCounter);

                                boolean goUp = event.getIsSpreadingUpward() && fluidInteractionEvent.getIsInitiatorAbove();
                                BlockPos finalPos = goUp ? pos.add(0, 1, 0) : pos;
                                int flags = goUp ? 3 : 2;
                                TaskScheduler.scheduleTask(new SetBlockStateTask(10, world, finalPos, blockState, flags));
                            }
                            else
                                world.setBlockState(pos, event.getLimitBarrier(), 2);
                            */

                            boolean goUp = event.getIsSpreadingUpward() && fluidInteractionEvent.getIsInitiatorAbove();
                            BlockPos finalPos = goUp ? pos.add(0, 1, 0) : pos;
                            TaskScheduler.scheduleTask(new SetBlockStateTask(10, world, finalPos, blockState, 3));
                        }
                        else
                            world.setBlockState(pos, blockState);
                    }
                }
            }
        };
    }
}
