package com.reasure.neoforge_tutorial.compat;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.client.screen.custom.CrystallizerScreen;
import com.reasure.neoforge_tutorial.recipe.CrystallizingRecipe;
import com.reasure.neoforge_tutorial.recipe.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEINeoforgeTutorialPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CrystallizerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<CrystallizingRecipe> crystallizingRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.CRYSTALLIZING_RECIPE.get()).stream().map(RecipeHolder::value).toList();

        registration.addRecipes(CrystallizerRecipeCategory.CRYSTALLIZING_RECIPE_RECIPE_TYPE, crystallizingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.CRYSTALLIZER.asItem(), CrystallizerRecipeCategory.CRYSTALLIZING_RECIPE_RECIPE_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CrystallizerScreen.class, 70, 30, 25, 20, CrystallizerRecipeCategory.CRYSTALLIZING_RECIPE_RECIPE_TYPE);
    }
}
