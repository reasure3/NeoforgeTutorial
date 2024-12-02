package com.reasure.neoforge_tutorial.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.client.render.RenderUtils;
import com.reasure.neoforge_tutorial.item.ModItems;
import com.reasure.neoforge_tutorial.item.custom.HammerItem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;

@EventBusSubscriber(modid = NeoforgeTutorial.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class RenderEvent {
    @SubscribeEvent
    public static void onDrawBlock(final RenderHighlightEvent.Block event) {
        BlockHitResult target = event.getTarget();
        if (target.getType() != HitResult.Type.MISS) {
            Camera camera = event.getCamera();
            if (camera.getEntity() instanceof Player player) {
                ItemStack item = player.getMainHandItem();
                if (item.getItem() instanceof HammerItem) {
                    BlockPos originPos = target.getBlockPos();
                    Vec3 cameraPos = camera.getPosition();
                    Level level = player.level();

                    MultiBufferSource bufferSource = event.getMultiBufferSource();
                    VertexConsumer lineVertex = bufferSource.getBuffer(RenderType.lines());
                    VertexConsumer breakingVertex = RenderUtils.getBreakingVertex(bufferSource, event.getLevelRenderer().destroyingBlocks.get(player.getId()), originPos);
                    BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
                    PoseStack poseStack = event.getPoseStack();

                    HammerItem.findBlocksExceptOrigin(item, level, player, originPos).forEach(eachPos -> {
                        BlockState state = level.getBlockState(eachPos);
                        if (breakingVertex != null) {
                            RenderUtils.renderBreakingTexture(blockRenderer, poseStack, breakingVertex, cameraPos, eachPos, state, level);
                        }
                        RenderUtils.renderBlockOutline(poseStack, lineVertex, player, cameraPos, eachPos, state);
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public static void onComputeFovModifierEvent(ComputeFovModifierEvent event) {
        if (event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.KAUPEN_BOW.get()) {
            float fovModifier = 1f;
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = (float) ticksUsingItem / 20f;
            if (deltaTicks > 1f) {
                deltaTicks = 1f;
            } else {
                deltaTicks *= deltaTicks;
            }
            fovModifier *= 1f - deltaTicks * 0.15f;
            event.setNewFovModifier(fovModifier);
        }
    }
}
