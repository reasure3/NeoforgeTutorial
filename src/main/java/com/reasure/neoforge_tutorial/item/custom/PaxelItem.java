package com.reasure.neoforge_tutorial.item.custom;

import com.google.common.collect.Sets;
import com.reasure.neoforge_tutorial.event.ModEvents;
import com.reasure.neoforge_tutorial.util.ModTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @see ModEvents#modifyItemComponent(ModifyDefaultComponentsEvent)
 */
public class PaxelItem extends DiggerItem {
    public static final Set<ItemAbility> DEFAULT_PAXEL_ACTIONS = Stream.of(
            ItemAbilities.DEFAULT_PICKAXE_ACTIONS,
            ItemAbilities.DEFAULT_AXE_ACTIONS,
            ItemAbilities.DEFAULT_SHOVEL_ACTIONS,
            ItemAbilities.DEFAULT_HOE_ACTIONS,
            ItemAbilities.DEFAULT_SWORD_ACTIONS
    ).flatMap(Set::stream).collect(Collectors.toCollection(Sets::newIdentityHashSet));

    public PaxelItem(Tier tier, Properties properties) {
        super(tier, ModTags.Blocks.MINEABLE_WITH_PAXEL, properties);
    }

    public static Tool createToolProperties(Tier tier) {
        Tool tierTool = tier.createToolProperties(ModTags.Blocks.MINEABLE_WITH_PAXEL);
        Tool swordTool = SwordItem.createToolProperties();
        List<Tool.Rule> rules = new ArrayList<>(tierTool.rules());
        rules.addAll(swordTool.rules());
        return new Tool(List.copyOf(rules), tierTool.defaultMiningSpeed(), tierTool.damagePerBlock());
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Player player = context.getPlayer();

        if (playerHasShieldUseIntent(context)) {
            return InteractionResult.PASS;
        }

        Optional<BlockState> newState = this.evaluateNewBlockState(level, blockpos, player, level.getBlockState(blockpos), context);
        if (newState.isEmpty()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            ItemStack itemInHand = context.getItemInHand();
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockpos, itemInHand);
            }

            level.setBlock(blockpos, newState.get(), 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, newState.get()));

            if (player != null) {
                itemInHand.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static boolean playerHasShieldUseIntent(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            return context.getHand().equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().is(Items.SHIELD) && !player.isSecondaryUseActive();
        }
        return false;
    }

    private Optional<BlockState> evaluateNewBlockState(Level level, BlockPos pos, @Nullable Player player, BlockState state, UseOnContext useOnContext) {
        // AXE
        Optional<BlockState> stripedState = Optional.ofNullable(state.getToolModifiedState(useOnContext, ItemAbilities.AXE_STRIP, false));
        if (stripedState.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
            return stripedState;
        }
        Optional<BlockState> scrapedState = Optional.ofNullable(state.getToolModifiedState(useOnContext, ItemAbilities.AXE_SCRAPE, false));
        if (scrapedState.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!level.isClientSide()) {
                level.levelEvent(player, 3005, pos, 0);
            }
            return scrapedState;
        }
        Optional<BlockState> waxOffState = Optional.ofNullable(state.getToolModifiedState(useOnContext, ItemAbilities.AXE_WAX_OFF, false));
        if (waxOffState.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!level.isClientSide()) {
                level.levelEvent(player, 3004, pos, 0);
            }
            return waxOffState;
        }

        // SHOVEL
        if (useOnContext.getClickedFace() != Direction.DOWN) {
            if (level.getBlockState(pos.above()).isAir()) {
                Optional<BlockState> flattenState = Optional.ofNullable(state.getToolModifiedState(useOnContext, ItemAbilities.SHOVEL_FLATTEN, false));
                if (flattenState.isPresent()) {
                    level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0f, 1.0f);
                    return flattenState;
                }
            }
            Optional<BlockState> douseState = Optional.ofNullable(state.getToolModifiedState(useOnContext, ItemAbilities.SHOVEL_DOUSE, false));
            if (douseState.isPresent()) {
                if (!level.isClientSide()) {
                    level.levelEvent(player, 1009, pos, 0);
                }
                return douseState;
            }
        }

        // HOE
        Optional<BlockState> tillState = Optional.ofNullable(state.getToolModifiedState(useOnContext, ItemAbilities.HOE_TILL, false));
        if (tillState.isPresent()) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            return tillState;
        }

        return Optional.empty();
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
        return DEFAULT_PAXEL_ACTIONS.contains(itemAbility);
    }
}
