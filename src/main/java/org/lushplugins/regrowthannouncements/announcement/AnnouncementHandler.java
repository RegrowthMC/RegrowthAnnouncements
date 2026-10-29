package org.lushplugins.regrowthannouncements.announcement;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitTask;
import org.lushplugins.lushlib.config.YamlUtils;
import org.lushplugins.regrowthannouncements.RegrowthAnnouncements;
import org.lushplugins.regrowthannouncements.config.MessageConfig;

import java.util.List;
import java.util.Random;

public class AnnouncementHandler {
    private static final Random RANDOM = new Random();

    private final boolean random;
    private final int interval;
    private final List<String> messageIds;
    private int nextMessageIndex = 0;
    private BukkitTask task;

    public AnnouncementHandler(ConfigurationSection config) {
        this.random = config.getBoolean("random");
        this.interval = config.getInt("interval", 600) * 20;
        this.messageIds = YamlUtils.getStringList(config, "messages");
    }

    public boolean isRandom() {
        return random;
    }

    public int getInterval() {
        return interval;
    }

    public List<String> getMessageIds() {
        return messageIds;
    }

    public String nextMessageId() {
        if (random) {
            return messageIds.get(RANDOM.nextInt(messageIds.size()));
        } else {
            String messageId = messageIds.get(nextMessageIndex);
            nextMessageIndex = (nextMessageIndex + 1) % messageIds.size();
            return messageId;
        }
    }

    public void start() {
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(RegrowthAnnouncements.getInstance(), () -> {
            String messageId = nextMessageId();
            MessageConfig message = RegrowthAnnouncements.getInstance().getConfigManager().message(messageId);
            if (message != null) {
                message.broadcast();
            } else {
                RegrowthAnnouncements.getInstance().getLogger().severe("Invalid announcement message defined: '%s'"
                    .formatted(messageId));
            }
        }, this.interval, this.interval);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
