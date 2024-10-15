package com.reasure.neoforge_tutorial.item.custom;

import com.reasure.neoforge_tutorial.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ChainsawItem extends Item {
    public ChainsawItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();

        if (level.getBlockState(context.getClickedPos()).is(ModTags.Blocks.CHAINSAW_CUTABLE_BLOCKS)) {
            if (!level.isClientSide()) {
                level.destroyBlock(context.getClickedPos(), true, context.getPlayer());

                context.getItemInHand().hurtAndBreak(1, (ServerLevel) level, ((ServerPlayer) context.getPlayer()),
                        item -> Objects.requireNonNull(context.getPlayer()).onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.neoforge_tutorial.chainsaw.tooltip.1"));
            tooltipComponents.add(Component.translatable("tooltip.neoforge_tutorial.chainsaw.tooltip.2"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.neoforge_tutorial.chainsaw.tooltip.shift"));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
