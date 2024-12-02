package com.reasure.neoforge_tutorial.item.custom;

import com.reasure.neoforge_tutorial.component.ModDataComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class DataTabletItem extends Item {
    public DataTabletItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack item = player.getItemInHand(usedHand);
        if (item.has(ModDataComponentTypes.FOUND_BLOCK)) {
            item.set(ModDataComponentTypes.FOUND_BLOCK, null);
        }

        return InteractionResultHolder.success(item);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.has(ModDataComponentTypes.FOUND_BLOCK);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(ModDataComponentTypes.FOUND_BLOCK)) {
            //noinspection DataFlowIssue
            tooltipComponents.add(Component.literal(stack.get(ModDataComponentTypes.FOUND_BLOCK).getOutputString()));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
