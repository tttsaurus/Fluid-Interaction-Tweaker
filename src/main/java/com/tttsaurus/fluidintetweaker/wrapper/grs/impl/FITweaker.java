package com.tttsaurus.fluidintetweaker.wrapper.grs.impl;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.common.api.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.api.FluidInteractionRecipe;
import com.tttsaurus.fluidintetweaker.common.api.interaction.InteractionIngredient;
import com.tttsaurus.fluidintetweaker.common.impl.FluidInteractionRecipeManager;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import java.util.*;

@RegistryDescription(linkGenerator = FluidInteractionTweaker.MODID,
                     title = "groovyscript.wiki.fluidintetweaker.fitweaker.title",
                     description = "groovyscript.wiki.fluidintetweaker.fitweaker.description")
public class FITweaker extends VirtualizedRegistry<FluidInteractionRecipe>
{
    @RecipeBuilderDescription(example =
            {
                    @Example(".fluidInitiator(fluid('cobalt')).fluidSurrounding(fluid('water')).outputBlock(block('minecraft:stone'))"),
                    @Example(".fluidInitiator(fluid('cobalt'), false).blockSurrounding(block('minecraft:bedrock')).outputBlock(block('minecraft:sand')).extraInfoLocalizationKey('modpack.string.desc')"),
            })
    public RecipeBuilder recipeBuilder()
    {
        return new RecipeBuilder();
    }

    @Override
    @GroovyBlacklist
    public void onReload()
    {
        removeScripted().forEach(FluidInteractionRecipeManager::removeRecipe);
        restoreFromBackup().forEach(FluidInteractionRecipeManager::addRecipe);
    }

    public void add(FluidInteractionRecipe recipe)
    {
        if (recipe == null) return;
        addScripted(recipe);
        FluidInteractionRecipeManager.addRecipe(recipe);
    }

    /*
    public boolean remove(FluidInteractionRecipe recipe)
    {
        if (recipe == null) return false;
        addBackup(recipe);
        FluidInteractionRecipeManager.removeRecipe(recipe);
        return true;
    }
    */

    public static class RecipeBuilder extends AbstractRecipeBuilder<List<FluidInteractionRecipe>>
    {
        private final List<Integer> possibleRecipeTypes = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        private void removePossibleRecipeTypes(Integer... types)
        {
            for (Integer type : types)
                possibleRecipeTypes.remove(type);
        }

        private Fluid fluidInitiator;
        private boolean isSourceA;
        private Fluid fluidSurrounding;
        private boolean isSourceB;
        private IBlockState blockSurrounding;
        private IBlockState outputBlock;
        private String extraInfoLocalizationKey;

        @RecipeBuilderMethodDescription
        public RecipeBuilder fluidInitiator(FluidStack fluidStack)
        {
            fluidInitiator = fluidStack.getFluid();
            removePossibleRecipeTypes(1, 2, 4);
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder fluidInitiator(FluidStack fluidStack, boolean isSource)
        {
            fluidInitiator = fluidStack.getFluid();
            isSourceA = isSource;
            removePossibleRecipeTypes(3, 5);
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder fluidSurrounding(FluidStack fluidStack)
        {
            fluidSurrounding = fluidStack.getFluid();
            removePossibleRecipeTypes(1, 4, 5);
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder fluidSurrounding(FluidStack fluidStack, boolean isSource)
        {
            fluidSurrounding = fluidStack.getFluid();
            isSourceB = isSource;
            removePossibleRecipeTypes(2, 3, 4, 5);
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder blockSurrounding(IBlockState blockState)
        {
            blockSurrounding = blockState;
            removePossibleRecipeTypes(1, 2, 3);
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder outputBlock(IBlockState blockState)
        {
            outputBlock = blockState;
            return this;
        }
        @RecipeBuilderMethodDescription
        public RecipeBuilder extraInfoLocalizationKey(String key)
        {
            extraInfoLocalizationKey = key;
            return this;
        }

        @Override
        public String getErrorMsg()
        {
            return "Error adding Fluid Interaction Tweaker recipe";
        }
        @Override
        public void validate(GroovyLog.Msg msg)
        {
            validateItems(msg);
            validateFluids(msg);
            msg.add(possibleRecipeTypes.size() != 1, "not a valid type of recipe");
        }
        @Override
        @RecipeBuilderRegistrationMethod
        public @Nullable List<FluidInteractionRecipe> register()
        {
            if (!validate()) return null;

            List<FluidInteractionRecipe> recipeList = new ArrayList<>();

            int recipeType = possibleRecipeTypes.get(0);
            if (recipeType == 1)
            {
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, isSourceA),
                        new InteractionIngredient(fluidSurrounding, isSourceB),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
            }
            else if (recipeType == 2)
            {
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, isSourceA),
                        new InteractionIngredient(fluidSurrounding, true),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, isSourceA),
                        new InteractionIngredient(fluidSurrounding, false),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
            }
            else if (recipeType == 3)
            {
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, true),
                        new InteractionIngredient(fluidSurrounding, true),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, true),
                        new InteractionIngredient(fluidSurrounding, false),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, false),
                        new InteractionIngredient(fluidSurrounding, true),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, false),
                        new InteractionIngredient(fluidSurrounding, false),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
            }
            else if (recipeType == 4)
            {
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, isSourceA),
                        new InteractionIngredient(blockSurrounding),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
            }
            else if (recipeType == 5)
            {
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, true),
                        new InteractionIngredient(blockSurrounding),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
                recipeList.add(new FluidInteractionRecipe(
                        new InteractionIngredient(fluidInitiator, false),
                        new InteractionIngredient(blockSurrounding),
                        ComplexOutput.createSimpleBlockOutput(outputBlock),
                        extraInfoLocalizationKey));
            }

            for (FluidInteractionRecipe recipe: recipeList)
                GrSFluidInteractionPlugin.propertyContainerInstance.tweaker.add(recipe);

            return recipeList;
        }
    }
}
