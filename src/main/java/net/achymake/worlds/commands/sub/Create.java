package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Create extends MainSubCommand {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
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
            Player player = (Player) sender;
            if (args.length == 2) {
                Worlds.send(player,"Usage: /worlds create worldName normal");
            }
            if (args.length == 3) {
                if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                    if (!getWorldConfig().folderExist(args[1])) {
                        Worlds.send(player, args[1] + " is about to be created");
                        getWorldConfig().create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                        Worlds.send(player, args[1] + " created with environment " + World.Environment.valueOf(args[2].toUpperCase()).toString().toLowerCase());
                    } else {
                        Worlds.send(player, args[1] + " already exist");
                    }
                } else {
                    Worlds.send(player, "You have to add environment to create your world");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
            if (args.length == 2) {
                Worlds.send(commandSender,"Usage: /worlds create worldName normal");
            }
            if (args.length == 3) {
                if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                    if (!getWorldConfig().folderExist(args[1])) {
                        Worlds.send(commandSender, args[1] + " is about to be created");
                        getWorldConfig().create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                        Worlds.send(commandSender, args[1] + " created with environment " + World.Environment.valueOf(args[2].toUpperCase()).toString().toLowerCase());
                    } else {
                        Worlds.send(commandSender, args[1] + " already exist");
                    }
                } else {
                    Worlds.send(commandSender, "You have to add environment to create your world");
                }
            }
        }
    }
}