package net.achymake.worlds.files;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Message {
    private final Logger logger;
    public Message (Logger logger) {
        this.logger = logger;
    }
    public void send(CommandSender sender, String message) {
        sender.sendMessage(addColor(message));
    }
    public String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public void sendLog(Level level, String message) {
        logger.log(level, message);
    }
}