package com.tttsaurus.fluidintetweaker.client.jefi.impl;

import com.tttsaurus.fluidintetweaker.Configuration;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.client.jefi.JustEnoughFluidInteractions;
import com.tttsaurus.fluidintetweaker.common.api.interaction.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionEvent;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionIngredientType;
import com.tttsaurus.fluidintetweaker.common.api.interaction.condition.IsInitiatorAbove;
import com.tttsaurus.fluidintetweaker.common.api.util.BlockUtils;
import com.tttsaurus.fluidintetweaker.common.api.util.StringRecipeProtocol;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import java.util.*;

@SideOnly(Side.CLIENT)
@JEIPlugin
public class JEFIPlugin implements IModPlugin
{
    private static final LinkedHashMap<String, JEFIRecipeWrapper> recipeWrapperDict = new LinkedHashMap<>();

    private static void addRecipeWrapper(InteractionIngredient ingredientA, boolean isAnyFluidStateA, InteractionIngredient ingredientB, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredientA, isAnyFluidStateA, ingredientB, complexOutput, null);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, boolean isAnyFluidStateA, InteractionIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        addRecipeWrapper(ingredientA, isAnyFluidStateA, ingredientB, false, complexOutput, extraInfoLocalizationKey);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, InteractionIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredientA, ingredientB, isAnyFluidStateB, complexOutput, null);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, InteractionIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        addRecipeWrapper(ingredientA, false, ingredientB, isAnyFluidStateB, complexOutput, extraInfoLocalizationKey);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, boolean isAnyFluidStateA, InteractionIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredientA, isAnyFluidStateA, ingredientB, isAnyFluidStateB, complexOutput, null);
    }
    private static void addRecipeWrapper(InteractionIngredient ingredientA, boolean isAnyFluidStateA, InteractionIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        JEFIRecipeWrapper recipeWrapper = new JEFIRecipeWrapper(ingredientA, ingredientB, complexOutput, extraInfoLocalizationKey);
        recipeWrapper.isAnyFluidStateA = isAnyFluidStateA;
        recipeWrapper.isAnyFluidStateB = isAnyFluidStateB;

        recipeWrapperDict.put(StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB), recipeWrapper);
    }

    public static void addRecipeWrapper(String recipeKey, InteractionIngredient ingredientA, InteractionIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        JEFIRecipeWrapper recipeWrapper = new JEFIRecipeWrapper(ingredientA, ingredientB, complexOutput, extraInfoLocalizationKey);
        recipeWrapperDict.put(recipeKey, recipeWrapper);

        //<editor-fold desc="combine `source` and `flowing` if they both exist">
        if (ingredientA.getIngredientType() == InteractionIngredientType.FLUID)
        {
            ingredientA.setIsFluidSource(!ingredientA.getIsFluidSource());
            String newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
            if (recipeWrapperDict.containsKey(newKey) &&
                recipeWrapperDict.get(newKey).complexOutput.equals(complexOutput))
            {
                recipeWrapperDict.remove(newKey);
                recipeWrapper.isAnyFluidStateA = true;
            }
            ingredientA.setIsFluidSource(!ingredientA.getIsFluidSource());
        }

        if (ingredientB.getIngredientType() == InteractionIngredientType.FLUID)
        {
            ingredientB.setIsFluidSource(!ingredientB.getIsFluidSource());
            String newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
            if (recipeWrapperDict.containsKey(newKey) &&
                recipeWrapperDict.get(newKey).complexOutput.equals(complexOutput))
            {
                recipeWrapperDict.remove(newKey);
                recipeWrapper.isAnyFluidStateB = true;
            }
            ingredientB.setIsFluidSource(!ingredientB.getIsFluidSource());
        }
        //</editor-fold>
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    public void register(@NotNull IModRegistry registry)
    {
        if (Configuration.enableLavaAndWaterRecipeInJEI)
        {
            addRecipeWrapper(InteractionIngredient.SOURCE_LAVA, InteractionIngredient.FLOWING_WATER, true, ComplexOutput.createSimpleBlockOutput(Blocks.OBSIDIAN.getDefaultState()));
            addRecipeWrapper(InteractionIngredient.FLOWING_LAVA, InteractionIngredient.FLOWING_WATER, true, ComplexOutput.create().addEvent(InteractionEvent.createSetBlockEvent(Blocks.COBBLESTONE.getDefaultState())).addEvent(InteractionEvent.createSetBlockEvent(Blocks.STONE.getDefaultState()).addCondition(new IsInitiatorAbove())));
        }
        if (Configuration.enableThermalFoundationJEICompat && FluidInteractionTweaker.IS_THERMALFOUNDATION_LOADED)
        {
            /*
                Primal Mana Todos:
                - entities that come into contact with primal mana may be teleported to a random destination in a radius of 8 blocks
                - adjacent blocks are set on fire or covered with snow layers
                - redstone ore lights up
            */
            InteractionIngredient FLOWING_MANA = new InteractionIngredient(FluidRegistry.getFluid("mana"), false);
            addRecipeWrapper(new InteractionIngredient(Blocks.DIRT.getDefaultState()), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRASS_PATH.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.DIRT.getStateFromMeta(1)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRASS_PATH.getStateFromMeta(1)));
            addRecipeWrapper(new InteractionIngredient(Blocks.FARMLAND.getDefaultState()), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.MYCELIUM.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.GLASS.getDefaultState()), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.SAND.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.LAPIS_ORE.getDefaultState()), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.LAPIS_BLOCK.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(BlockUtils.getBlockState("thermalfoundation:ore", 2)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(BlockUtils.getBlockState("thermalfoundation:ore", 8)));
            addRecipeWrapper(new InteractionIngredient(BlockUtils.getBlockState("thermalfoundation:ore", 3)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.GOLD_ORE.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(BlockUtils.getBlockState("thermalfoundation:storage", 2)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(BlockUtils.getBlockState("thermalfoundation:storage", 8)));
            addRecipeWrapper(new InteractionIngredient(BlockUtils.getBlockState("thermalfoundation:storage", 3)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.GOLD_BLOCK.getDefaultState()));

            /*
                Pyrotheum Todos:
                - instantly starting fires on top of every adjacent block
                - flammable blocks are instantly destroyed
                - creepers instantly explode
            */
            InteractionIngredient FLOWING_PYROTHEUM = new InteractionIngredient(FluidRegistry.getFluid("pyrotheum"), false);
            addRecipeWrapper(new InteractionIngredient(Blocks.COBBLESTONE.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.GRASS.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.DIRT.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.SAND.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GLASS.getDefaultState()));
            addRecipeWrapper(InteractionIngredient.FLOWING_WATER, true, FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.ICE.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.CLAY.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.HARDENED_CLAY.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.SNOW.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.AIR.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.SNOW_LAYER.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.AIR.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.STONE_STAIRS.getStateFromMeta(0)), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE_BRICK_STAIRS.getStateFromMeta(0)));

            /*
                Cryotheum Todos:
                - adjacent blocks are covered with snow layers
                - grass and leaves are instantly destroyed
                - fire is extinguished
                - zombies and creepers are turned into snow golems
                - blazes take 10 damage instead of 2
                - snow golems and blizzes are given the effects Speed I and Regeneration I for 6 seconds
            */
            InteractionIngredient FLOWING_CRYOTHEUM = new InteractionIngredient(FluidRegistry.getFluid("cryotheum"), false);
            addRecipeWrapper(new InteractionIngredient(Blocks.GRASS.getDefaultState()), FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.DIRT.getDefaultState()));
            addRecipeWrapper(InteractionIngredient.SOURCE_WATER, FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.ICE.getDefaultState()));
            addRecipeWrapper(InteractionIngredient.FLOWING_WATER, FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.SNOW.getDefaultState()));
            addRecipeWrapper(InteractionIngredient.SOURCE_LAVA, FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.OBSIDIAN.getDefaultState()));
            addRecipeWrapper(InteractionIngredient.FLOWING_LAVA, FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(FluidRegistry.getFluid("glowstone"), true), FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GLOWSTONE.getDefaultState()));

            /*
                Petrotheum Todos:
                - when touched by players and mobs, tectonic petrotheum applies the effect Haste I to them for 6 seconds
                - if enabled, tectonic petrotheum breaks any adjacent stone- or rock-like blocks. This is disabled by default
            */
            InteractionIngredient FLOWING_PETROTHEUM = new InteractionIngredient(FluidRegistry.getFluid("petrotheum"), false);
            addRecipeWrapper(new InteractionIngredient(Blocks.STONE.getDefaultState()), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.COBBLESTONE.getDefaultState()), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.STONEBRICK.getDefaultState()), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.STONEBRICK.getStateFromMeta(1)), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.STONEBRICK.getStateFromMeta(2)), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.STONEBRICK.getStateFromMeta(3)), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
            addRecipeWrapper(new InteractionIngredient(Blocks.MOSSY_COBBLESTONE.getDefaultState()), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
        }
        if (Configuration.enableBiomesOPlentyJEICompat && FluidInteractionTweaker.IS_BIOMESOPLENTY_LOADED)
        {
            /*
            InteractionIngredient FLOWING_BLOOD = new InteractionIngredient(FluidRegistry.getFluid("blood"), false);
            addRecipeWrapper(FLOWING_BLOOD, true, InteractionIngredient.FLOWING_WATER, true, ComplexOutput.createSimpleBlockOutput(BlockUtils.getBlockState("biomesoplenty:flesh")));
            addRecipeWrapper(FLOWING_BLOOD, true, InteractionIngredient.FLOWING_LAVA, true, ComplexOutput.createSimpleBlockOutput(BlockUtils.getBlockState("biomesoplenty:flesh")));
            addRecipeWrapper(InteractionIngredient.FLOWING_LAVA, FLOWING_BLOOD, true, ComplexOutput.createSimpleBlockOutput(Blocks.COBBLESTONE.getDefaultState()));
            addRecipeWrapper(InteractionIngredient.SOURCE_LAVA, FLOWING_BLOOD, true, ComplexOutput.createSimpleBlockOutput(Blocks.OBSIDIAN.getDefaultState()));
            addRecipeWrapper(FLOWING_BLOOD, true, new InteractionIngredient(FluidRegistry.getFluid("sand"), false), true, ComplexOutput.createSimpleBlockOutput(BlockUtils.getBlockState("biomesoplenty:flesh")));
            addRecipeWrapper(new InteractionIngredient(FluidRegistry.getFluid("sand"), false), true, FLOWING_BLOOD, true, ComplexOutput.createSimpleBlockOutput(BlockUtils.getBlockState("biomesoplenty:mud")));
            addRecipeWrapper(FLOWING_BLOOD, true, new InteractionIngredient(FluidRegistry.getFluid("hot_spring_water"), false), true, ComplexOutput.createSimpleBlockOutput(BlockUtils.getBlockState("biomesoplenty:flesh")));
            addRecipeWrapper(new InteractionIngredient(FluidRegistry.getFluid("hot_spring_water"), false), true, FLOWING_BLOOD, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE.getDefaultState()));
            */
        }

        registry.addRecipes(recipeWrapperDict.values(), JustEnoughFluidInteractions.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new JEFICategory(registry.getJeiHelpers().getGuiHelper()));
    }
}
