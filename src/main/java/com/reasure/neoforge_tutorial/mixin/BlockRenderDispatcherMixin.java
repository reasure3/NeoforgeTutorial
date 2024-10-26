package com.reasure.neoforge_tutorial.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.reasure.neoforge_tutorial.item.custom.HammerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderDispatcher.class)
public class BlockRenderDispatcherMixin {
    @Inject(method = "renderBreakingTexture(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/neoforged/neoforge/client/model/data/ModelData;)V", at = @At("HEAD"), cancellable = true)
    public void renderBreakingTexture(BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer, ModelData modelData, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack item = player.getMainHandItem();
            if (item.getItem() instanceof HammerItem && !item.isCorrectToolForDrops(state)) {
                ci.cancel();
            }
        }
    }
}
