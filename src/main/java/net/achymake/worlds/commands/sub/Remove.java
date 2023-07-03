package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Remove extends MainSubCommand {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
    public String getName() {
        return "remove";
    }
    public String getDescription() {
        return "save and remove world";
    }
    public String getSyntax() {
        return "/worlds remove name";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                Worlds.send(player, "&cUsage:&f /worlds remove worldName");
            }
            if (args.length == 2) {
                String worldName = args[1];
                if (getWorldConfig().worldExist(worldName)) {
                    File file = new File(Worlds.getInstance().getDataFolder(), "worlds/" + worldName + ".yml");
                    if (file.exists()) {
                        file.delete();
                    }
                    sender.getServer().unloadWorld(worldName, true);
                    Worlds.send(player, worldName + "&6 is saved and removed");
                } else {
                    Worlds.send(player, worldName + "&c does not exist");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
            if (args.length == 1) {
                Worlds.send(commandSender, "Usage: /worlds remove worldName");
            }
            if (args.length == 2) {
                String worldName = args[1];
                if (getWorldConfig().worldExist(worldName)) {
                    File file = new File(Worlds.getInstance().getDataFolder(), "worlds/" + worldName + ".yml");
                    if (file.exists()) {
                        file.delete();
                    }
                    sender.getServer().unloadWorld(worldName, true);
                    Worlds.send(commandSender, worldName + " is saved and removed");
                } else {
                    Worlds.send(commandSender, worldName + " does not exist");
                }
            }
        }
    }
}