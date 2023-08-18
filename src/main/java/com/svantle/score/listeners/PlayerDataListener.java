package com.svantle.score.listeners;

import com.svantle.score.SCore;
import com.svantle.score.utils.MessagesHandler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerDataListener implements Listener {
    private final SCore plugin;
    private final MessagesHandler messagesHandler;

    public PlayerDataListener(SCore plugin) {
        this.plugin = plugin;
        this.messagesHandler = plugin.getMessagesHandler();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        File dataFolder = plugin.getDataFolder();
        File playerFile = new File(dataFolder, "playerdata.yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);

        boolean wasFlying = playerData.getBoolean(player.getUniqueId().toString() + ".flight", false);

        player.setAllowFlight(wasFlying);
        if (wasFlying) {
            player.setFlying(true);
            player.sendMessage(messagesHandler.prefixed(messagesHandler.get("flight_enabled")));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        File dataFolder = plugin.getDataFolder();
        File playerFile = new File(dataFolder, "playerdata.yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);

        playerData.set(player.getUniqueId().toString() + ".flight", player.getAllowFlight());

        try {
            playerData.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
