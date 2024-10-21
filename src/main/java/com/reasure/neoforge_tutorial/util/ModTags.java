package com.reasure.neoforge_tutorial.util;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> CHAINSAW_CUTABLE_BLOCKS = createTag("chainsaw_cutable_blocks");
        public static final TagKey<Block> INCORRECT_FOR_BLACK_OPAL_TOOL = createTag("incorrect_for_black_opal_tool");
        public static final TagKey<Block> NEEDS_BLACK_OPAL_TOOL = createTag("needs_black_opal_tool");

        public static final TagKey<Block> MINEABLE_WITH_PAXEL = createTag("mineable/paxel");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, name));
        }
    }
}
