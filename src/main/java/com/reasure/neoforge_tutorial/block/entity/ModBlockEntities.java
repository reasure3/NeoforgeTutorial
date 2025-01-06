package com.reasure.neoforge_tutorial.block.entity;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.block.entity.custom.CrystallizerBlockEntity;
import com.reasure.neoforge_tutorial.block.entity.custom.PedestalBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NeoforgeTutorial.MODID);

    public static final Supplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BE = BLOCK_ENTITIES.register("pedestal_be", () -> BlockEntityType.Builder.of(
            PedestalBlockEntity::new, ModBlocks.PEDESTAL.get()).build(null)
    );

    public static final Supplier<BlockEntityType<CrystallizerBlockEntity>> CRYSTALLIZER_BE = BLOCK_ENTITIES.register("crystallizer_be", () -> BlockEntityType.Builder.of(
            CrystallizerBlockEntity::new, ModBlocks.CRYSTALLIZER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
