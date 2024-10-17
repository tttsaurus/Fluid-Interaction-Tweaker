package com.tttsaurus.fluidintetweaker.common.api.behavior;

import com.tttsaurus.fluidintetweaker.common.api.behavior.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.api.util.BlockUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import java.util.ArrayList;
import java.util.List;

public class BehaviorEvent
{
    private final BehaviorEventType eventType;
    private final List<IEventCondition> conditions = new ArrayList<>();
    public BehaviorEventType getEventType() { return eventType; }
    public List<IEventCondition> getConditions() { return conditions; }

    // BehaviorEventType.PotionEffect
    private PotionEffect potionEffect;
    public PotionEffect getPotionEffect() { return potionEffect; }

    // BehaviorEventType.EntityConversion
    private String entityIDFrom;
    private EntityEntry entityEntryTo;
    public String getEntityIDFrom() { return entityIDFrom; }
    public EntityEntry getEntityEntryTo() { return entityEntryTo; }

    // BehaviorEventType.ExtinguishFire

    // BehaviorEventType.SetFire

    // BehaviorEventType.SetSnow

    // BehaviorEventType.Teleport
    private int xRange;
    private int yRange;
    public int getXRange() { return xRange; }
    public int getYRange() { return yRange; }

    // BehaviorEventType.BreakSurrounding
    private List<IBlockState> blockStates;
    public List<IBlockState> getBlockStates() { return blockStates; }

    private BehaviorEvent(BehaviorEventType eventType) { this.eventType = eventType; }

    public static BehaviorEvent createPotionEffectEvent(String id, int duration, int amplifier)
    {
        BehaviorEvent event = new BehaviorEvent(BehaviorEventType.PotionEffect);
        event.potionEffect = new PotionEffect(Potion.getPotionFromResourceLocation(id), duration, amplifier);
        return event;
    }
    public static BehaviorEvent createEntityConversionEvent(String idFrom, String idTo)
    {
        BehaviorEvent event = new BehaviorEvent(BehaviorEventType.EntityConversion);
        event.entityIDFrom = idFrom;
        event.entityEntryTo = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(idTo));
        return event;
    }
    public static BehaviorEvent createExtinguishFireEvent()
    {
        BehaviorEvent event = new BehaviorEvent(BehaviorEventType.ExtinguishFire);
        return event;
    }
    public static BehaviorEvent createSetFireEvent()
    {
        BehaviorEvent event = new BehaviorEvent(BehaviorEventType.SetFire);
        return event;
    }
    public static BehaviorEvent createSetSnowEvent()
    {
        BehaviorEvent event = new BehaviorEvent(BehaviorEventType.SetSnow);
        return event;
    }
    public static BehaviorEvent createTeleportEvent(int xRange, int yRange)
    {
        BehaviorEvent event = new BehaviorEvent(BehaviorEventType.Teleport);
        event.xRange = xRange;
        event.yRange = yRange;
        return event;
    }
    public static BehaviorEvent createBreakSurroundingEvent(Ingredient ingredient)
    {
        BehaviorEvent event = new BehaviorEvent(BehaviorEventType.BreakSurrounding);
        event.blockStates = new ArrayList<>();
        for (ItemStack itemStack: ingredient.getMatchingStacks())
        {
            event.blockStates.add(BlockUtils.getBlockState(itemStack));
        }
        return event;
    }
    public BehaviorEvent addCondition(IEventCondition condition)
    {
        conditions.add(condition);
        return this;
    }
}