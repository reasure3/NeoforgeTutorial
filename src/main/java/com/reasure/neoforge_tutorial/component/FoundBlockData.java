package com.reasure.neoforge_tutorial.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public record FoundBlockData(BlockState block, BlockPos position) {
    public static final Codec<FoundBlockData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockState.CODEC.fieldOf("block").forGetter(FoundBlockData::block),
                    BlockPos.CODEC.fieldOf("position").forGetter(FoundBlockData::position)
            ).apply(instance, FoundBlockData::new)
    );

    public static final StreamCodec<ByteBuf, FoundBlockData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), FoundBlockData::block,
            BlockPos.STREAM_CODEC, FoundBlockData::position,
            FoundBlockData::new
    );

    public String getOutputString() {
        return block.getBlock().getName().getString() + " at " + "(" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FoundBlockData(BlockState block1, BlockPos position1))) return false;
        return Objects.equals(block, block1) && Objects.equals(position, position1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(block, position);
    }
}
