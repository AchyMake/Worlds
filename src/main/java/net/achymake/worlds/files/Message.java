package net.achymake.worlds.files;

import net.achymake.worlds.Worlds;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {
    private final Worlds worlds;
    public Message (Worlds worlds) {
        this.worlds = worlds;
    }
    public void send(CommandSender sender, String message){
        sender.sendMessage(color(message));
    }
    public void sendLog(String message) {
        worlds.getServer().getConsoleSender().sendMessage("[" + worlds.getName() + "] " + message);
    }
    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}