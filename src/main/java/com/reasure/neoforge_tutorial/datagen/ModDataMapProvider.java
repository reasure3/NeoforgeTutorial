package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModItems.FROSTFIRE_ICE, new FurnaceFuel(800), false);

        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.TOMATO, new Compostable(0.45f, false), false);
        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.TOMATO_SEEDS, new Compostable(0.2f, true), false);
    }
}
