package com.svantle.score.commands;

import com.svantle.score.utils.MessagesHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorkbenchCommand implements CommandExecutor {

    private final MessagesHandler messagesHandler;

    public WorkbenchCommand(MessagesHandler messagesHandler) {
        this.messagesHandler = messagesHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("only_players")));
            return true;
        }

        if (!player.hasPermission("score.workbench")) {
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("no_permission")));
            return true;
        }

        player.openWorkbench(null, true);
        player.sendMessage(messagesHandler.prefixed(messagesHandler.get("workbench_opened")));

        return true;
    }
}
