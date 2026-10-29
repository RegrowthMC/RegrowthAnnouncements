package org.lushplugins.regrowthannouncements.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.lushplugins.regrowthannouncements.RegrowthAnnouncements;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@SuppressWarnings("unused")
@Command("regrowthannouncements")
public class AnnouncementsCommand {

    @Subcommand("reload")
    @CommandPermission("regrowthannouncements.reload")
    public void reload(CommandSender sender) {
        RegrowthAnnouncements.getInstance().getConfigManager().reload();

        sender.sendMessage(Component.text()
            .content("RegrowthAnnouncements reloaded!")
            .color(TextColor.fromHexString("#b7faa2"))
            .build());
    }
}
