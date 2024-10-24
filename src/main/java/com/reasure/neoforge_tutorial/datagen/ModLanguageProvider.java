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

        add(ModItems.BLACK_OPAL_SWORD.get(), "Black Opal Sword");
        add(ModItems.BLACK_OPAL_PICKAXE.get(), "Black Opal Pickaxe");
        add(ModItems.BLACK_OPAL_SHOVEL.get(), "Black Opal Shovel");
        add(ModItems.BLACK_OPAL_AXE.get(), "Black Opal Axe");
        add(ModItems.BLACK_OPAL_HOE.get(), "Black Opal Hoe");

        add(ModItems.BLACK_OPAL_PAXEL.get(), "Black Opal Paxel");

        add(ModBlocks.BLACK_OPAL_BLOCK.get(), "Block of Black Opal");
        add(ModBlocks.RAW_BLACK_OPAL_BLOCK.get(), "Block of Raw Black Opal");

        add(ModBlocks.BLACK_OPAL_STAIRS.get(), "Black Opal Stairs");
        add(ModBlocks.BLACK_OPAL_SLAB.get(), "Black Opal Slab");

        add(ModBlocks.BLACK_OPAL_PRESSURE_PLATE.get(), "Black Opal Pressure Plate");
        add(ModBlocks.BLACK_OPAL_BUTTON.get(), "Black Opal Button");

        add(ModBlocks.BLACK_OPAL_FENCE.get(), "Black Opal Fence");
        add(ModBlocks.BLACK_OPAL_FENCE_GATE.get(), "Black Opal Fence Gate");
        add(ModBlocks.BLACK_OPAL_WALL.get(), "Black Opal Wall");

        add(ModBlocks.BLACK_OPAL_DOOR.get(), "Black Opal Door");
        add(ModBlocks.BLACK_OPAL_TRAPDOOR.get(), "Black Opal Trapdoor");

        add(ModBlocks.BLACK_OPAL_ORE.get(), "Black Opal Ore");
        add(ModBlocks.BLACK_OPAL_DEEPSLATE_ORE.get(), "Black Opal Deepslate Ore");
        add(ModBlocks.BLACK_OPAL_END_ORE.get(), "Black Opal End Ore");
        add(ModBlocks.BLACK_OPAL_NETHER_ORE.get(), "Black Opal Nether Ore");

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
