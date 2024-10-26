package com.reasure.neoforge_tutorial.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class RenderUtils {
    public static VertexConsumer getBreakingVertex(MultiBufferSource bufferSource, @Nullable BlockDestructionProgress progress, BlockPos originPos) {
        if (progress == null) return null;
        if (!progress.getPos().equals(originPos)) return null;
        return bufferSource.getBuffer(ModelBakery.DESTROY_TYPES.get(progress.getProgress()));
    }

    public static void renderBreakingTexture(BlockRenderDispatcher renderer, PoseStack poseStack, VertexConsumer breakingConsumer, Vec3 cameraPos, BlockPos blockPos, BlockState state, Level level) {
        poseStack.pushPose();
        poseStack.translate(blockPos.getX() - cameraPos.x, blockPos.getY() - cameraPos.y, blockPos.getZ() - cameraPos.z);
        VertexConsumer textureConsumer = new SheetedDecalTextureGenerator(breakingConsumer, poseStack.last(), 1.0f);
        renderer.renderBreakingTexture(state, blockPos, level, poseStack, textureConsumer, level.getModelData(blockPos));
        poseStack.popPose();
    }

    public static void renderBlockOutline(PoseStack poseStack, VertexConsumer consumer, Entity entity, Vec3 camPos, BlockPos pos, BlockState state) {
        PoseStack.Pose pose = poseStack.last();
        VoxelShape blockShape = state.getShape(entity.level(), pos, CollisionContext.of(entity));
        double dx = pos.getX() - camPos.x;
        double dy = pos.getY() - camPos.y;
        double dz = pos.getZ() - camPos.z;
        blockShape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
                    double normalX = maxX - minX;
                    double normalY = maxY - minY;
                    double normalZ = maxZ - minZ;
                    double distance = Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);
                    normalX /= distance;
                    normalY /= distance;
                    normalZ /= distance;
                    consumer.addVertex(pose, (float) (minX + dx), (float) (minY + dy), (float) (minZ + dz))
                            .setColor(0f, 0f, 0f, 0.4f)
                            .setNormal(pose, (float) normalX, (float) normalY, (float) normalZ);
                    consumer.addVertex(pose, (float) (maxX + dx), (float) (maxY + dy), (float) (maxZ + dz))
                            .setColor(0f, 0f, 0f, 0.4f)
                            .setNormal(pose, (float) normalX, (float) normalY, (float) normalZ);
                }
        );
    }
}
