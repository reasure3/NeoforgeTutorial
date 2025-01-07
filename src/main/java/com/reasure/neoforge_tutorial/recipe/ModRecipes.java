package com.reasure.neoforge_tutorial.recipe;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, NeoforgeTutorial.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, NeoforgeTutorial.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrystallizingRecipe>> CRYSTALLIZING_SERIALIZER = SERIALIZERS.register("crystallizing", CrystallizingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<CrystallizingRecipe>> CRYSTALLIZING_RECIPE = TYPES.register("crystallizing", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "crystallizing")));

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
