package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PVP extends MainSubCommand {
    private Server getHost() {
        return Worlds.getHost();
    }
    public String getName() {
        return "pvp";
    }
    public String getDescription() {
        return "reload config";
    }
    public String getSyntax() {
        return "/worlds pvp world true";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                if (Worlds.worldExist(player.getWorld().getName())) {
                    Worlds.setPVP(player.getWorld(), !Worlds.isPVP(player.getWorld()));
                    if (Worlds.isPVP(player.getWorld())){
                        Worlds.send(player, player.getWorld().getName() + "&6 is now pvp mode");
                    } else {
                        Worlds.send(player, player.getWorld().getName() + "&6 is no longer pvp mode");
                    }
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                if (Worlds.worldExist(args[1])) {
                    Worlds.setPVP(player.getWorld(), !Worlds.isPVP(player.getWorld()));
                    if (Worlds.isPVP(player.getWorld())) {
                        Worlds.send(player, args[1] + "&6 is now pvp mode");
                    } else {
                        Worlds.send(player, args[1] + "&6 is no longer pvp mode");
                    }
                }
            }
            if (args.length == 3) {
                if (Worlds.worldExist(args[1])) {
                    Worlds.setPVP(getHost().getWorld(args[1]), Boolean.valueOf(args[2]));
                    if (Worlds.isPVP(getHost().getWorld(args[1]))) {
                        Worlds.send((Player) sender, args[1] + "&6 is now pvp mode");
                    } else {
                        Worlds.send((Player) sender, args[1] + "&6 is no longer pvp mode");
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                if (Worlds.worldExist(args[1])) {
                    Worlds.setPVP(getHost().getWorld(args[1]), !Worlds.isPVP(getHost().getWorld(args[1])));
                    if (Worlds.isPVP(getHost().getWorld(args[1]))) {
                        Worlds.send((ConsoleCommandSender) sender, args[1] + " is now pvp mode");
                    } else {
                        Worlds.send((ConsoleCommandSender) sender, args[1] + " is no longer pvp mode");
                    }
                }
            }
            if (args.length == 3) {
                if (Worlds.worldExist(args[1])) {
                    Worlds.setPVP(getHost().getWorld(args[1]), Boolean.valueOf(args[2]));
                    if (Worlds.isPVP(getHost().getWorld(args[1]))) {
                        Worlds.send((ConsoleCommandSender) sender, args[1] + " is now pvp mode");
                    } else {
                        Worlds.send((ConsoleCommandSender) sender, args[1] + " is no longer pvp mode");
                    }
                }
            }
        }
    }
}