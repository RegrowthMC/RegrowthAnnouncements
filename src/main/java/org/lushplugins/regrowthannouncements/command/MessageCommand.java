package org.lushplugins.regrowthannouncements.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.lushplugins.regrowthannouncements.RegrowthAnnouncements;
import org.lushplugins.regrowthannouncements.config.MessageConfig;
import revxrsal.commands.annotation.CommandPlaceholder;
import revxrsal.commands.orphan.OrphanCommand;

@SuppressWarnings("unused")
public record MessageCommand(String id) implements OrphanCommand {

    @CommandPlaceholder
    public void run(CommandSender sender) {
        MessageConfig message = RegrowthAnnouncements.getInstance().getConfigManager().message(id);
        if (message != null) {
            message.send(sender);
        } else {
            sender.sendMessage(Component.text()
                .content("This message couldn't be found")
                .color(TextColor.fromHexString("#ff6969"))
                .build());
        }
    }
}
