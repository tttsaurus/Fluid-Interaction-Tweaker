package com.tttsaurus.fluidintetweaker.common.api.interaction;

import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IEventCondition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
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
    public static InteractionEvent createSpawnEntityItemEvent(ItemStack itemStack)
    {
        InteractionEvent event = new InteractionEvent(InteractionEventType.SpawnEntityItem);
        event.itemStack = itemStack;
        return event;
    }
    public InteractionEvent addCondition(IEventCondition condition)
    {
        conditions.add(condition);
        return this;
    }
}
