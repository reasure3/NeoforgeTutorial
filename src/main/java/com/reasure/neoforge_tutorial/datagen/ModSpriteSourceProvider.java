package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModSpriteSourceProvider extends SpriteSourceProvider {
    public ModSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NeoforgeTutorial.MODID, existingFileHelper);
    }

    @Override
    protected void gather() {
        atlas(ResourceLocation.withDefaultNamespace("armor_trims"))
                .addSource(new PalettedPermutations(
                        List.of(pattern("kaupen"), pattern("kaupen_leggings")),
                        ResourceLocation.withDefaultNamespace("trims/color_palettes/trim_palette"),
                        Map.of("black_opal", material("black_opal"))
                ));
    }

    private ResourceLocation pattern(String name) {
        return ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "trims/models/armor/" + name);
    }

    private ResourceLocation material(String name) {
        return ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "trims/color_palettes/" + name);
    }
}
