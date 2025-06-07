package com.tttsaurus.fluidintetweaker.common.core.event;

import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.core.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.core.delegate.IFluidBehaviorDelegate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class CustomFluidBehaviorEvent extends WorldEvent
{
    private final BlockPos pos;
    private final EntityLivingBase entityLivingBase;
    private final WorldIngredient ingredient;
    private final ComplexOutput complexOutput;
    private final IFluidBehaviorDelegate delegate;

    public BlockPos getPos() { return pos; }
    public EntityLivingBase getEntityLivingBase() { return entityLivingBase; }
    public WorldIngredient getIngredient() { return ingredient; }
    public ComplexOutput getComplexOutput() { return complexOutput; }
    public IFluidBehaviorDelegate getDelegate() { return delegate; }
    public String getFluidBehaviorRecipeKey() { return ingredient.toString(); }

    public CustomFluidBehaviorEvent(World world, BlockPos pos, EntityLivingBase entityLivingBase, WorldIngredient ingredient, ComplexOutput complexOutput)
    {
        super(world);
        this.pos = pos;
        this.entityLivingBase = entityLivingBase;
        this.ingredient = ingredient;
        this.complexOutput = complexOutput;
        this.delegate = complexOutput.getOutputDelegate(this);
    }
}
