package com.reasure.neoforge_tutorial.event;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.entity.ModBlockEntities;
import com.reasure.neoforge_tutorial.block.entity.custom.CrystallizerBlockEntity;
import com.reasure.neoforge_tutorial.item.ModItems;
import com.reasure.neoforge_tutorial.item.ModToolTiers;
import com.reasure.neoforge_tutorial.item.custom.PaxelItem;
import net.minecraft.core.component.DataComponents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

@EventBusSubscriber(modid = NeoforgeTutorial.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void modifyItemComponent(final ModifyDefaultComponentsEvent event) {
        event.modify(ModItems.BLACK_OPAL_PAXEL, builder ->
                builder.set(DataComponents.TOOL, PaxelItem.createToolProperties(ModToolTiers.BLACK_OPAL)));
        event.modify(ModItems.BLACK_OPAL_PAXEL, builder ->
                builder.set(DataComponents.MAX_DAMAGE, (int) (ModToolTiers.BLACK_OPAL.getUses() * 1.5)));
        event.modify(ModItems.BLACK_OPAL_HAMMER, builder ->
                builder.set(DataComponents.MAX_DAMAGE, ModToolTiers.BLACK_OPAL.getUses() * 3));
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.CRYSTALLIZER_BE.get(), CrystallizerBlockEntity::getSidedItemCapability);
    }
}
