package com.svantle.score.commands;

import com.svantle.score.utils.MessagesHandler;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;

public class FlySpeedCommand implements CommandExecutor {

    private final MessagesHandler messagesHandler;

    public FlySpeedCommand(MessagesHandler messagesHandler) {
        this.messagesHandler = messagesHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(messagesHandler.prefixed(messagesHandler.get("only_players")));
            return true;
        }

        if (!player.hasPermission("score.flyspeed")) {
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("no_permission")));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("flyspeed_usage")));
            return true;
        }

        setPlayerFlySpeed(player, args[0]);

        return true;
    }

    private void setPlayerFlySpeed(Player player, String input) {
        try {
            int speed = Integer.parseInt(input);

            if (speed < 1 || speed > 10) {
                player.sendMessage(messagesHandler.prefixed(messagesHandler.get("flyspeed_invalid")));
                return;
            }

            float actualSpeed = speed / 10.0f;
            player.setFlySpeed(actualSpeed);
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("flyspeed_updated").append(Component.text(String.valueOf(speed), NamedTextColor.GOLD))));

        } catch (NumberFormatException e) {
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("flyspeed_invalid")));
        }
    }
}
