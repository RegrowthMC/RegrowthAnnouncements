package org.lushplugins.regrowthannouncements.config;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.chatcolorhandler.paper.PaperColor;
import org.lushplugins.regrowthannouncements.RegrowthAnnouncements;
import org.lushplugins.regrowthannouncements.command.MessageCommand;
import revxrsal.commands.orphan.Orphans;

public class MessageConfig {
    private final String id;
    private final String content;

    public MessageConfig(String id, ConfigurationSection config) {
        this.id = id;

        if (config.isString("content")) {
            this.content = config.getString("content");
        } else {
            this.content = String.join("<newline>", config.getStringList("content"));
        }

        String command = config.getString("command");
        if (command != null) {
            RegrowthAnnouncements.getInstance().getConfigManager().lampInstance()
                .register(Orphans.path(command)
                    .handler(new MessageCommand(id)));
        }
    }

    public String id() {
        return id;
    }

    public String content() {
        return content;
    }

    public void send(CommandSender sender) {
        PaperColor.handler().sendMessage(sender, content);
    }

    public void broadcast() {
        PaperColor.handler().broadcastMessage(content);
    }
}
