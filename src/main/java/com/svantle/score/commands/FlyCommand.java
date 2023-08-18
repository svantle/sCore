package com.svantle.score.commands;

import com.svantle.score.utils.MessagesHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    private final MessagesHandler messagesHandler;

    public FlyCommand(MessagesHandler messagesHandler) {
        this.messagesHandler = messagesHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("only_players")));
            return true;
        }

        if (!player.hasPermission("score.fly")) {
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("no_permission")));
            return true;
        }

        toggleFly(player);
        return true;
    }

    private void toggleFly(Player player) {
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("flight_disabled")));
        } else {
            player.setAllowFlight(true);
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("flight_enabled")));
        }
    }
}
