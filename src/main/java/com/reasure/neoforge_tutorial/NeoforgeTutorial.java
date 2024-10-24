package com.reasure.neoforge_tutorial;

import com.mojang.logging.LogUtils;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.item.ModCreativeTabs;
import com.reasure.neoforge_tutorial.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NeoforgeTutorial.MODID)
public class NeoforgeTutorial {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "neoforge_tutorial";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public NeoforgeTutorial(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

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
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.BLACK_OPAL_SWORD);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.CHAINSAW);
            event.accept(ModItems.BLACK_OPAL_PICKAXE);
            event.accept(ModItems.BLACK_OPAL_SHOVEL);
            event.accept(ModItems.BLACK_OPAL_AXE);
            event.accept(ModItems.BLACK_OPAL_HOE);
            event.accept(ModItems.BLACK_OPAL_PAXEL);
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.TOMATO);
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.BLACK_OPAL);
            event.accept(ModItems.RAW_BLACK_OPAL);
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
        }
    }
}
