package com.reasure.neoforge_tutorial;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.block.entity.ModBlockEntities;
import com.reasure.neoforge_tutorial.component.ModDataComponentTypes;
import com.reasure.neoforge_tutorial.effect.ModEffects;
import com.reasure.neoforge_tutorial.enchantment.ModEnchantmentEffects;
import com.reasure.neoforge_tutorial.fluid.BaseFluidType;
import com.reasure.neoforge_tutorial.fluid.ModFluidTypes;
import com.reasure.neoforge_tutorial.fluid.ModFluids;
import com.reasure.neoforge_tutorial.item.ModArmorMaterials;
import com.reasure.neoforge_tutorial.item.ModCreativeTabs;
import com.reasure.neoforge_tutorial.item.ModItemProperties;
import com.reasure.neoforge_tutorial.item.ModItems;
import com.reasure.neoforge_tutorial.potion.ModPotions;
import com.reasure.neoforge_tutorial.sound.ModSounds;
import com.reasure.neoforge_tutorial.villager.ModVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.util.Map;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NeoforgeTutorial.MODID)
public class NeoforgeTutorial {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "neoforge_tutorial";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static Map<Block, Block> STRIPPABLES = Map.of();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public NeoforgeTutorial(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModSounds.register(modEventBus);

        ModDataComponentTypes.register(modEventBus);

        ModArmorMaterials.register(modEventBus);

        ModVillagers.register(modEventBus);

        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);

        ModEnchantmentEffects.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Neoforge_tutorial) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));

        event.enqueueWork(() -> {
            FireBlock fire = (FireBlock) Blocks.FIRE;
            fire.setFlammable(ModBlocks.EBONY_LOG.get(), 5, 5);
            fire.setFlammable(ModBlocks.EBONY_WOOD.get(), 5, 5);
            fire.setFlammable(ModBlocks.STRIPPED_EBONY_LOG.get(), 5, 5);
            fire.setFlammable(ModBlocks.STRIPPED_EBONY_WOOD.get(), 5, 5);
            fire.setFlammable(ModBlocks.EBONY_PLANKS.get(), 5, 20);
            fire.setFlammable(ModBlocks.EBONY_LEAVES.get(), 30, 60);

            STRIPPABLES = new ImmutableMap.Builder<Block, Block>()
                    .put(ModBlocks.EBONY_LOG.get(), ModBlocks.STRIPPED_EBONY_LOG.get())
                    .put(ModBlocks.EBONY_WOOD.get(), ModBlocks.STRIPPED_EBONY_WOOD.get())
                    .build();

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PETUNIA.getId(), ModBlocks.POTTED_PETUNIA);
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.BLACK_OPAL_SWORD);
            event.accept(ModItems.BLACK_OPAL_HELMET);
            event.accept(ModItems.BLACK_OPAL_CHESTPLATE);
            event.accept(ModItems.BLACK_OPAL_LEGGINGS);
            event.accept(ModItems.BLACK_OPAL_BOOTS);
            event.accept(ModItems.BLACK_OPAL_HORSE_ARMOR);
            event.accept(ModItems.KAUPEN_BOW);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.CHAINSAW);
            event.accept(ModItems.BLACK_OPAL_PICKAXE);
            event.accept(ModItems.BLACK_OPAL_SHOVEL);
            event.accept(ModItems.BLACK_OPAL_AXE);
            event.accept(ModItems.BLACK_OPAL_HOE);
            event.accept(ModItems.BLACK_OPAL_PAXEL);
            event.accept(ModItems.BLACK_OPAL_HAMMER);
            event.accept(ModItems.METAL_DETECTOR);
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.TOMATO);
            event.accept(ModItems.TOMATO_SEEDS);
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.BLACK_OPAL);
            event.accept(ModItems.RAW_BLACK_OPAL);
            event.accept(ModItems.KAUPEN_SMITHING_TEMPLATE);
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.BLACK_OPAL_BLOCK);
            event.accept(ModBlocks.BLACK_OPAL_STAIRS);
            event.accept(ModBlocks.BLACK_OPAL_SLAB);
            event.accept(ModBlocks.BLACK_OPAL_PRESSURE_PLATE);
            event.accept(ModBlocks.BLACK_OPAL_BUTTON);
            event.accept(ModBlocks.BLACK_OPAL_FENCE);
            event.accept(ModBlocks.BLACK_OPAL_FENCE_GATE);
            event.accept(ModBlocks.BLACK_OPAL_WALL);
            event.accept(ModBlocks.BLACK_OPAL_DOOR);
            event.accept(ModBlocks.BLACK_OPAL_TRAPDOOR);
        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.RAW_BLACK_OPAL_BLOCK);

            event.accept(ModBlocks.BLACK_OPAL_ORE);
            event.accept(ModBlocks.BLACK_OPAL_DEEPSLATE_ORE);
            event.accept(ModBlocks.BLACK_OPAL_END_ORE);
            event.accept(ModBlocks.BLACK_OPAL_NETHER_ORE);
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.MAGIC_BLOCK);
            event.accept(ModBlocks.BLACK_OPAL_LAMP);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            ModItemProperties.addCustomItemProperties();
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_BLACK_OPAL_WATER.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_BLACK_OPAL_WATER.get(), RenderType.translucent());
            });
        }

        @SubscribeEvent
        public static void onClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(((BaseFluidType) ModFluidTypes.BLACK_OPAL_WATER_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.BLACK_OPAL_WATER_FLUID_TYPE.get());
        }

        @SubscribeEvent
        public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event) {
            event.register(ClientModEvents::getFoliageColor, ModBlocks.COLORED_LEAVES.get());
        }

        @SubscribeEvent
        public static void registerColoredItems(RegisterColorHandlersEvent.Item event) {
            event.register(ClientModEvents::defaultFoliageColor, ModBlocks.COLORED_LEAVES.get());
        }

        private static int getFoliageColor(BlockState state, BlockAndTintGetter level, BlockPos pos, int tintIndex) {
            return level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor();
        }

        private static int defaultFoliageColor(ItemStack stack, int index) {
            return FoliageColor.getDefaultColor();
        }
    }
}
