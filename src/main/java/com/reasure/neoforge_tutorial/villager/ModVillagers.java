package com.reasure.neoforge_tutorial.villager;

import com.google.common.collect.ImmutableSet;
import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.ModBlocks;
import com.reasure.neoforge_tutorial.sound.ModSounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    // 주민이 관심을 가지는 블록 (직업 블록, 종 등등)
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, NeoforgeTutorial.MODID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, NeoforgeTutorial.MODID);

    public static final DeferredHolder<PoiType, PoiType> MAGIC_POI = POI_TYPES.register("magic_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.MAGIC_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final DeferredHolder<VillagerProfession, VillagerProfession> KAUPENGER = VILLAGER_PROFESSIONS.register("kaupenger",
            () -> new VillagerProfession("kaupenger",
                    holder -> holder.value() == MAGIC_POI.get(),
                    holder -> holder.value() == MAGIC_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(),
                    ModSounds.MAGIC_BLOCK_PLACE.get()));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
