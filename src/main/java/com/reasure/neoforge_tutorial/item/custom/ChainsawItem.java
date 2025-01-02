package com.reasure.neoforge_tutorial.item.custom;

import com.reasure.neoforge_tutorial.component.ModDataComponentTypes;
import com.reasure.neoforge_tutorial.sound.ModSounds;
import com.reasure.neoforge_tutorial.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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

                // player 부분 의미 (첫번째 파라미터)
                // ServerLevel -> 해당 플레이어 제외하고 전체 발송
                // ClientLevel -> 클라이언트의 플레이어와 전달받은 플레이어가 같을 경우에만 해당 플레이어만 제생. 그 외는 재생하지 않음
                context.getLevel().playSound(null, context.getPlayer().blockPosition(), ModSounds.CHAINSAW_CUT.get(), SoundSource.PLAYERS, 1f, 1f);

                ((ServerLevel) context.getLevel()).sendParticles(ParticleTypes.SMOKE, context.getClickedPos().getX() + 0.5f, context.getClickedPos().getY() + 1.0f, context.getClickedPos().getZ() + 0.5f,
                        25, 0.0, 0.05, 0.0, 0.15f);

                context.getItemInHand().set(ModDataComponentTypes.COORDINATES, context.getClickedPos());
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        if (!level.isClientSide()) {
            context.getLevel().playSound(null, context.getPlayer().blockPosition(), ModSounds.CHAINSAW_PULL.get(), SoundSource.PLAYERS, 1f, 1f);
            return InteractionResult.FAIL;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.neoforge_tutorial.chainsaw.tooltip.1"));
            tooltipComponents.add(Component.translatable("tooltip.neoforge_tutorial.chainsaw.tooltip.2"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.neoforge_tutorial.chainsaw.tooltip.shift"));
        }

        if (stack.has(ModDataComponentTypes.COORDINATES.get())) {
            tooltipComponents.add(Component.literal("Last Tree was chopped at " + stack.get(ModDataComponentTypes.COORDINATES)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
