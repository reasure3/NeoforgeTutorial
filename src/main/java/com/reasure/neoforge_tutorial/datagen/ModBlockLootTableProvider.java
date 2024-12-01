package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.BLACK_OPAL_BLOCK.get());
        dropSelf(ModBlocks.RAW_BLACK_OPAL_BLOCK.get());

        dropSelf(ModBlocks.BLACK_OPAL_STAIRS.get());
        add(ModBlocks.BLACK_OPAL_SLAB.get(), this::createSlabItemTable);

        dropSelf(ModBlocks.BLACK_OPAL_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.BLACK_OPAL_BUTTON.get());

        dropSelf(ModBlocks.BLACK_OPAL_FENCE.get());
        dropSelf(ModBlocks.BLACK_OPAL_FENCE_GATE.get());
        dropSelf(ModBlocks.BLACK_OPAL_WALL.get());

        add(ModBlocks.BLACK_OPAL_DOOR.get(), this::createDoorTable);
        dropSelf(ModBlocks.BLACK_OPAL_TRAPDOOR.get());

        add(ModBlocks.BLACK_OPAL_ORE.get(),
                block -> createOreDrop(block, ModItems.RAW_BLACK_OPAL.get()));
        add(ModBlocks.BLACK_OPAL_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(block, ModItems.RAW_BLACK_OPAL.get(), 2, 5));
        add(ModBlocks.BLACK_OPAL_END_ORE.get(),
                block -> createMultipleOreDrops(block, ModItems.RAW_BLACK_OPAL.get(), 4, 7));
        add(ModBlocks.BLACK_OPAL_NETHER_ORE.get(),
                block -> createMultipleOreDrops(block, ModItems.RAW_BLACK_OPAL.get(), 3, 9));

        dropSelf(ModBlocks.BLACK_OPAL_LAMP.get());
    }

    protected LootTable.Builder createMultipleOreDrops(Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return createSilkTouchDispatchTable(block, this.applyExplosionDecay(block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
