package com.reasure.neoforge_tutorial.item;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.item.custom.ChainsawItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoforgeTutorial.MODID);

    public static final DeferredItem<Item> BLACK_OPAL = ITEMS.registerSimpleItem("black_opal");
    public static final DeferredItem<Item> RAW_BLACK_OPAL = ITEMS.registerSimpleItem("raw_black_opal");

    public static final DeferredItem<Item> CHAINSAW = ITEMS.registerItem("chainsaw", ChainsawItem::new, new Item.Properties().durability(32));

    public static final DeferredItem<Item> TOMATO = ITEMS.registerItem("tomato", properties -> new Item(properties) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
            tooltipComponents.add(Component.translatable("tooltip.neoforge_tutorial.tomato.1"));

            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        }
    }, new Item.Properties().food(ModFoodProperties.TOMATO));

    public static final DeferredItem<Item> FROSTFIRE_ICE = ITEMS.registerItem("frostfire_ice", Item::new, new Item.Properties());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
