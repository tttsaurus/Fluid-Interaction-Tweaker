package com.tttsaurus.fluidintetweaker.client.impl.jefi;

import com.tttsaurus.fluidintetweaker.Configuration;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.common.core.interaction.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.core.interaction.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.core.interaction.InteractionEvent;
import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.core.WorldIngredientType;
import com.tttsaurus.fluidintetweaker.common.impl.interaction.condition.IsInitiatorAbove;
import com.tttsaurus.fluidintetweaker.common.core.util.BlockUtils;
import com.tttsaurus.fluidintetweaker.common.core.interaction.StringRecipeProtocol;
import com.tttsaurus.ometweaks.OMEConfig;
import com.tttsaurus.ometweaks.integration.thermalfoundation.ThermalFoundationModule;
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

@SuppressWarnings("all")
@SideOnly(Side.CLIENT)
@JEIPlugin
public class JEFIPlugin implements IModPlugin
{
    private static final LinkedHashMap<String, JEFIRecipeWrapper> recipeWrapperDict = new LinkedHashMap<>();

    //<editor-fold desc="adding recipe wrappers manually">
    public static void addRecipeWrapper(WorldIngredient ingredientA, boolean isAnyFluidStateA, WorldIngredient ingredientB, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredientA, isAnyFluidStateA, ingredientB, complexOutput, null);
    }
    public static void addRecipeWrapper(WorldIngredient ingredientA, boolean isAnyFluidStateA, WorldIngredient ingredientB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        addRecipeWrapper(ingredientA, isAnyFluidStateA, ingredientB, false, complexOutput, extraInfoLocalizationKey);
    }
    public static void addRecipeWrapper(WorldIngredient ingredientA, WorldIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredientA, ingredientB, isAnyFluidStateB, complexOutput, null);
    }
    public static void addRecipeWrapper(WorldIngredient ingredientA, WorldIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        addRecipeWrapper(ingredientA, false, ingredientB, isAnyFluidStateB, complexOutput, extraInfoLocalizationKey);
    }
    public static void addRecipeWrapper(WorldIngredient ingredientA, boolean isAnyFluidStateA, WorldIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredientA, isAnyFluidStateA, ingredientB, isAnyFluidStateB, complexOutput, null);
    }
    public static void addRecipeWrapper(WorldIngredient ingredientA, boolean isAnyFluidStateA, WorldIngredient ingredientB, boolean isAnyFluidStateB, ComplexOutput complexOutput, String extraInfoLocalizationKey)
    {
        JEFIRecipeWrapper recipeWrapper = new JEFIRecipeWrapper(new FluidInteractionRecipe(ingredientA, ingredientB, complexOutput, extraInfoLocalizationKey));
        recipeWrapper.isAnyFluidStateA = isAnyFluidStateA;
        recipeWrapper.isAnyFluidStateB = isAnyFluidStateB;

        String key = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(ingredientA, ingredientB);
        if (recipeWrapperDict.containsKey(key))
            key += recipeWrapper.hashCode();
        recipeWrapperDict.put(key, recipeWrapper);
    }
    //</editor-fold>

    // this is an internal method
    public static void addRecipeWrapper(FluidInteractionRecipe recipe)
    {
        String recipeKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(recipe.ingredientA, recipe.ingredientB);
        JEFIRecipeWrapper recipeWrapper = new JEFIRecipeWrapper(recipe);
        recipeWrapperDict.put(recipeKey, recipeWrapper);

        //<editor-fold desc="combine `source` and `flowing` if they both exist">
        if (recipe.ingredientA.getIngredientType() == WorldIngredientType.FLUID)
        {
            recipe.ingredientA.setIsFluidSource(!recipe.ingredientA.getIsFluidSource());
            String newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(recipe.ingredientA, recipe.ingredientB);
            if (recipeWrapperDict.containsKey(newKey) &&
                recipeWrapperDict.get(newKey).recipe.complexOutput.equals(recipe.complexOutput))
            {
                recipeWrapperDict.remove(newKey);
                recipeWrapper.isAnyFluidStateA = true;
            }
            recipe.ingredientA.setIsFluidSource(!recipe.ingredientA.getIsFluidSource());
        }

        if (recipe.ingredientB.getIngredientType() == WorldIngredientType.FLUID)
        {
            recipe.ingredientB.setIsFluidSource(!recipe.ingredientB.getIsFluidSource());
            String newKey = StringRecipeProtocol.getRecipeKeyFromTwoIngredients(recipe.ingredientA, recipe.ingredientB);
            if (recipeWrapperDict.containsKey(newKey) &&
                recipeWrapperDict.get(newKey).recipe.complexOutput.equals(recipe.complexOutput))
            {
                recipeWrapperDict.remove(newKey);
                recipeWrapper.isAnyFluidStateB = true;
            }
            recipe.ingredientB.setIsFluidSource(!recipe.ingredientB.getIsFluidSource());
        }
        //</editor-fold>
    }

    @Override
    public void register(@NotNull IModRegistry registry)
    {
        if (Configuration.enableLavaAndWaterRecipeInJEI)
        {
            addRecipeWrapper(WorldIngredient.SOURCE_LAVA, WorldIngredient.FLOWING_WATER, true, ComplexOutput.createSimpleBlockOutput(Blocks.OBSIDIAN.getDefaultState()));
            addRecipeWrapper(WorldIngredient.FLOWING_LAVA, WorldIngredient.FLOWING_WATER, true, ComplexOutput.create().addEvent(InteractionEvent.createSetBlockEvent(Blocks.COBBLESTONE.getDefaultState())).addEvent(InteractionEvent.createSetBlockEvent(Blocks.STONE.getDefaultState()).addCondition(new IsInitiatorAbove())));
        }
        if (Configuration.enableThermalFoundationJEICompat && FluidInteractionTweaker.IS_THERMALFOUNDATION_LOADED)
        {
            boolean enableMana = true;
            boolean enablePyrotheum = true;
            boolean enableCryotheum = true;
            boolean enablePetrotheum = true;

            if (FluidInteractionTweaker.IS_OMETWEAKS_LOADED)
            {
                if (OMEConfig.ENABLE && ThermalFoundationModule.ENABLE_TF_MODULE)
                {
                    if (ThermalFoundationModule.DISABLE_TF_MANA_INTERACTIONS) enableMana = false;
                    if (ThermalFoundationModule.DISABLE_TF_PYROTHEUM_INTERACTIONS) enablePyrotheum = false;
                    if (ThermalFoundationModule.DISABLE_TF_CRYOTHEUM_INTERACTIONS) enableCryotheum = false;
                    if (ThermalFoundationModule.DISABLE_TF_PETROTHEUM_INTERACTIONS) enablePetrotheum = false;
                }
            }

            if (enableMana)
            {
                WorldIngredient FLOWING_MANA = new WorldIngredient(FluidRegistry.getFluid("mana"), false);
                addRecipeWrapper(new WorldIngredient(Blocks.DIRT.getDefaultState()), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRASS_PATH.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.DIRT.getStateFromMeta(1)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRASS_PATH.getStateFromMeta(1)));
                addRecipeWrapper(new WorldIngredient(Blocks.FARMLAND.getDefaultState()), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.MYCELIUM.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.GLASS.getDefaultState()), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.SAND.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.LAPIS_ORE.getDefaultState()), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.LAPIS_BLOCK.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(BlockUtils.getBlockState("thermalfoundation:ore", 2)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(BlockUtils.getBlockState("thermalfoundation:ore", 8)));
                addRecipeWrapper(new WorldIngredient(BlockUtils.getBlockState("thermalfoundation:ore", 3)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.GOLD_ORE.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(BlockUtils.getBlockState("thermalfoundation:storage", 2)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(BlockUtils.getBlockState("thermalfoundation:storage", 8)));
                addRecipeWrapper(new WorldIngredient(BlockUtils.getBlockState("thermalfoundation:storage", 3)), FLOWING_MANA, true, ComplexOutput.createSimpleBlockOutput(Blocks.GOLD_BLOCK.getDefaultState()));
            }
            if (enablePyrotheum)
            {
                WorldIngredient FLOWING_PYROTHEUM = new WorldIngredient(FluidRegistry.getFluid("pyrotheum"), false);
                addRecipeWrapper(new WorldIngredient(Blocks.COBBLESTONE.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.GRASS.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.DIRT.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.SAND.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GLASS.getDefaultState()));
                addRecipeWrapper(WorldIngredient.FLOWING_WATER, true, FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.ICE.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.CLAY.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.HARDENED_CLAY.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.SNOW.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.AIR.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.SNOW_LAYER.getDefaultState()), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.AIR.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.STONE_STAIRS.getStateFromMeta(0)), FLOWING_PYROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE_BRICK_STAIRS.getStateFromMeta(0)));
            }
            if (enableCryotheum)
            {
                WorldIngredient FLOWING_CRYOTHEUM = new WorldIngredient(FluidRegistry.getFluid("cryotheum"), false);
                addRecipeWrapper(new WorldIngredient(Blocks.GRASS.getDefaultState()), FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.DIRT.getDefaultState()));
                addRecipeWrapper(WorldIngredient.SOURCE_WATER, FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.ICE.getDefaultState()));
                addRecipeWrapper(WorldIngredient.FLOWING_WATER, FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.SNOW.getDefaultState()));
                addRecipeWrapper(WorldIngredient.SOURCE_LAVA, FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.OBSIDIAN.getDefaultState()));
                addRecipeWrapper(WorldIngredient.FLOWING_LAVA, FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.STONE.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(FluidRegistry.getFluid("glowstone"), true), FLOWING_CRYOTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GLOWSTONE.getDefaultState()));
            }
            if (enablePetrotheum)
            {
                WorldIngredient FLOWING_PETROTHEUM = new WorldIngredient(FluidRegistry.getFluid("petrotheum"), false);
                addRecipeWrapper(new WorldIngredient(Blocks.STONE.getDefaultState()), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.COBBLESTONE.getDefaultState()), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.STONEBRICK.getDefaultState()), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.STONEBRICK.getStateFromMeta(1)), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.STONEBRICK.getStateFromMeta(2)), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.STONEBRICK.getStateFromMeta(3)), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
                addRecipeWrapper(new WorldIngredient(Blocks.MOSSY_COBBLESTONE.getDefaultState()), FLOWING_PETROTHEUM, true, ComplexOutput.createSimpleBlockOutput(Blocks.GRAVEL.getDefaultState()));
            }
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
