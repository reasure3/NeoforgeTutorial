package com.reasure.neoforge_tutorial.worldgen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> EBONY_PLACED_KEY = registerKey("ebony_placed");
    public static final ResourceKey<PlacedFeature> NETHER_EBONY_PLACED_KEY = registerKey("nether_ebony_placed");

    public static final ResourceKey<PlacedFeature> BLACK_OPAL_ORE_PLACED_KEY = registerKey("black_opal_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_BLACK_OPAL_ORE_PLACED_KEY = registerKey("nether_black_opal_ore_placed");
    public static final ResourceKey<PlacedFeature> END_BLACK_OPAL_ORE_PLACED_KEY = registerKey("end_black_opal_ore_placed");

    public static final ResourceKey<PlacedFeature> PETUNIA_PLACED_KEY = registerKey("petunia_placed");

    public static final ResourceKey<PlacedFeature> BLACK_OPAL_GEODE_PLACED_KEY = registerKey("black_opal_geode_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, EBONY_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.EBONY_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2), ModBlocks.EBONY_SAPLING.get()));

        register(context, NETHER_EBONY_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_EBONY_KEY),
                List.of(CountOnEveryLayerPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(UniformInt.of(0, 1), 9)
                                .add(ConstantInt.of(3), 1).build())),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.EBONY_SAPLING.get().defaultBlockState(), BlockPos.ZERO))));

        register(context, BLACK_OPAL_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_BLACK_OPAL_ORE_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, NETHER_BLACK_OPAL_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_BLACK_OPAL_ORE_KEY),
                ModOrePlacements.commonOrePlacement(9,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, END_BLACK_OPAL_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_BLACK_OPAL_ORE_KEY),
                ModOrePlacements.commonOrePlacement(7,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, PETUNIA_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PETUNIA_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, BLACK_OPAL_GEODE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BLACK_OPAL_GEODE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(50)),
                        BiomeFilter.biome()));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
