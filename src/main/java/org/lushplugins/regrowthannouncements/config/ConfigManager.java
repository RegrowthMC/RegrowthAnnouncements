package org.lushplugins.regrowthannouncements.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.lushplugins.lushlib.config.YamlUtils;
import org.lushplugins.regrowthannouncements.RegrowthAnnouncements;
import org.lushplugins.regrowthannouncements.announcement.AnnouncementHandler;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;

import java.nio.file.Files;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ConfigManager {
    private final Lamp<?> lampInstance;
    private AnnouncementHandler announcer;
    private Map<String, MessageConfig> messages;

    public ConfigManager() {
        this.lampInstance = BukkitLamp.builder(RegrowthAnnouncements.getInstance())
            .build();

        RegrowthAnnouncements.getInstance().saveDefaultConfig();
        if (!Files.exists(RegrowthAnnouncements.getInstance().getDataPath().resolve("messages"))) {
            RegrowthAnnouncements.getInstance().saveDefaultResource("messages/example.yml");
        }
    }

    public void reload() {
        if (announcer != null) {
            announcer.stop();
            announcer = null;
        }

        RegrowthAnnouncements.getInstance().reloadConfig();
        FileConfiguration config = RegrowthAnnouncements.getInstance().getConfig();

        ConfigurationSection announcementsSection = config.getConfigurationSection("announcements");
        if (announcementsSection != null && announcementsSection.getBoolean("enabled")) {
            this.announcer = new AnnouncementHandler(announcementsSection);
        }

        lampInstance.unregisterAllCommands();
        this.messages = YamlUtils.readConfigsInDirectory(RegrowthAnnouncements.getInstance().getDataPath().resolve("messages")).stream()
            .map(messagesFile -> new MessageConfig(
                messagesFile.first().replace(".yml", ""),
                messagesFile.second()))
            .collect(Collectors.toMap(MessageConfig::id, UnaryOperator.identity()));

        if (announcer != null) {
            announcer.start();
        }
    }

    public Lamp<?> lampInstance() {
        return lampInstance;
    }

    public Set<String> messageIds() {
        return messages.keySet();
    }

    public MessageConfig message(String id) {
        return messages.get(id);
    }
}
