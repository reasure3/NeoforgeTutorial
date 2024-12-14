package com.reasure.neoforge_tutorial.event;

import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.command.HomeCommand;
import com.reasure.neoforge_tutorial.item.ModItems;
import com.reasure.neoforge_tutorial.item.custom.HammerItem;
import com.reasure.neoforge_tutorial.potion.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

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

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Sheep sheep) {
            if (event.getSource().getDirectEntity() instanceof Player player) {
                if (player.getMainHandItem().getItem() == ModItems.METAL_DETECTOR.get()) {
                    player.sendSystemMessage(Component.literal(player.getName().getString() + " just hit a freaking Sheep with a Metal Detector!"));
                }

                if (player.getMainHandItem().getItem() == ModItems.TOMATO.get()) {
                    player.sendSystemMessage(Component.literal(player.getName().getString() + " just hit a freaking Sheep with a tomato!"));
                    sheep.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 50));
                    player.getMainHandItem().shrink(1);
                }

                if (player.getMainHandItem().getItem() == Items.END_ROD) {
                    player.sendSystemMessage(Component.literal(player.getName().getString() + " just hit a freaking Sheep with AN END ROD WHAT?!!"));
                    sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 50));
                    player.getMainHandItem().shrink(1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onToolUse(BlockEvent.BlockToolModificationEvent event) {
        if (event.getItemAbility() == ItemAbilities.AXE_STRIP) {
            BlockState original = event.getFinalState();
            Block block = NeoforgeTutorial.STRIPPABLES.get(original.getBlock());
            if (block != null) {
                event.setFinalState(block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, original.getValue(RotatedPillarBlock.AXIS)));
            }
        }
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addStartMix(Items.SLIME_BALL, ModPotions.SLIMEY_POTION);
        builder.addMix(ModPotions.LONG_SLIMEY_POTION, Items.REDSTONE, ModPotions.SLIMEY_POTION);
    }

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event) {
        HomeCommand.register(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        HomeCommand.saveHome(event.getEntity(), HomeCommand.getHome(event.getOriginal()));
    }
}
