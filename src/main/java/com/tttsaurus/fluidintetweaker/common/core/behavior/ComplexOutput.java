package com.tttsaurus.fluidintetweaker.common.core.behavior;

import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.core.WorldIngredientType;
import com.tttsaurus.fluidintetweaker.common.core.behavior.condition.IEventCondition;
import com.tttsaurus.fluidintetweaker.common.core.event.CustomFluidBehaviorEvent;
import com.tttsaurus.fluidintetweaker.common.impl.delegate.FluidBehaviorDelegate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.EnumSet;
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

    private static final EnumSet<EnumFacing> surrounding = EnumSet.of(EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST);
    public FluidBehaviorDelegate getOutputDelegate(CustomFluidBehaviorEvent fluidBehaviorEvent)
    {
        World world = fluidBehaviorEvent.getWorld();
        BlockPos pos = fluidBehaviorEvent.getPos();
        EntityLivingBase entityLivingBase = fluidBehaviorEvent.getEntityLivingBase();
        return new FluidBehaviorDelegate()
        {
            @Override
            public void doAction()
            {
                if (world.isRemote) return;
                for (BehaviorEvent event: events)
                {
                    List<IEventCondition> conditions = event.getConditions();
                    boolean flag = true;
                    for (IEventCondition condition : conditions)
                    {
                        flag = condition.judge(fluidBehaviorEvent);
                        if (!flag) break;
                    }
                    if (!flag) continue;

                    BehaviorEventType eventType = event.getEventType();
                    if (eventType == BehaviorEventType.PotionEffect)
                    {
                        if (entityLivingBase == null) continue;

                        PotionEffect potionEffect = event.getPotionEffect();
                        entityLivingBase.addPotionEffect(new PotionEffect(potionEffect.getPotion(), potionEffect.getDuration(), potionEffect.getAmplifier()));
                    }
                    else if (eventType == BehaviorEventType.EntityConversion)
                    {
                        if (entityLivingBase == null) continue;
                        ResourceLocation rl = EntityList.getKey(entityLivingBase);
                        if (rl != null)
                        {
                            if (rl.toString().equals(event.getEntityIDFrom()))
                            {
                                double x = entityLivingBase.prevPosX;
                                double y = entityLivingBase.prevPosY;
                                double z = entityLivingBase.prevPosZ;
                                entityLivingBase.setDead();
                                Entity entity = event.getEntityEntryTo().newInstance(world);
                                entity.setPosition(x, y, z);
                                world.spawnEntity(entity);
                            }
                        }
                    }
                    else if (eventType == BehaviorEventType.ExtinguishFire)
                    {
                        for (EnumFacing facing : surrounding)
                        {
                            Vec3i vec3 = facing.getDirectionVec();
                            int x = vec3.getX();
                            int y = vec3.getY();
                            int z = vec3.getZ();

                            BlockPos neighborPos1 = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            if (world.getBlockState(neighborPos1).getBlock() == Blocks.FIRE)
                                world.setBlockToAir(neighborPos1);
                            BlockPos neighborPos2 = new BlockPos(pos.getX() + x, pos.getY() + y + 1, pos.getZ() + z);
                            if (world.getBlockState(neighborPos2).getBlock() == Blocks.FIRE)
                                world.setBlockToAir(neighborPos2);
                        }
                    }
                    else if (eventType == BehaviorEventType.SetFire)
                    {
                        for (EnumFacing facing : surrounding)
                        {
                            Vec3i vec3 = facing.getDirectionVec();
                            int x = vec3.getX();
                            int y = vec3.getY();
                            int z = vec3.getZ();

                            BlockPos neighborPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            BlockPos upperNeighborPos = new BlockPos(pos.getX() + x, pos.getY() + y + 1, pos.getZ() + z);
                            WorldIngredient neighbor = WorldIngredient.getFrom(world, neighborPos);
                            if (neighbor != WorldIngredient.AIR &&
                                    neighbor.getIngredientType() == WorldIngredientType.BLOCK &&
                                    world.getBlockState(upperNeighborPos).getBlock() == Blocks.AIR &&
                                    world.getBlockState(neighborPos).getBlock() != Blocks.FIRE)
                                world.setBlockState(upperNeighborPos, Blocks.FIRE.getDefaultState());
                        }
                    }
                    else if (eventType == BehaviorEventType.SetSnow)
                    {
                        for (EnumFacing facing : surrounding)
                        {
                            Vec3i vec3 = facing.getDirectionVec();
                            int x = vec3.getX();
                            int y = vec3.getY();
                            int z = vec3.getZ();

                            BlockPos neighborPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            BlockPos upperNeighborPos = new BlockPos(pos.getX() + x, pos.getY() + y + 1, pos.getZ() + z);
                            WorldIngredient neighbor = WorldIngredient.getFrom(world, neighborPos);
                            if (neighbor != WorldIngredient.AIR &&
                                    neighbor.getIngredientType() == WorldIngredientType.BLOCK &&
                                    world.getBlockState(upperNeighborPos).getBlock() == Blocks.AIR &&
                                    world.getBlockState(neighborPos).getBlock() != Blocks.SNOW_LAYER)
                                world.setBlockState(upperNeighborPos, Blocks.SNOW_LAYER.getDefaultState());
                        }
                    }
                    else if (eventType == BehaviorEventType.Teleport)
                    {
                        if (entityLivingBase == null) continue;
                        float xRange = (float)event.getXRange();
                        float yRange = (float)event.getYRange();
                        float x = world.rand.nextFloat() * xRange - xRange / 2f;
                        float y = world.rand.nextFloat() * xRange - yRange / 2f;
                        if (entityLivingBase.attemptTeleport(x, y, entityLivingBase.posZ))
                            entityLivingBase.setPosition(x, y, entityLivingBase.posZ);
                    }
                    else if (eventType == BehaviorEventType.BreakSurrounding)
                    {
                        for (EnumFacing facing : surrounding)
                        {
                            Vec3i vec3 = facing.getDirectionVec();
                            int x = vec3.getX();
                            int y = vec3.getY();
                            int z = vec3.getZ();

                            BlockPos neighborPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            String neighbor = WorldIngredient.getFrom(world, neighborPos).toString();
                            for (IBlockState blockState: event.getBlockStates())
                            {
                                String target = (new WorldIngredient(blockState)).toString();
                                if (neighbor.equals(target))
                                    world.setBlockToAir(neighborPos);
                            }
                        }
                    }
                }
            }
        };
    }
}
