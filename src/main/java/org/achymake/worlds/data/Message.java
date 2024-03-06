package org.achymake.worlds.data;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.achymake.worlds.Worlds;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public record Message(Worlds plugin) {
    public void send(ConsoleCommandSender sender, String message) {
        sender.sendMessage(message);
    }
    public void send(Player player, String message) {
        player.sendMessage(addColor(message));
    }
    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(addColor(message)));
    }
    public String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public void sendLog(Level level, String message) {
        plugin.getLogger().log(level, message);
    }
}
