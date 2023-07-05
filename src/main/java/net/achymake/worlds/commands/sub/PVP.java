package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PVP extends MainSubCommand {
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
                String worldName = player.getWorld().getName();
                if (Worlds.worldExist(worldName)) {
                    Worlds.setPVP(player.getWorld(), !Worlds.isPVP(player.getWorld()));
                    if (Worlds.isPVP(player.getWorld())){
                        Worlds.send(player, worldName + "&6 is now pvp mode");
                    } else {
                        Worlds.send(player, worldName + "&6 is no longer pvp mode");
                    }
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                String worldName = args[1];
                if (Worlds.worldExist(worldName)) {
                    Worlds.setPVP(player.getWorld(), !Worlds.isPVP(player.getWorld()));
                    if (Worlds.isPVP(player.getWorld())) {
                        Worlds.send(player, worldName + "&6 is now pvp mode");
                    } else {
                        Worlds.send(player, worldName + "&6 is no longer pvp mode");
                    }
                }
            }
            if (args.length == 3) {
                Player player = (Player) sender;
                String worldName = args[1];
                World world = Worlds.getInstance().getServer().getWorld(worldName);
                boolean value = Boolean.valueOf(args[2]);
                if (Worlds.worldExist(worldName)) {
                    Worlds.setPVP(world, value);
                    if (Worlds.isPVP(world)) {
                        Worlds.send(player, worldName + "&6 is now pvp mode");
                    } else {
                        Worlds.send(player, worldName + "&6 is no longer pvp mode");
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
                String worldName = args[1];
                World world = Worlds.getInstance().getServer().getWorld(args[1]);
                if (Worlds.worldExist(worldName)) {
                    Worlds.setPVP(world, !Worlds.isPVP(world));
                    if (Worlds.isPVP(world)) {
                        Worlds.send(commandSender, worldName + " is now pvp mode");
                    } else {
                        Worlds.send(commandSender, worldName + " is no longer pvp mode");
                    }
                }
            }
            if (args.length == 3) {
                ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
                String worldName = args[1];
                boolean value = Boolean.valueOf(args[2]);
                if (Worlds.worldExist(worldName)) {
                    World world = Worlds.getInstance().getServer().getWorld(worldName);
                    Worlds.setPVP(world, value);
                    if (Worlds.isPVP(world)) {
                        Worlds.send(commandSender, worldName + " is now pvp mode");
                    } else {
                        Worlds.send(commandSender, worldName + " is no longer pvp mode");
                    }
                }
            }
        }
    }
}