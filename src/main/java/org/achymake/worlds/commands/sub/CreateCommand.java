package org.achymake.worlds.commands.sub;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.commands.WorldsSubCommand;
import org.achymake.worlds.data.Message;
import org.achymake.worlds.data.WorldConfig;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreateCommand extends WorldsSubCommand {
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
        return "create";
    }
    public String getDescription() {
        return "create new world";
    }
    public String getSyntax() {
        return "/worlds create name";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("worlds.command.world.create")) {
                if (args.length == 2) {
                    getMessage().send(player,"&cUsage:&f /worlds create worldName worldType");
                }
                if (args.length == 3) {
                    if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                        if (getWorldConfig().folderExist(args[1])) {
                            getMessage().send(player, args[1] + "&c already exist");
                        } else {
                            getMessage().send(player, args[1] + "&6 is about to be created");
                            getWorldConfig().create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                            getMessage().send(player, args[1] + "&6 created with environment&f " + World.Environment.valueOf(args[2].toUpperCase()).toString().toLowerCase());
                        }
                    } else {
                        getMessage().send(player, "&cYou have to add environment to create your world");
                    }
                }
                if (args.length == 4) {
                    getMessage().send(player, "arg1: " + args[1]);
                    getMessage().send(player,  " arg2: " + args[2]);
                    getMessage().send(player,  " arg3: " + args[3]);
                    if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                        if (getWorldConfig().folderExist(args[1])) {
                            getMessage().send(player, args[1] + "&c already exist");
                        } else {
                            getMessage().send(player, args[1] + "&6 is about to be created");
                            getWorldConfig().create(args[1], World.Environment.valueOf(args[2].toUpperCase()), Long.valueOf(args[3]));
                            getMessage().send(player, args[1] + "&6 created with environment&f " + World.Environment.valueOf(args[2].toUpperCase()).toString().toLowerCase());
                        }
                    } else {
                        getMessage().send(player, "&cYou have to add environment to create your world");
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            if (args.length == 2) {
                getMessage().send(consoleCommandSender,"Usage: /worlds create worldName normal");
            }
            if (args.length == 3) {
                if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                    if (getWorldConfig().folderExist(args[1])) {
                        getMessage().send(consoleCommandSender, args[1] + " already exist");
                    } else {
                        getMessage().send(consoleCommandSender, args[1] + " is about to be created");
                        getWorldConfig().create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                        getMessage().send(consoleCommandSender, args[1] + " created with environment " + World.Environment.valueOf(args[2].toUpperCase()).toString().toLowerCase());
                    }
                } else {
                    getMessage().send(consoleCommandSender, "You have to add environment to create your world");
                }
            }
        }
    }
}