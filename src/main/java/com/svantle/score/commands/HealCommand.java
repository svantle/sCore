package com.svantle.score.commands;

import com.svantle.score.utils.MessagesHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    private final MessagesHandler messagesHandler;

    public HealCommand(MessagesHandler messagesHandler) {
        this.messagesHandler = messagesHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("only_players")));
                return true;
            }

            if (!player.hasPermission("score.heal")) {
                player.sendMessage(messagesHandler.prefixed(messagesHandler.get("no_permission")));
                return true;
            }

            heal(player);
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("heal_success")));
            return true;
        }

        else if (args.length == 1) {
            if (!sender.hasPermission("score.heal.others")) {
                sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("no_permission")));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("player_not_found")));
                return true;
            }

            heal(target);
            target.sendMessage(messagesHandler.prefixed(messagesHandler.get("healed")));

            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("healed_others", "player", target.getName())));
            return true;
        }

        else {
            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("heal_usage")));
            return true;
        }
    }

    private void heal(Player player) {
        player.setHealth(player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue());
    }
}
