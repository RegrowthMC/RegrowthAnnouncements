package org.lushplugins.regrowthannouncements;

import org.bukkit.plugin.java.JavaPlugin;

public final class RegrowthAnnouncements extends JavaPlugin {
    private static RegrowthAnnouncements plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // Enable implementation
    }

    @Override
    public void onDisable() {
        // Disable implementation
    }

    public static RegrowthAnnouncements getInstance() {
        return plugin;
    }
}
