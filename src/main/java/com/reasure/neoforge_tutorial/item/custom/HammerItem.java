package com.reasure.neoforge_tutorial.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.reasure.neoforge_tutorial.client.event.RenderEvent;
import com.reasure.neoforge_tutorial.event.PlayerEvents;
import com.reasure.neoforge_tutorial.mixin.BlockRenderDispatcherMixin;
import com.reasure.neoforge_tutorial.mixin.BlockStateBaseMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

/**
 * @see BlockStateBaseMixin#getHammerDestroyProgress(Player, BlockGetter, BlockPos, CallbackInfoReturnable)
 * @see BlockRenderDispatcherMixin#renderBreakingTexture(BlockState, BlockPos, BlockAndTintGetter, PoseStack, VertexConsumer, ModelData, CallbackInfo)
 * @see RenderEvent#onDrawBlock(RenderHighlightEvent.Block)
 * @see PlayerEvents#breakBlock(BlockEvent.BreakEvent)
 */
public class HammerItem extends PickaxeItem {
    public HammerItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    public static Stream<BlockPos> findBlocks(ItemStack hammer, Level level, Player player, BlockPos originPos) {
        BlockHitResult blockHitResult = getOriginBlock(level, player);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) return Stream.of();

        Direction direction = blockHitResult.getDirection();
        boolean goX = direction.getStepX() == 0;
        boolean goY = direction.getStepY() == 0;
        boolean goZ = direction.getStepZ() == 0;
        return BlockPos.betweenClosedStream(
                originPos.offset(goX ? -1 : 0, goY ? -1 : 0, goZ ? -1 : 0),
                originPos.offset(goX ? 1 : 0, goY ? 1 : 0, goZ ? 1 : 0)
        ).filter(eachPos -> hammer.isCorrectToolForDrops(level.getBlockState(eachPos)));
    }

    public static Stream<BlockPos> findBlocksExceptOrigin(ItemStack hammer, Level level, Player player, BlockPos originPos) {
        return findBlocks(hammer, level, player, originPos).filter(eachPos -> !eachPos.equals(originPos));
    }

    public static BlockHitResult getOriginBlock(Level level, Player player) {
        double range = player.blockInteractionRange();
        Vec3 eyePos = player.getEyePosition();
        Vec3 rotation = player.getViewVector(1f);
        Vec3 combined = eyePos.add(rotation.scale(range));
        return level.clip(new ClipContext(eyePos, combined, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }

    public static void mineOther(ItemStack hammer, Level level, BlockPos originPos, ServerPlayer player) {
        findBlocksExceptOrigin(hammer, level, player, originPos).forEach(player.gameMode::destroyBlock);
    }

    @Override
    public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player) {
        ItemStack hammer = player.getMainHandItem();
        List<BlockPos> blocks = findBlocks(hammer, level, player, pos).toList();
        return !blocks.isEmpty();
    }
}
