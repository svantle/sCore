package com.svantle.score.listeners;

import com.svantle.score.SCore;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final SCore plugin;

    public PlayerJoinListener(SCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);

        Sound joinSound = Sound.valueOf(plugin.getConfigHandler().getConfig().getString("join-sound", ""));
        player.playSound(player.getLocation(), joinSound, 1.0F, 1.0F);

        player.sendMessage(plugin.getMessagesHandler().get("player-join"));
    }
}
