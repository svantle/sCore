package com.svantle.score.commands;

import com.svantle.score.utils.MessagesHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    private final MessagesHandler messagesHandler;

    public FeedCommand(MessagesHandler messagesHandler) {
        this.messagesHandler = messagesHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            return handleSelfFeed(sender);
        } else if (args.length == 1) {
            return handleFeedOthers(sender, args[0]);
        } else {
            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("feed_usage")));
            return true;
        }
    }

    private boolean handleSelfFeed(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("only_players")));
            return true;
        }

        if (!player.hasPermission("score.feed")) {
            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("no_permission")));
            return true;
        }

        player.setFoodLevel(20);
        player.sendMessage(messagesHandler.prefixed(messagesHandler.get("feed_success")));
        return true;
    }

    private boolean handleFeedOthers(CommandSender sender, String targetName) {
        if (!sender.hasPermission("score.feed.others")) {
            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("no_permission")));
            return true;
        }

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("player_not_found")));
            return true;
        }

        target.setFoodLevel(20);
        target.sendMessage(messagesHandler.prefixed(messagesHandler.get("feed_success")));
        sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("feed_success_others", "{player}", target.getName())));
        return true;
    }
}
