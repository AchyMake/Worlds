package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Create extends MainSubCommand {
    public String getName() {
        return "create";
    }
    public String getDescription() {
        return "create new world";
    }
    public String getSyntax() {
        return "/worlds create name";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 2) {
                Worlds.send((Player) sender,"&cUsage:&f /worlds create worldName normal");
            }
            if (args.length == 3) {
                if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                    if (!Worlds.folderExist(args[1])) {
                        Worlds.send((Player) sender, args[1] + "&6 is about to be created");
                        Worlds.create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                        Worlds.send((Player) sender, args[1] + "&6 created with environment&f " + World.Environment.valueOf(args[2].toUpperCase()).toString().toLowerCase());
                    } else {
                        Worlds.send((Player) sender, args[1] + "&c already exist");
                    }
                } else {
                    Worlds.send((Player) sender, "&cYou have to add environment to create your world");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                Worlds.send((ConsoleCommandSender) sender,"Usage: /worlds create worldName normal");
            }
            if (args.length == 3) {
                if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                    if (!Worlds.folderExist(args[1])) {
                        Worlds.send((ConsoleCommandSender) sender, args[1] + " is about to be created");
                        Worlds.create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                        Worlds.send((ConsoleCommandSender) sender, args[1] + " created with environment " + World.Environment.valueOf(args[2].toUpperCase()).toString().toLowerCase());
                    } else {
                        Worlds.send((ConsoleCommandSender) sender, args[1] + " already exist");
                    }
                } else {
                    Worlds.send((ConsoleCommandSender) sender, "You have to add environment to create your world");
                }
            }
        }
    }
}