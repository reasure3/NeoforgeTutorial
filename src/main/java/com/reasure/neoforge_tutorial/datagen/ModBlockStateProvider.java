package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NeoforgeTutorial.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.BLACK_OPAL_BLOCK);
        blockWithItem(ModBlocks.RAW_BLACK_OPAL_BLOCK);

        stairsBlock((StairBlock) ModBlocks.BLACK_OPAL_STAIRS.get(), blockTexture(ModBlocks.BLACK_OPAL_BLOCK.get()));
        slabBlock((SlabBlock) ModBlocks.BLACK_OPAL_SLAB.get(), blockTexture(ModBlocks.BLACK_OPAL_BLOCK.get()), blockTexture(ModBlocks.BLACK_OPAL_BLOCK.get()));

        blockItem(ModBlocks.BLACK_OPAL_STAIRS);
        blockItem(ModBlocks.BLACK_OPAL_SLAB);

        pressurePlateBlock((PressurePlateBlock) ModBlocks.BLACK_OPAL_PRESSURE_PLATE.get(), blockTexture(ModBlocks.BLACK_OPAL_BLOCK.get()));
        button(ModBlocks.BLACK_OPAL_BUTTON, ModBlocks.BLACK_OPAL_BLOCK);

        blockItem(ModBlocks.BLACK_OPAL_PRESSURE_PLATE);

        fence(ModBlocks.BLACK_OPAL_FENCE, ModBlocks.BLACK_OPAL_BLOCK);
        fenceGateBlock((FenceGateBlock) ModBlocks.BLACK_OPAL_FENCE_GATE.get(), blockTexture(ModBlocks.BLACK_OPAL_BLOCK.get()));
        wall(ModBlocks.BLACK_OPAL_WALL, ModBlocks.BLACK_OPAL_BLOCK);

        blockItem(ModBlocks.BLACK_OPAL_FENCE_GATE);

        blockWithItem(ModBlocks.BLACK_OPAL_ORE);
        blockWithItem(ModBlocks.BLACK_OPAL_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.BLACK_OPAL_END_ORE);
        blockWithItem(ModBlocks.BLACK_OPAL_NETHER_ORE);

        blockWithItem(ModBlocks.MAGIC_BLOCK);
    }

    private void blockWithItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(NeoforgeTutorial.MODID + ":block/" + deferredBlock.getId().getPath()));
    }

    public void button(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        buttonBlock((ButtonBlock) block.get(), blockTexture(baseBlock.get()));
        blockParentItem(block, baseBlock, "texture", "button_inventory");
    }

    public void fence(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        fenceBlock((FenceBlock) block.get(), blockTexture(baseBlock.get()));
        blockParentItem(block, baseBlock, "texture", "fence_inventory");
    }

    public void wall(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        wallBlock((WallBlock) block.get(), blockTexture(baseBlock.get()));
        blockParentItem(block, baseBlock, "wall", "wall_inventory");
    }

    public void blockParentItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock, String key, String type) {
        itemModels().withExistingParent(block.getId().getPath(), mcLoc("block/" + type))
                .texture(key, ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
}
