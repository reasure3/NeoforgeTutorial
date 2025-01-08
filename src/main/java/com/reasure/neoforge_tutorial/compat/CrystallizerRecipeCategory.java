package com.reasure.neoforge_tutorial.compat;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.recipe.CrystallizingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CrystallizerRecipeCategory implements IRecipeCategory<CrystallizingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "crystallizing");
    public static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "textures/gui/crystallizer/jei/crystallizer_jei.png");
    public static final ResourceLocation ARROW_TEXTURE = ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "textures/gui/crystallizer/jei/arrow_progress_jei.png");
    private static final ResourceLocation CLUSTER_TEXTURE = ResourceLocation.parse("textures/block/amethyst_cluster.png");

    public static final RecipeType<CrystallizingRecipe> CRYSTALLIZING_RECIPE_RECIPE_TYPE = new RecipeType<>(UID, CrystallizingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    private final IDrawableStatic arrow_static;
    private final IDrawableStatic cluster_static;

    private final IGuiHelper guiHelper;

    public CrystallizerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(BACKGROUND_TEXTURE, 0, 0, 162, 73);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CRYSTALLIZER.get()));
        this.arrow_static = helper.drawableBuilder(ARROW_TEXTURE, 0, 0, 22, 15).setTextureSize(22, 15).build();
        this.cluster_static = helper.drawableBuilder(CLUSTER_TEXTURE, 0, 0, 16, 16).setTextureSize(16, 16).build();
        this.guiHelper = helper;
    }

    @Override
    public RecipeType getRecipeType() {
        return CRYSTALLIZING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Crystallizing");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return 162;
    }

    @Override
    public int getHeight() {
        return 73;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrystallizingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 47, 28).addIngredients(recipe.getIngredients().getFirst());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 97, 28).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, CrystallizingRecipe recipe, IFocusGroup focuses) {
        IDrawableAnimated animatedArrow = guiHelper.createAnimatedDrawable(arrow_static, recipe.time(), IDrawableAnimated.StartDirection.LEFT, false);
        IDrawableAnimated animatedCluster = guiHelper.createAnimatedDrawable(cluster_static, recipe.time(), IDrawableAnimated.StartDirection.BOTTOM, false);

        builder.addDrawable(animatedArrow).setPosition(67, 29);
        builder.addDrawable(animatedCluster).setPosition(97, 7);

        addCookTime(builder, recipe);
    }

    protected void addCookTime(IRecipeExtrasBuilder builder, CrystallizingRecipe recipe) {
        int cookTime = recipe.time();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            builder.addText(timeString, 72 - 10, 10)
                    .setPosition(46, 50, 72, 10, HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
                    .setTextAlignment(HorizontalAlignment.RIGHT)
                    .setTextAlignment(VerticalAlignment.BOTTOM)
                    .setColor(0xFF808080);
        }
    }

    @Override
    public void draw(CrystallizingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
    }
}
