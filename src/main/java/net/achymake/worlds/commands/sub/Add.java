package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Add extends MainSubCommand {
    public String getName() {
        return "add";
    }
    public String getDescription() {
        return "add existing world";
    }
    public String getSyntax() {
        return "/worlds add name normal";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 2) {
                Worlds.send(player, "&cUsage:&f /worlds add worldName normal");
            }
            if (args.length == 3) {
                if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                    if (Worlds.folderExist(args[1])) {
                        if (!Worlds.worldExist(args[1])) {
                            Worlds.send(player, args[1] + "&6 is about to be added");
                            Worlds.create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                            Worlds.send(player, args[1] + "&6 is added with environment&f " + World.Environment.valueOf(args[2].toUpperCase()).name().toLowerCase());
                        } else {
                            Worlds.send(player, args[1] + "&c already exist");
                        }
                    }
                } else {
                    Worlds.send(player, "&cYou have to add environment to add your world");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
            if (args.length == 2) {
                Worlds.send(commandSender, "Usage: /worlds add worldName normal");
            }
            if (args.length == 3) {
                if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                    if (Worlds.folderExist(args[1])) {
                        if (!Worlds.worldExist(args[1])) {
                            Worlds.send(commandSender, args[1] + " is about to be added");
                            Worlds.create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                            Worlds.send(commandSender, args[1] + " is added with environment " + World.Environment.valueOf(args[2].toUpperCase()).name().toLowerCase());
                        } else {
                            Worlds.send(commandSender, args[1] + " already exist");
                        }
                    }
                } else {
                    Worlds.send(commandSender, "You have to add environment to add your world");
                }
            }
        }
    }
}