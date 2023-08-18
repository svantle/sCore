package com.svantle.score.utils;

import com.svantle.score.SCore;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {

    private final SCore plugin;
    private FileConfiguration config;

    public ConfigHandler(SCore plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

}
