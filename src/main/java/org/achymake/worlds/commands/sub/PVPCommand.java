package org.achymake.worlds.commands.sub;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.commands.WorldsSubCommand;
import org.achymake.worlds.data.Message;
import org.achymake.worlds.data.WorldConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PVPCommand extends WorldsSubCommand {
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
        return "pvp";
    }
    public String getDescription() {
        return "PvP settings";
    }
    public String getSyntax() {
        return "/worlds pvp world true";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("worlds.command.world.pvp")) {
                if (args.length == 1) {
                    if (getWorldConfig().worldExist(player.getWorld().getName())) {
                        getWorldConfig().setPVP(player.getWorld(), !getWorldConfig().isPVP(player.getWorld()));
                        if (getWorldConfig().isPVP(player.getWorld())){
                            getMessage().send(player, player.getWorld().getName() + "&6 is now pvp mode");
                        } else {
                            getMessage().send(player, player.getWorld().getName() + "&6 is no longer pvp mode");
                        }
                    }
                }
                if (args.length == 2) {
                    if (getWorldConfig().worldExist(args[1])) {
                        getWorldConfig().setPVP(player.getWorld(), !getWorldConfig().isPVP(player.getWorld()));
                        if (getWorldConfig().isPVP(player.getWorld())) {
                            getMessage().send(player, args[1] + "&6 is now pvp mode");
                        } else {
                            getMessage().send(player, args[1] + "&6 is no longer pvp mode");
                        }
                    } else {
                        getMessage().send(player, args[1] + "&c does not exist");
                    }
                }
                if (args.length == 3) {
                    if (getWorldConfig().worldExist(args[1])) {
                        getWorldConfig().setPVP(getPlugin().getServer().getWorld(args[1]), Boolean.valueOf(args[2]));
                        if (getWorldConfig().isPVP(getPlugin().getServer().getWorld(args[1]))) {
                            getMessage().send(player, args[1] + "&6 is now pvp mode");
                        } else {
                            getMessage().send(player, args[1] + "&6 is no longer pvp mode");
                        }
                    } else {
                        getMessage().send(player, args[1] + "&c does not exist");
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            if (args.length == 2) {
                if (getWorldConfig().worldExist(args[1])) {
                    getWorldConfig().setPVP(getPlugin().getServer().getWorld(args[1]), !getWorldConfig().isPVP(getPlugin().getServer().getWorld(args[1])));
                    if (getWorldConfig().isPVP(getPlugin().getServer().getWorld(args[1]))) {
                        getMessage().send(consoleCommandSender, args[1] + " is now pvp mode");
                    } else {
                        getMessage().send(consoleCommandSender, args[1] + " is no longer pvp mode");
                    }
                } else {
                    getMessage().send(consoleCommandSender, args[1] + " does not exist");
                }
            }
            if (args.length == 3) {
                if (getWorldConfig().worldExist(args[1])) {
                    getWorldConfig().setPVP(getPlugin().getServer().getWorld(args[1]), Boolean.valueOf(args[2]));
                    if (getWorldConfig().isPVP(getPlugin().getServer().getWorld(args[1]))) {
                        getMessage().send(consoleCommandSender, args[1] + " is now pvp mode");
                    } else {
                        getMessage().send(consoleCommandSender, args[1] + " is no longer pvp mode");
                    }
                } else {
                    getMessage().send(consoleCommandSender, args[1] + " does not exist");
                }
            }
        }
    }
}