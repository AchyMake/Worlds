package org.achymake.worlds.commands.sub;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.commands.WorldsSubCommand;
import org.achymake.worlds.data.Message;
import org.achymake.worlds.data.WorldConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class RemoveCommand extends WorldsSubCommand {
    private Worlds getPlugin() {
        return Worlds.getInstance();
    }
    private WorldConfig getWorldConfig() {
        return getPlugin().getWorldConfig();
    }
    private Message getMessage() {
        return getPlugin().getMessage();
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
        if (sender instanceof Player player) {
            if (player.hasPermission("worlds.command.world.remove")) {
                if (args.length == 1) {
                    getMessage().send(player, "&cUsage:&f /worlds remove worldName");
                }
                if (args.length == 2) {
                    if (getWorldConfig().worldExist(args[1])) {
                        remove(args[1]);
                        getMessage().send(player, args[1] + "&6 is saved and removed");
                    } else {
                        getMessage().send(player, args[1] + "&c does not exist");
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            if (args.length == 1) {
                getMessage().send(consoleCommandSender, "Usage: /worlds remove worldName");
            }
            if (args.length == 2) {
                if (getWorldConfig().worldExist(args[1])) {
                    remove(args[1]);
                    getMessage().send(consoleCommandSender, args[1] + " is saved and removed");
                } else {
                    getMessage().send(consoleCommandSender, args[1] + " does not exist");
                }
            }
        }
    }
    private void remove(String worldName) {
        File file = new File(getPlugin().getDataFolder(), "worlds/" + worldName + ".yml");
        if (file.exists()) {
            file.delete();
        }
        getPlugin().getServer().unloadWorld(worldName, true);
    }
}