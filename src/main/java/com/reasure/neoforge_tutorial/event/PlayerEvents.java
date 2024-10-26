package com.reasure.neoforge_tutorial.event;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.item.custom.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = NeoforgeTutorial.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void breakBlock(final BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = player.level();
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ItemStack item = serverPlayer.getMainHandItem();
            if (item.getItem() instanceof HammerItem) {
                BlockHitResult hitResult = HammerItem.getOriginBlock(level, serverPlayer);
                BlockPos originPos = event.getPos();
                BlockState originBlock = level.getBlockState(originPos);
                if (hitResult.getType() == HitResult.Type.BLOCK && hitResult.getBlockPos().equals(originPos)) {
                    HammerItem.mineOther(item, level, originPos, serverPlayer);
                    if (!item.isCorrectToolForDrops(originBlock)) event.setCanceled(true);
                }
            }
        }
    }
}
