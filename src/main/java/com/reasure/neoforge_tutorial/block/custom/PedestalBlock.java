package com.reasure.neoforge_tutorial.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PedestalBlock extends Block {
    public static final VoxelShape SHAPE = Shapes.or(
            box(1, 0, 1, 15, 2, 15),
            box(2, 2, 2, 14, 5, 14),
            box(6, 5, 6, 10, 9, 10),
            box(5, 9, 5, 11, 11, 11),
            Shapes.join(
                    box(3, 11, 3, 13, 13, 13),
                    box(4, 12, 4, 12, 13, 12),
                    BooleanOp.ONLY_FIRST
            )
    );

    public PedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
