package com.svantle.score.utils;

import com.svantle.score.SCore;
import net.kyori.adventure.text.Component;

public class MessagesHandler {
    private final SCore plugin;
    private final Component PLUGIN_PREFIX;

    public MessagesHandler(SCore plugin) {
        this.plugin = plugin;
        this.PLUGIN_PREFIX = Component.text(color(plugin.getMessagesConfig().getString("plugin_prefix", "")));
    }

    public Component prefixed(Component message) {
        return PLUGIN_PREFIX.append(message);
    }

    public Component get(String path) {
        return Component.text(color(plugin.getMessagesConfig().getString(path, "")));
    }

    public Component get(String path, String placeholder, String replacement) {
        String message = plugin.getMessagesConfig().getString(path, "");
        message = message.replace("{" + placeholder + "}", replacement);
        return Component.text(color(message));
    }

    private String color(String message) {
        return message.replace('&', '§');
    }

}
