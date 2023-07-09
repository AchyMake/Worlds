package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Remove extends MainSubCommand {
    private File getFolder() {
        return Worlds.getFolder();
    }
    private Server getHost() {
        return Worlds.getHost();
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
            if (args.length == 1) {
                Worlds.send((Player) sender, "&cUsage:&f /worlds remove worldName");
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                if (Worlds.worldExist(args[1])) {
                    remove(args[1]);
                    Worlds.send(player, args[1] + "&6 is saved and removed");
                } else {
                    Worlds.send(player, args[1] + "&c does not exist");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
            if (args.length == 1) {
                Worlds.send(commandSender, "Usage: /worlds remove worldName");
            }
            if (args.length == 2) {
                if (Worlds.worldExist(args[1])) {
                    remove(args[1]);
                    Worlds.send(commandSender, args[1] + " is saved and removed");
                } else {
                    Worlds.send(commandSender, args[1] + " does not exist");
                }
            }
        }
    }
    private void remove(String worldName) {
        File file = new File(getFolder(), "worlds/" + worldName + ".yml");
        if (file.exists()) {
            file.delete();
        }
        getHost().unloadWorld(worldName, true);
    }
}