package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.recipe.CrystallizingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CrystallizingRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final ItemStack result;
    private int maxProgress = 72;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private @Nullable String group;

    private CrystallizingRecipeBuilder(Ingredient ingredient, ItemStack result) {
        this.ingredient = ingredient;
        this.result = result;
    }

    public static CrystallizingRecipeBuilder create(Ingredient ingredient, ItemLike result) {
        return new CrystallizingRecipeBuilder(ingredient, new ItemStack(result));
    }

    public static CrystallizingRecipeBuilder create(Ingredient ingredient, ItemLike result, int count) {
        return new CrystallizingRecipeBuilder(ingredient, new ItemStack(result, count));
    }

    @Override
    public CrystallizingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public CrystallizingRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    public CrystallizingRecipeBuilder time(int recipeTimeTick) {
        this.maxProgress = recipeTimeTick;
        return this;
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        ensureValid(id);
        Advancement.Builder advancementBuilder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(advancementBuilder::addCriterion);
        CrystallizingRecipe recipe = new CrystallizingRecipe(Objects.requireNonNullElse(group, ""), ingredient, result, maxProgress);
        recipeOutput.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/crystallizing/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
