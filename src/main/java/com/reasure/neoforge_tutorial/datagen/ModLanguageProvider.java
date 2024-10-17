package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, NeoforgeTutorial.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.BLACK_OPAL.get(), "Black Opal");
        add(ModItems.RAW_BLACK_OPAL.get(), "Raw Black Opal");
        add(ModItems.CHAINSAW.get(), "Chainsaw");
        add(ModItems.TOMATO.get(), "Tomato");
        add(ModItems.FROSTFIRE_ICE.get(), "Frostfire Ice");

        add(ModBlocks.BLACK_OPAL_BLOCK.get(), "Block of Black Opal");
        add(ModBlocks.RAW_BLACK_OPAL_BLOCK.get(), "Block of Raw Black Opal");

        add(ModBlocks.BLACK_OPAL_STAIRS.get(), "Black Opal Stairs");
        add(ModBlocks.BLACK_OPAL_SLAB.get(), "Black Opal Slab");

        add(ModBlocks.BLACK_OPAL_ORE.get(), "Black Opal Ore");
        add(ModBlocks.BLACK_OPAL_DEEPSLATE_ORE.get(), "Black Opal Deepslate Ore");
        add(ModBlocks.BLACK_OPAL_END_ORE.get(), "Black Opal End Ore");
        add(ModBlocks.BLACK_OPAL_NETHER_ORE.get(), "Black Opal Nether ORe");

        add(ModBlocks.MAGIC_BLOCK.get(), "Magic Block");

        add("tooltip.neoforge_tutorial.chainsaw.tooltip.shift", "Press §eShift§r for more Information!");
        add("tooltip.neoforge_tutorial.chainsaw.tooltip.1", "This chainsaw is awesome and will cut down trees!");
        add("tooltip.neoforge_tutorial.chainsaw.tooltip.2", "§oChainsaw go vroom!§r");

        add("tooltip.neoforge_tutorial.magic_block.tooltip.1", "This Block is §9MAGICAL§r");
        add("tooltip.neoforge_tutorial.tomato.1", "Tomato; Tomato");
        add("itemGroup.neoforge_tutorial.black_opal_items_tab", "Black Opal Items");
        add("itemGroup.neoforge_tutorial.black_opal_blocks_tab", "Black Opal Blocks");
    }
}
