package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.effect.ModEffects;
import com.reasure.neoforge_tutorial.item.ModItems;
import com.reasure.neoforge_tutorial.potion.ModPotions;
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

        add(ModItems.BLACK_OPAL_HAMMER.get(), "Black Opal Hammer");

        add(ModItems.BLACK_OPAL_HELMET.get(), "Black Opal Helmet");
        add(ModItems.BLACK_OPAL_CHESTPLATE.get(), "Black Opal Chestplate");
        add(ModItems.BLACK_OPAL_LEGGINGS.get(), "Black Opal Leggings");
        add(ModItems.BLACK_OPAL_BOOTS.get(), "Black Opal Boots");

        add(ModItems.BLACK_OPAL_HORSE_ARMOR.get(), "Black Opal Horse Armor");

        add(ModItems.KAUPEN_SMITHING_TEMPLATE.get(), "Kaupen Trim Template");

        add(ModItems.METAL_DETECTOR.get(), "Metal Detector");
        add(ModItems.DATA_TABLET.get(), "Data Tablet");
        add(ModItems.KAUPEN_BOW.get(), "Kaupen Bow");

        add(ModItems.TOMATO_SEEDS.get(), "Tomato Seeds");

        add(ModItems.BAR_BRAWL_MUSIC_DISC.get(), "Bar Brawl Music Disc");

        add(ModItems.RADIATION_STAFF.get(), "Radiation Staff");

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

        add(ModBlocks.BLACK_OPAL_LAMP.get(), "Black Opal Lamp");

        add(ModBlocks.TOMATO_CROP.get(), "Tomato Crop");

        add(ModBlocks.PETUNIA.get(), "Petunia");
        add(ModBlocks.POTTED_PETUNIA.get(), "Potted Petunia");

        add(ModBlocks.COLORED_LEAVES.get(), "Colored Leaves");

        add(ModBlocks.EBONY_LOG.get(), "Ebony Log");
        add(ModBlocks.EBONY_PLANKS.get(), "Ebony Planks");
        add(ModBlocks.EBONY_WOOD.get(), "Ebony Wood");
        add(ModBlocks.STRIPPED_EBONY_LOG.get(), "Stripped Ebony Log");
        add(ModBlocks.STRIPPED_EBONY_WOOD.get(), "Stripped Ebony Wood");
        add(ModBlocks.EBONY_LEAVES.get(), "Ebony Leaves");
        add(ModBlocks.EBONY_SAPLING.get(), "Ebony Sapling");

        add(ModBlocks.PEDESTAL.get(), "Pedestal");

        add(ModEffects.SLiMEY_EFFECT.get(), "Slimey");
        add("item.minecraft.potion.effect.slimey_potion", "Potion of Slimey");
        add("item.minecraft.splash_potion.effect.slimey_potion", "Splash Potion of Slimey");
        add("item.minecraft.lingering_potion.effect.slimey_potion", "Lingering Potion of Slimey");
        add("item.minecraft.potion.effect.long_slimey_potion", "Potion of Slimey");
        add("item.minecraft.splash_potion.effect.long_slimey_potion", "Splash Potion of Slimey");
        add("item.minecraft.lingering_potion.effect.long_slimey_potion", "Lingering Potion of Slimey");
        add("item.minecraft.tipped_arrow.effect.slimey_potion", "Arrow of Slimey");
        add("item.minecraft.tipped_arrow.effect.long_slimey_potion", "Arrow of Slimey");

        add("tooltip.neoforge_tutorial.chainsaw.tooltip.shift", "Press §eShift§r for more Information!");
        add("tooltip.neoforge_tutorial.chainsaw.tooltip.1", "This chainsaw is awesome and will cut down trees!");
        add("tooltip.neoforge_tutorial.chainsaw.tooltip.2", "§oChainsaw go vroom!§r");

        add("tooltip.neoforge_tutorial.magic_block.tooltip.1", "This Block is §9MAGICAL§r");
        add("tooltip.neoforge_tutorial.tomato.1", "Tomato; Tomato");
        add("itemGroup.neoforge_tutorial.black_opal_items_tab", "Black Opal Items");
        add("itemGroup.neoforge_tutorial.black_opal_blocks_tab", "Black Opal Blocks");

        add("trim_pattern.neoforge_tutorial.kaupen", "Kaupen Armor Trim");
        add("trim_material.neoforge_tutorial.black_opal", "Black Opal Material");

        add("item.neoforge_tutorial.metal_detector.no_valuables", "No Valuables Found");

        add("sounds." + NeoforgeTutorial.MODID + ".chainsaw_cut", "Chainsaw Cutting Sound");
        add("sounds." + NeoforgeTutorial.MODID + ".chainsaw_pull", "Chainsaw Pulling Sound");
        add("jukebox_song." + NeoforgeTutorial.MODID + ".bar_brawl", "Bar Brawl");
        add("item." + NeoforgeTutorial.MODID + ".bar_brawl_music_disc.desc", "Bryan Tech - Bar Brawl (CC0)");
    }
}
