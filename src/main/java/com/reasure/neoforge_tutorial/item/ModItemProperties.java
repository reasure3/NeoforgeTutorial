package com.reasure.neoforge_tutorial.item;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.component.ModDataComponentTypes;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.DATA_TABLET.get(), ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "on"),
                ((stack, level, entity, seed) -> stack.has(ModDataComponentTypes.FOUND_BLOCK) ? 1f : 0f));
    }
}
