package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NeoforgeTutorial.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.BLACK_OPAL_BLOCK.get())
                .add(ModBlocks.RAW_BLACK_OPAL_BLOCK.get())
                .add(ModBlocks.BLACK_OPAL_STAIRS.get())
                .add(ModBlocks.BLACK_OPAL_SLAB.get())
                .add(ModBlocks.BLACK_OPAL_PRESSURE_PLATE.get())
                .add(ModBlocks.BLACK_OPAL_BUTTON.get())
                .add(ModBlocks.BLACK_OPAL_FENCE.get())
                .add(ModBlocks.BLACK_OPAL_FENCE_GATE.get())
                .add(ModBlocks.BLACK_OPAL_WALL.get())
                .add(ModBlocks.BLACK_OPAL_ORE.get())
                .add(ModBlocks.BLACK_OPAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.BLACK_OPAL_END_ORE.get())
                .add(ModBlocks.BLACK_OPAL_NETHER_ORE.get())
                .add(ModBlocks.MAGIC_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.BLACK_OPAL_ORE.get())
                .add(ModBlocks.BLACK_OPAL_DEEPSLATE_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.BLACK_OPAL_END_ORE.get())
                .add(ModBlocks.BLACK_OPAL_NETHER_ORE.get());

        // 철이랑 동급
        tag(ModTags.Blocks.NEEDS_BLACK_OPAL_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_BLACK_OPAL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_BLACK_OPAL_TOOL);

        tag(ModTags.Blocks.MINEABLE_WITH_PAXEL)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(BlockTags.MINEABLE_WITH_HOE);

        tag(ModTags.Blocks.CHAINSAW_CUTABLE_BLOCKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.LEAVES)
                .add(Blocks.MANGROVE_DOOR);

        tag(BlockTags.FENCES).add(ModBlocks.BLACK_OPAL_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.BLACK_OPAL_FENCE_GATE.get());
        tag(BlockTags.WALLS).add(ModBlocks.BLACK_OPAL_WALL.get());

        tag(ModTags.Blocks.EBONY_LOGS)
                .add(ModBlocks.EBONY_LOG.get())
                .add(ModBlocks.STRIPPED_EBONY_LOG.get())
                .add(ModBlocks.EBONY_WOOD.get())
                .add(ModBlocks.STRIPPED_EBONY_WOOD.get());
        tag(BlockTags.LOGS_THAT_BURN)
                .addTag(ModTags.Blocks.EBONY_LOGS);
        tag(Tags.Blocks.STRIPPED_LOGS)
                .add(ModBlocks.STRIPPED_EBONY_LOG.get());
        tag(Tags.Blocks.STRIPPED_WOODS)
                .add(ModBlocks.STRIPPED_EBONY_WOOD.get());
    }
}
