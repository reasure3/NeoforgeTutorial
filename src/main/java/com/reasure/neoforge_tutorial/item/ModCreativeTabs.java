package com.reasure.neoforge_tutorial.item;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
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
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLACK_OPAL_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("black_opal_blocks_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.neoforge_tutorial.black_opal_blocks_tab"))
                    .icon(() -> new ItemStack(ModItems.RAW_BLACK_OPAL.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "black_opal_items_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(Blocks.STONE);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
