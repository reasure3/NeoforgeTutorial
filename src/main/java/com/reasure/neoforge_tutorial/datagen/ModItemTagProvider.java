package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.item.ModItems;
import com.reasure.neoforge_tutorial.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, NeoforgeTutorial.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.BLACK_OPAL.get())
                .add(Items.COAL)
                .add(Items.DANDELION)
                .add(Items.COMPASS);

        tag(ItemTags.SWORDS)
                .add(ModItems.BLACK_OPAL_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.BLACK_OPAL_PICKAXE.get())
                .addTag(ModTags.Items.PAXELS)
                .add(ModItems.BLACK_OPAL_HAMMER.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.BLACK_OPAL_SHOVEL.get())
                .addTag(ModTags.Items.PAXELS);
        tag(ItemTags.AXES)
                .add(ModItems.BLACK_OPAL_AXE.get())
                .addTag(ModTags.Items.PAXELS);
        tag(ItemTags.HOES)
                .add(ModItems.BLACK_OPAL_HOE.get())
                .addTag(ModTags.Items.PAXELS);
        tag(ModTags.Items.PAXELS)
                .add(ModItems.BLACK_OPAL_PAXEL.get());

        tag(Tags.Items.MINING_TOOL_TOOLS)
                .add(ModItems.BLACK_OPAL_PICKAXE.get())
                .add(ModItems.BLACK_OPAL_PAXEL.get())
                .add(ModItems.BLACK_OPAL_HAMMER.get());

        tag(Tags.Items.MELEE_WEAPON_TOOLS)
                .add(ModItems.BLACK_OPAL_SWORD.get())
                .add(ModItems.BLACK_OPAL_AXE.get());

        tag(ItemTags.CLUSTER_MAX_HARVESTABLES)
                .add(ModItems.BLACK_OPAL_PICKAXE.get())
                .add(ModItems.BLACK_OPAL_PAXEL.get())
                .add(ModItems.BLACK_OPAL_HAMMER.get());

        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.BLACK_OPAL_HELMET.get());
        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.BLACK_OPAL_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.BLACK_OPAL_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.BLACK_OPAL_BOOTS.get());

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.BLACK_OPAL_HELMET.get())
                .add(ModItems.BLACK_OPAL_CHESTPLATE.get())
                .add(ModItems.BLACK_OPAL_LEGGINGS.get())
                .add(ModItems.BLACK_OPAL_BOOTS.get());

        tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.BLACK_OPAL.get());

        tag(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.KAUPEN_SMITHING_TEMPLATE.get());

        copy(ModTags.Blocks.EBONY_LOGS, ModTags.Items.EBONY_LOGS);
        tag(ItemTags.LOGS_THAT_BURN)
                .addTag(ModTags.Items.EBONY_LOGS);
        tag(Tags.Items.STRIPPED_LOGS)
                .add(ModBlocks.STRIPPED_EBONY_LOG.asItem());
        tag(Tags.Items.STRIPPED_WOODS)
                .add(ModBlocks.STRIPPED_EBONY_WOOD.asItem());
        tag(ItemTags.PLANKS)
                .add(ModBlocks.EBONY_PLANKS.asItem());
    }
}
