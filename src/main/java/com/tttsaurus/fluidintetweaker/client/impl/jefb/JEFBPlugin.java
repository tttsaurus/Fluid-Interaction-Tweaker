package com.tttsaurus.fluidintetweaker.client.impl.jefb;

import com.tttsaurus.fluidintetweaker.Configuration;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import com.tttsaurus.fluidintetweaker.common.core.WorldIngredient;
import com.tttsaurus.fluidintetweaker.common.core.behavior.BehaviorEvent;
import com.tttsaurus.fluidintetweaker.common.core.behavior.ComplexOutput;
import com.tttsaurus.fluidintetweaker.common.core.behavior.FluidBehaviorRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import java.util.LinkedHashMap;

@SuppressWarnings("all")
@SideOnly(Side.CLIENT)
@JEIPlugin
public class JEFBPlugin implements IModPlugin
{
    private static final LinkedHashMap<String, JEFBRecipeWrapper> recipeWrapperDict = new LinkedHashMap<>();

    //<editor-fold desc="adding recipe wrappers manually">
    public static void addRecipeWrapper(WorldIngredient ingredient, ComplexOutput complexOutput)
    {
        addRecipeWrapper(ingredient, false, complexOutput);
    }
    public static void addRecipeWrapper(WorldIngredient ingredient, boolean isAnyFluidState, ComplexOutput complexOutput)
    {
        JEFBRecipeWrapper recipeWrapper = new JEFBRecipeWrapper(new FluidBehaviorRecipe(ingredient, complexOutput));
        recipeWrapper.isAnyFluidState = isAnyFluidState;

        String key = ingredient.toString();
        if (recipeWrapperDict.containsKey(key))
            key += recipeWrapper.hashCode();
        recipeWrapperDict.put(key, recipeWrapper);
    }
    //</editor-fold>

    // this is an internal method
    public static void addRecipeWrapper(FluidBehaviorRecipe recipe)
    {
        String recipeKey = recipe.ingredient.toString();
        JEFBRecipeWrapper recipeWrapper = new JEFBRecipeWrapper(recipe);
        recipeWrapperDict.put(recipeKey, recipeWrapper);

        //<editor-fold desc="combine `source` and `flowing` if they both exist">
        recipe.ingredient.setIsFluidSource(!recipe.ingredient.getIsFluidSource());
        String newKey = recipe.ingredient.toString();
        if (recipeWrapperDict.containsKey(newKey) &&
            recipeWrapperDict.get(newKey).recipe.complexOutput.equals(recipe.complexOutput))
        {
            recipeWrapperDict.remove(newKey);
            recipeWrapper.isAnyFluidState = true;
        }
        recipe.ingredient.setIsFluidSource(!recipe.ingredient.getIsFluidSource());
        //</editor-fold>
    }

    @Override
    public void register(@NotNull IModRegistry registry)
    {
        if (Configuration.enableThermalFoundationJEICompat && FluidInteractionTweaker.IS_THERMALFOUNDATION_LOADED)
        {
            /*
                ignored traits of Primal Mana:
                - redstone ore lights up
            */
            WorldIngredient FLOWING_MANA = new WorldIngredient(FluidRegistry.getFluid("mana"), false);
            addRecipeWrapper(FLOWING_MANA, true, ComplexOutput.create()
                    .addEvent(BehaviorEvent.createTeleportEvent(8, 8))
                    .addEvent(BehaviorEvent.createSetFireEvent())
                    .addEvent(BehaviorEvent.createSetSnowEvent()));

            /*
                ignored traits of Pyrotheum:
                - flammable blocks are instantly destroyed
                - creepers instantly explode

                flammable?
            */
            WorldIngredient FLOWING_PYROTHEUM = new WorldIngredient(FluidRegistry.getFluid("pyrotheum"), false);
            addRecipeWrapper(FLOWING_PYROTHEUM, true, ComplexOutput.create()
                    .addEvent(BehaviorEvent.createSetFireEvent()));

            /*
                ignored traits of Cryotheum:
                - grass and leaves are instantly destroyed
                - blazes take 10 damage instead of 2
                - snow golems and blizzes are given the effects Speed I and Regeneration I for 6 seconds

                Cryotheum doesn't use oredict for its breaking logic
            */
            WorldIngredient FLOWING_CRYOTHEUM = new WorldIngredient(FluidRegistry.getFluid("cryotheum"), false);
            addRecipeWrapper(FLOWING_CRYOTHEUM, true, ComplexOutput.create()
                    .addEvent(BehaviorEvent.createSetSnowEvent())
                    //.addEvent(BehaviorEvent.createBreakSurroundingEvent(Ingredient.merge(Arrays.asList(new Ingredient[]{new OreIngredient("treeLeaves"), new OreIngredient("plantGrass")}))))
                    .addEvent(BehaviorEvent.createExtinguishFireEvent())
                    .addEvent(BehaviorEvent.createEntityConversionEvent("minecraft:zombie", "minecraft:snowman"))
                    .addEvent(BehaviorEvent.createEntityConversionEvent("minecraft:creeper", "minecraft:snowman")));

            /*
                ignored traits of Petrotheum:
                - if enabled, tectonic petrotheum breaks any adjacent stone- or rock-like blocks. This is disabled by default
            */
            WorldIngredient FLOWING_PETROTHEUM = new WorldIngredient(FluidRegistry.getFluid("petrotheum"), false);
            addRecipeWrapper(FLOWING_PETROTHEUM, true, ComplexOutput.create()
                    .addEvent(BehaviorEvent.createPotionEffectEvent("haste", 120, 0)));
        }

        registry.addRecipes(recipeWrapperDict.values(), JustEnoughFluidBehavior.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new JEFBCategory(registry.getJeiHelpers().getGuiHelper()));
    }
}
