package com.reasure.neoforge_tutorial.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class HomeCommand {
    public static final String HOME_POS_KEY = NeoforgeTutorial.MODID + ".home_pos";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("home").executes(HomeCommand::goHome)
                        .then(Commands.literal("set").executes(HomeCommand::setHome)));
    }

    public static void saveHome(Player player, BlockPos pos) {
        saveHome(player, new int[]{pos.getX(), pos.getY(), pos.getZ()});
    }

    public static void saveHome(Player player, int[] pos) {
        player.getPersistentData().putIntArray(HOME_POS_KEY, pos);
    }

    public static int[] getHome(Player player) {
        return player.getPersistentData().getIntArray(HOME_POS_KEY);
    }

    private static int setHome(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = checkPlayer(context);
        if (player == null) return -1;

        BlockPos pos = player.blockPosition();
        String posString = String.format("(%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());

        saveHome(player, pos);

        context.getSource().sendSuccess(() -> Component.literal("Home set at " + posString), true);
        return 1;
    }

    private static int goHome(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = checkPlayer(context);
        if (player == null) return -1;

        int[] homePos = getHome(player);

        if (homePos.length < 3) {
            context.getSource().sendFailure(Component.literal("You do not have a home set."));
            return -1;
        }

        player.teleportTo(homePos[0], homePos[1], homePos[2]);
        context.getSource().sendSuccess(() -> Component.literal("Teleported to your home."), true);
        return 1;
    }

    private static ServerPlayer checkPlayer(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();

        if (player == null) {
            context.getSource().sendFailure(Component.literal("You must be a player to use this command."));
        }

        return player;
    }
}
