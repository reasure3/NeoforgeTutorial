package com.reasure.neoforge_tutorial.datagen;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import net.minecraft.data.PackOutput;
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

        stairs(ModBlocks.BLACK_OPAL_STAIRS, ModBlocks.BLACK_OPAL_BLOCK);
        slab(ModBlocks.BLACK_OPAL_SLAB, ModBlocks.BLACK_OPAL_BLOCK);

        pressurePlate(ModBlocks.BLACK_OPAL_PRESSURE_PLATE, ModBlocks.BLACK_OPAL_BLOCK);
        button(ModBlocks.BLACK_OPAL_BUTTON, ModBlocks.BLACK_OPAL_BLOCK);

        blockItem(ModBlocks.BLACK_OPAL_PRESSURE_PLATE);

        fence(ModBlocks.BLACK_OPAL_FENCE, ModBlocks.BLACK_OPAL_BLOCK);
        fenceGate(ModBlocks.BLACK_OPAL_FENCE_GATE, ModBlocks.BLACK_OPAL_BLOCK);
        wall(ModBlocks.BLACK_OPAL_WALL, ModBlocks.BLACK_OPAL_BLOCK);

        door(ModBlocks.BLACK_OPAL_DOOR);
        trapDoor(ModBlocks.BLACK_OPAL_TRAPDOOR);

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
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(blockTexture(deferredBlock.get())));
    }

    private void blockItem(DeferredBlock<Block> deferredBlock, String suffix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(blockTexture(deferredBlock.get()).withSuffix(suffix)));
    }

    public void stairs(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        stairsBlock((StairBlock) block.get(), blockTexture(baseBlock.get()));
        blockItem(block);
    }

    public void slab(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        slabBlock((SlabBlock) block.get(), blockTexture(baseBlock.get()), blockTexture(baseBlock.get()));
        blockItem(block);
    }

    public void pressurePlate(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        pressurePlateBlock((PressurePlateBlock) block.get(), blockTexture(baseBlock.get()));
        blockItem(block);
    }

    public void button(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        buttonBlock((ButtonBlock) block.get(), blockTexture(baseBlock.get()));
        blockParentItem(block, baseBlock, "texture", "button_inventory");
    }

    public void fence(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        fenceBlock((FenceBlock) block.get(), blockTexture(baseBlock.get()));
        blockParentItem(block, baseBlock, "texture", "fence_inventory");
    }

    public void fenceGate(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        fenceGateBlock((FenceGateBlock) block.get(), blockTexture(baseBlock.get()));
        blockItem(block);
    }

    public void wall(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        wallBlock((WallBlock) block.get(), blockTexture(baseBlock.get()));
        blockParentItem(block, baseBlock, "wall", "wall_inventory");
    }

    public void door(DeferredBlock<Block> block) {
        doorBlockWithRenderType((DoorBlock) block.get(),
                blockTexture(block.get()).withSuffix("_bottom"),
                blockTexture(block.get()).withSuffix("_top"),
                "cutout");
        itemModels().basicItem(block.asItem());
    }

    public void trapDoor(DeferredBlock<Block> block) {
        trapdoorBlockWithRenderType((TrapDoorBlock) block.get(), blockTexture(block.get()), true, "cutout");
        blockItem(block, "_bottom");
    }

    public void blockParentItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock, String key, String type) {
        itemModels().withExistingParent(block.getId().getPath(), mcLoc("block/" + type))
                .texture(key, blockTexture(baseBlock.get()));
    }
}
