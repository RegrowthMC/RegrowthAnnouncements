package org.lushplugins.regrowthannouncements;

import org.lushplugins.lushlib.utils.plugin.SpigotPlugin;
import org.lushplugins.regrowthannouncements.command.AnnouncementsCommand;
import org.lushplugins.regrowthannouncements.config.ConfigManager;
import revxrsal.commands.bukkit.BukkitLamp;

public final class RegrowthAnnouncements extends SpigotPlugin {
    private static RegrowthAnnouncements plugin;

    private ConfigManager configManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager();
        this.configManager.reload();

        BukkitLamp.builder(this)
            .build()
            .register(new AnnouncementsCommand());
    }

    @Override
    public void onDisable() {
        // Disable implementation
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static RegrowthAnnouncements getInstance() {
        return plugin;
    }
}
