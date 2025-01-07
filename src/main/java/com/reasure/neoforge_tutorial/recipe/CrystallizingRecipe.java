package com.reasure.neoforge_tutorial.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

// or extends SingleItemRecipe
public record CrystallizingRecipe(String group, Ingredient inputItem,
                                  ItemStack output, int time) implements Recipe<CrystallizingRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(CrystallizingRecipeInput input, Level level) {
        if (level.isClientSide()) return false;

        return inputItem.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(CrystallizingRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.CRYSTALLIZER.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CRYSTALLIZING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CRYSTALLIZING_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<CrystallizingRecipe> {
        public static final MapCodec<CrystallizingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(CrystallizingRecipe::group),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(CrystallizingRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(CrystallizingRecipe::output),
                Codec.intRange(0, Integer.MAX_VALUE).fieldOf("time").forGetter(CrystallizingRecipe::time)
        ).apply(instance, CrystallizingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrystallizingRecipe> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, CrystallizingRecipe::group,
                Ingredient.CONTENTS_STREAM_CODEC, CrystallizingRecipe::inputItem,
                ItemStack.STREAM_CODEC, CrystallizingRecipe::output,
                ByteBufCodecs.VAR_INT, CrystallizingRecipe::time,
                CrystallizingRecipe::new
        );

        @Override
        public MapCodec<CrystallizingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrystallizingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
