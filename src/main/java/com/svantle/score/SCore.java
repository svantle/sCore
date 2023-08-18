package com.svantle.score;

import com.svantle.score.commands.*;
import com.svantle.score.listeners.*;
import com.svantle.score.utils.ConfigHandler;
import com.svantle.score.utils.MessagesHandler;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SCore extends JavaPlugin {

    private ConfigHandler configHandler;
    private MessagesHandler messagesHandler;
    private FileConfiguration messagesConfig;
    private File messagesFile;

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public MessagesHandler getMessagesHandler() {
        return messagesHandler;
    }

    public FileConfiguration getMessagesConfig() {
        if (messagesConfig == null) {
            loadMessages();
        }
        return messagesConfig;
    }

    private void loadMessages() {
        if (messagesFile == null) {
            messagesFile = new File(getDataFolder(), "messages.yml");
        }
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    @Override
    public void onEnable() {

        configHandler = new ConfigHandler(this);
        messagesHandler = new MessagesHandler(this);

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(this), this);


        // Register commands
        this.getCommand("fly").setExecutor(new FlyCommand(this.getMessagesHandler()));
        this.getCommand("flyspeed").setExecutor(new FlySpeedCommand(this.getMessagesHandler()));
        this.getCommand("feed").setExecutor(new FeedCommand(this.getMessagesHandler()));
        this.getCommand("heal").setExecutor(new HealCommand(this.getMessagesHandler()));
        this.getCommand("workbench").setExecutor(new WorkbenchCommand(this.getMessagesHandler()));
    }
}
