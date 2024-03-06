package org.achymake.worlds.commands;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.commands.sub.*;
import org.achymake.worlds.data.Message;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldsCommand implements CommandExecutor, TabCompleter {
    private Worlds getPlugin() {
        return Worlds.getInstance();
    }
    private Message getMessage() {
        return getPlugin().getMessage();
    }
    private final ArrayList<WorldsSubCommand> worldsSubCommands = new ArrayList<>();
    public WorldsCommand() {
        worldsSubCommands.add(new AddCommand());
        worldsSubCommands.add(new CreateCommand());
        worldsSubCommands.add(new GameruleCommand());
        worldsSubCommands.add(new PVPCommand());
        worldsSubCommands.add(new ReloadCommand());
        worldsSubCommands.add(new RemoveCommand());
        worldsSubCommands.add(new SetSpawnCommand());
        worldsSubCommands.add(new TeleportCommand());
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player player) {
                getMessage().send(player, "&6" + getPlugin().getName() + ":&f " + getPlugin().getDescription().getVersion());
            }
            if (sender instanceof ConsoleCommandSender consoleCommandSender) {
                getMessage().send(consoleCommandSender, getPlugin().getName() + ": " + getPlugin().getDescription().getVersion());
            }
        } else {
            for (WorldsSubCommand commands : worldsSubCommands) {
                if (args[0].equalsIgnoreCase(commands.getName())) {
                    commands.perform(sender, args);
                }
            }
        }
        return true;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (player.hasPermission("worlds.command.world.add")) {
                    commands.add("add");
                }
                if (player.hasPermission("worlds.command.world.create")) {
                    commands.add("create");
                }
                if (player.hasPermission("worlds.command.world.gamerule")) {
                    commands.add("gamerule");
                }
                if (player.hasPermission("worlds.command.world.pvp")) {
                    commands.add("pvp");
                }
                if (player.hasPermission("worlds.command.world.reload")) {
                    commands.add("reload");
                }
                if (player.hasPermission("worlds.command.world.remove")) {
                    commands.add("remove");
                }
                if (player.hasPermission("worlds.command.world.setspawn")) {
                    commands.add("setspawn");
                }
                if (player.hasPermission("worlds.command.world.teleport")) {
                    commands.add("teleport");
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("gamerule") | args[0].equalsIgnoreCase("pvp") | args[0].equalsIgnoreCase("remove") | args[0].equalsIgnoreCase("teleport")) {
                    for (World worlds : sender.getServer().getWorlds()) {
                        commands.add(worlds.getName());
                    }
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("add")) {
                    if (player.hasPermission("worlds.command.world.add") | player.hasPermission("worlds.command.world.remove") | player.hasPermission("worlds.command.world.create")) {
                        commands.add("normal");
                        commands.add("nether");
                        commands.add("the_end");
                    }
                }
                if (args[0].equalsIgnoreCase("create")) {
                    if (player.hasPermission("worlds.command.world.add") | player.hasPermission("worlds.command.world.remove") | player.hasPermission("worlds.command.world.create")) {
                        commands.add("normal");
                        commands.add("nether");
                        commands.add("the_end");
                    }
                }
                if (args[0].equalsIgnoreCase("pvp")) {
                    if (player.hasPermission("worlds.command.world.pvp")) {
                        commands.add("true");
                        commands.add("false");
                    }
                }
                if (args[0].equalsIgnoreCase("gamerule")) {
                    if (player.hasPermission("worlds.command.world.gamerule")) {
                        Collections.addAll(commands, sender.getServer().getWorld(args[1]).getGameRules());
                    }
                }
            }
            if (args.length == 4) {
                if (args[0].equalsIgnoreCase("gamerule")) {
                    if (player.hasPermission("worlds.command.world.gamerule")) {
                        commands.add(sender.getServer().getWorld(args[1]).getGameRuleValue(args[2]));
                    }
                }
            }
        }
        return commands;
    }
}
