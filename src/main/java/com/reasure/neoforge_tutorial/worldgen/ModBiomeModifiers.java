package com.reasure.neoforge_tutorial.worldgen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_TREE_EBONY = registerKey("add_tree_ebony");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_TREE_EBONY = registerKey("add_nether_tree_ebony");

    public static final ResourceKey<BiomeModifier> ADD_BLACK_OPAL_ORE = registerKey("add_black_opal_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_BLACK_OPAL_ORE = registerKey("add_nether_black_opal_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_BLACK_OPAL_ORE = registerKey("add_end_black_opal_ore");

    public static final ResourceKey<BiomeModifier> ADD_PETUNIA = registerKey("add_petunia");

    public static final ResourceKey<BiomeModifier> ADD_BLACK_OPAL_GEODE = registerKey("add_black_opal_geode");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        context.register(ADD_TREE_EBONY, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_DECIDUOUS_TREE),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.EBONY_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_NETHER_TREE_EBONY, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_NETHER),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.NETHER_EBONY_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_BLACK_OPAL_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.BLACK_OPAL_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_NETHER_BLACK_OPAL_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_NETHER),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.NETHER_BLACK_OPAL_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_END_BLACK_OPAL_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_END),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.END_BLACK_OPAL_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_PETUNIA, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.FLOWER_FOREST)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.PETUNIA_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_BLACK_OPAL_GEODE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.BLACK_OPAL_GEODE_PLACED_KEY)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));
    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, name));
    }
}
