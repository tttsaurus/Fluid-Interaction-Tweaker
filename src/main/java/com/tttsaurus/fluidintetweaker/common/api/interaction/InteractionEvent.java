package com.tttsaurus.fluidintetweaker.common.api.interaction;

import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class InteractionEvent
{
    private final InteractionEventType eventType;
    private final List<IEventCondition> conditions = new ArrayList<>();
    public InteractionEventType getEventType() { return eventType; }
    public List<IEventCondition> getConditions() { return conditions; }

    // InteractionEventType.SetBlock
    private IBlockState blockState;
    public IBlockState getBlockState() { return blockState; }

    // InteractionEventType.Explosion
    private float strength;
    private boolean damagesTerrain;
    public float getStrength() { return strength; }
    public boolean getDamagesTerrain() { return damagesTerrain; }

    // InteractionEventType.SpawnEntity
    private EntityEntry entityEntry;
    public EntityEntry getEntityEntry() { return entityEntry; }

    // InteractionEventType.SpawnEntityItem
    private ItemStack itemStack;
    public ItemStack getItemStack() { return itemStack; }

    // InteractionEventType.SetFluid
    private Fluid fluid;
    private boolean isSpreadingUpward = false;
    //private IBlockState limitBarrier = Blocks.STONE.getDefaultState();
    public Fluid getFluid() { return fluid; }
    public boolean getIsSpreadingUpward() { return isSpreadingUpward; }
    //public IBlockState getLimitBarrier() { return limitBarrier; }

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
    public static InteractionEvent createExplosionEvent(float strength, boolean damagesTerrain)
    {
        InteractionEvent event = new InteractionEvent(InteractionEventType.Explosion);
        event.strength = strength;
        event.damagesTerrain = damagesTerrain;
        return event;
    }
    public static InteractionEvent createSpawnEntityEvent(String id)
    {
        InteractionEvent event = new InteractionEvent(InteractionEventType.SpawnEntity);
        event.entityEntry = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(id));
        return event;
    }
    public static InteractionEvent createSpawnEntityEvent(EntityEntry entityEntry)
    {
        InteractionEvent event = new InteractionEvent(InteractionEventType.SpawnEntity);
        event.entityEntry = entityEntry;
        return event;
    }
    public static InteractionEvent createSpawnEntityItemEvent(ItemStack itemStack, int amount)
    {
        InteractionEvent event = new InteractionEvent(InteractionEventType.SpawnEntityItem);
        itemStack.setCount(amount);
        event.itemStack = itemStack.copy();
        return event;
    }
    public static InteractionEvent createSetFluidEvent(Fluid fluid, boolean isSpreadingUpward/*, IBlockState limitBarrier*/)
    {
        InteractionEvent event = new InteractionEvent(InteractionEventType.SetFluid);
        event.fluid = fluid;
        event.isSpreadingUpward = isSpreadingUpward;
        //event.limitBarrier = limitBarrier;
        return event;
    }
    public static InteractionEvent createSetFluidEvent(Fluid fluid)
    {
        InteractionEvent event = new InteractionEvent(InteractionEventType.SetFluid);
        event.fluid = fluid;
        return event;
    }
    public InteractionEvent addCondition(IEventCondition condition)
    {
        conditions.add(condition);
        return this;
    }

    private SoundEvent soundEvent = null;
    public SoundEvent getSoundEvent() { return soundEvent; }
    private EnumParticleTypes particleType = null;
    public EnumParticleTypes getParticleType() { return particleType; }

    public InteractionEvent setSoundEvent(SoundEvent soundEvent)
    {
        this.soundEvent = soundEvent;
        return this;
    }
    public InteractionEvent setParticleType(EnumParticleTypes particleType)
    {
        this.particleType = particleType;
        return this;
    }
}
