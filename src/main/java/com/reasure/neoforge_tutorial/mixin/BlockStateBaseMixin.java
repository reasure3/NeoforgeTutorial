package com.reasure.neoforge_tutorial.mixin;

import com.reasure.neoforge_tutorial.item.custom.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {
    @Inject(method = "getDestroyProgress", at = @At("RETURN"), cancellable = true)
    public void getHammerDestroyProgress(Player player, BlockGetter getter, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        ItemStack item = player.getMainHandItem();
        if (item.getItem() instanceof HammerItem) {
            Level level = player.level();
            BlockHitResult hitResult = HammerItem.getOriginBlock(level, player);
            if (hitResult.getType() == HitResult.Type.BLOCK && hitResult.getBlockPos().equals(pos)) {
                BlockState originBlock = level.getBlockState(hitResult.getBlockPos());
                HammerItem.findBlocksExceptOrigin(item, level, player, pos)
                        .mapToDouble(findPos -> level.getBlockState(findPos).getDestroyProgress(player, level, findPos))
                        .filter(progress -> progress > 0.0)
                        .min().ifPresent(min -> {
                            boolean canBreakOrigin = item.isCorrectToolForDrops(originBlock);
                            double result = canBreakOrigin ? Math.min(min, cir.getReturnValue()) : min;
                            if (result > 0.0) cir.setReturnValue((float) result);
                        });
            }
        }
    }
}
