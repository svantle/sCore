package com.svantle.score.listeners;

import com.svantle.score.SCore;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    private final SCore plugin;

    public PlayerLeaveListener(SCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();


        event.setQuitMessage(null);

        Sound leaveSound = Sound.valueOf(plugin.getConfigHandler().getConfig().getString("leave-sound", ""));
        player.playSound(player.getLocation(), leaveSound, 1.0F, 1.0F);

        player.sendMessage(plugin.getMessagesHandler().get("player-leave"));
    }
}
