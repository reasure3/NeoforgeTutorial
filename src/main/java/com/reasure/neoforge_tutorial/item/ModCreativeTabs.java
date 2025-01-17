package com.reasure.neoforge_tutorial.item;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.fluid.ModFluids;
import com.reasure.neoforge_tutorial.potion.ModPotions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NeoforgeTutorial.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLACK_OPAL_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("black_opal_items_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.neoforge_tutorial.black_opal_items_tab"))
                    .icon(() -> new ItemStack(ModItems.BLACK_OPAL.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.BLACK_OPAL);
                        output.accept(ModItems.RAW_BLACK_OPAL);

                        output.accept(ModItems.CHAINSAW);
                        output.accept(ModItems.TOMATO);
                        output.accept(ModItems.FROSTFIRE_ICE);

                        output.accept(ModItems.BLACK_OPAL_SWORD);
                        output.accept(ModItems.BLACK_OPAL_PICKAXE);
                        output.accept(ModItems.BLACK_OPAL_SHOVEL);
                        output.accept(ModItems.BLACK_OPAL_AXE);
                        output.accept(ModItems.BLACK_OPAL_HOE);

                        output.accept(ModItems.BLACK_OPAL_PAXEL);

                        output.accept(ModItems.BLACK_OPAL_HAMMER);

                        output.accept(ModItems.BLACK_OPAL_HELMET);
                        output.accept(ModItems.BLACK_OPAL_CHESTPLATE);
                        output.accept(ModItems.BLACK_OPAL_LEGGINGS);
                        output.accept(ModItems.BLACK_OPAL_BOOTS);

                        output.accept(ModItems.BLACK_OPAL_HORSE_ARMOR);
                        output.accept(ModItems.KAUPEN_SMITHING_TEMPLATE);

                        output.accept(ModItems.METAL_DETECTOR);
                        output.accept(ModItems.DATA_TABLET);
                        output.accept(ModItems.KAUPEN_BOW);

                        output.accept(ModItems.TOMATO_SEEDS);

                        output.accept(ModItems.BAR_BRAWL_MUSIC_DISC);

                        output.accept(ModItems.RADIATION_STAFF);

                        output.accept(ModFluids.BLACK_OPAL_WATER_BUCKET);

                        makePotions(output, ModPotions.SLIMEY_POTION, ModPotions.LONG_SLIMEY_POTION);
                    }).build());

    @SafeVarargs
    private static void makePotions(CreativeModeTab.Output output, Holder<Potion>... potions) {
        for (Holder<Potion> potion : potions) {
            output.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, potion));
        }
        for (Holder<Potion> potion : potions) {
            output.accept(PotionContents.createItemStack(Items.POTION, potion));
        }
        for (Holder<Potion> potion : potions) {
            output.accept(PotionContents.createItemStack(Items.SPLASH_POTION, potion));
        }
        for (Holder<Potion> potion : potions) {
            output.accept(PotionContents.createItemStack(Items.LINGERING_POTION, potion));
        }
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLACK_OPAL_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("black_opal_blocks_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.neoforge_tutorial.black_opal_blocks_tab"))
                    .icon(() -> new ItemStack(ModBlocks.BLACK_OPAL_BLOCK.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "black_opal_items_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.BLACK_OPAL_BLOCK);
                        output.accept(ModBlocks.RAW_BLACK_OPAL_BLOCK);

                        output.accept(ModBlocks.BLACK_OPAL_STAIRS);
                        output.accept(ModBlocks.BLACK_OPAL_SLAB);

                        output.accept(ModBlocks.BLACK_OPAL_PRESSURE_PLATE);
                        output.accept(ModBlocks.BLACK_OPAL_BUTTON);

                        output.accept(ModBlocks.BLACK_OPAL_FENCE);
                        output.accept(ModBlocks.BLACK_OPAL_FENCE_GATE);
                        output.accept(ModBlocks.BLACK_OPAL_WALL);

                        output.accept(ModBlocks.BLACK_OPAL_DOOR);
                        output.accept(ModBlocks.BLACK_OPAL_TRAPDOOR);

                        output.accept(ModBlocks.BLACK_OPAL_ORE);
                        output.accept(ModBlocks.BLACK_OPAL_DEEPSLATE_ORE);
                        output.accept(ModBlocks.BLACK_OPAL_END_ORE);
                        output.accept(ModBlocks.BLACK_OPAL_NETHER_ORE);

                        output.accept(ModBlocks.MAGIC_BLOCK);

                        output.accept(ModBlocks.BLACK_OPAL_LAMP);

                        output.accept(ModBlocks.PETUNIA);

                        output.accept(ModBlocks.COLORED_LEAVES);

                        output.accept(ModBlocks.EBONY_LOG);
                        output.accept(ModBlocks.STRIPPED_EBONY_LOG);
                        output.accept(ModBlocks.EBONY_WOOD);
                        output.accept(ModBlocks.STRIPPED_EBONY_WOOD);
                        output.accept(ModBlocks.EBONY_LEAVES);
                        output.accept(ModBlocks.EBONY_SAPLING);
                        output.accept(ModBlocks.EBONY_PLANKS);

                        output.accept(ModBlocks.PEDESTAL);

                        output.accept(ModBlocks.CRYSTALLIZER);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
