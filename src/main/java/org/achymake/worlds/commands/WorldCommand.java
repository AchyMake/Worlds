package org.achymake.worlds.commands;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.data.Message;
import org.achymake.worlds.handlers.CreatorHandler;
import org.achymake.worlds.handlers.WorldHandler;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabCompleter {
    private Worlds getInstance() {
        return Worlds.getInstance();
    }
    private CreatorHandler getCreator() {
        return getInstance().getCreatorHandler();
    }
    private WorldHandler getWorldHandler() {
        return getInstance().getWorldHandler();
    }
    private Message getMessage() {
        return getInstance().getMessage();
    }
    public WorldCommand() {
        getInstance().getCommand("world").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("setspawn")) {
                    var world = player.getWorld();
                    world.setSpawnLocation(player.getLocation());
                    player.sendMessage(getMessage().get("commands.world.setspawn", world.getName()));
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("add")) {
                    if (getWorldHandler().get(args[1]) == null) {
                        if (getWorldHandler().getWorldFolder(args[1]).exists()) {
                            player.sendMessage(getMessage().get("world-creator.post", args[1]));
                            var info = getCreator().add(args[1]);
                            player.sendMessage(getMessage().get("world-creator.title", info.getName()));
                            player.sendMessage(getMessage().get("world-creator.environment", info.getEnvironment().name()));
                            player.sendMessage(getMessage().get("world-creator.seed", String.valueOf(info.getSeed())));
                        } else player.sendMessage(getMessage().get("error.world.folder.invalid", args[1]));
                    } else player.sendMessage(getMessage().get("error.world.exists", args[1]));
                    return true;
                } else if (args[0].equalsIgnoreCase("info")) {
                    var world = getWorldHandler().get(args[1]);
                    if (world != null) {
                        player.sendMessage(getMessage().get("commands.world.info.title", world.getName()));
                        player.sendMessage(getMessage().get("commands.world.info.pvp", String.valueOf(world.getPVP())));
                        player.sendMessage(getMessage().get("commands.world.info.difficulty", getMessage().toTitleCase(world.getDifficulty().name())));
                        player.sendMessage(getMessage().get("commands.world.info.seed", String.valueOf(world.getSeed())));
                        player.sendMessage(getMessage().get("commands.world.info.environment", getMessage().toTitleCase(world.getEnvironment().name())));
                        player.sendMessage(getMessage().get("commands.world.info.gamerule.title"));
                        for (var gameRules : world.getGameRules()) {
                            player.sendMessage(getMessage().get("commands.world.info.gamerule.listed", gameRules, world.getGameRuleValue(gameRules)));
                        }
                    } else player.sendMessage(getMessage().get("error.world.invalid", args[1]));
                    return true;
                } else if (args[0].equalsIgnoreCase("remove")) {
                    var world = getWorldHandler().get(args[1]);
                    if (world != null) {
                        getWorldHandler().remove(world.getName());
                        player.sendMessage(getMessage().get("commands.world.remove", args[1]));
                    } else player.sendMessage(getMessage().get("error.world.invalid", args[1]));
                    return true;
                } else if (args[0].equalsIgnoreCase("teleport")) {
                    var world = getWorldHandler().get(args[1]);
                    if (world != null) {
                        getMessage().sendActionBar(player, getMessage().get("events.teleport.success", args[1]));
                        player.teleport(world.getSpawnLocation());
                    } else player.sendMessage(getMessage().get("error.world.invalid", args[1]));
                    return true;
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (!getWorldHandler().getWorldFolder(args[1]).exists()) {
                        player.sendMessage(getMessage().get("world-creator.post", args[1]));
                        var info = getCreator().create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                        player.sendMessage(getMessage().get("world-creator.title", info.getName()));
                        player.sendMessage(getMessage().get("world-creator.environment", info.getEnvironment().name()));
                        player.sendMessage(getMessage().get("world-creator.seed", String.valueOf(info.getSeed())));
                    } else player.sendMessage(getMessage().get("error.world.folder.exists", args[1]));
                    return true;
                } else if (args[0].equalsIgnoreCase("difficulty")) {
                    var world = getWorldHandler().get(args[1]);
                    if (world != null) {
                        if (getWorldHandler().setDifficulty(args[1], args[2])) {
                            player.sendMessage(getMessage().get("commands.world.difficulty.success", world.getName(), getMessage().toTitleCase(args[2])));
                        } else player.sendMessage(getMessage().get("commands.world.difficulty.invalid", args[2]));
                    } else player.sendMessage(getMessage().get("error.world.invalid", args[1]));
                    return true;
                }
            } else if (args.length == 4) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (args[3].equalsIgnoreCase("random")) {
                        if (!getWorldHandler().getWorldFolder(args[1]).exists()) {
                            player.sendMessage(getMessage().get("creator.post", args[1]));
                            var info = getCreator().createRandom(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                            player.sendMessage(getMessage().get("creator.title", info.getName()));
                            player.sendMessage(getMessage().get("creator.environment", info.getEnvironment().name()));
                            player.sendMessage(getMessage().get("creator.seed", String.valueOf(info.getSeed())));
                        } else player.sendMessage(getMessage().get("error.world.folder.exists", args[1]));
                    } else if (!getWorldHandler().getWorldFolder(args[1]).exists()) {
                        player.sendMessage(getMessage().get("creator.post", args[1]));
                        var info = getCreator().create(args[1], World.Environment.valueOf(args[2].toUpperCase()), Long.parseLong(args[3]));
                        player.sendMessage(getMessage().get("creator.title", info.getName()));
                        player.sendMessage(getMessage().get("creator.environment", info.getEnvironment().name()));
                        player.sendMessage(getMessage().get("creator.seed", String.valueOf(info.getSeed())));
                    } else player.sendMessage(getMessage().get("error.world.folder.exists", args[1]));
                    return true;
                } else if (args[0].equalsIgnoreCase("gamerule")) {
                    var world = getWorldHandler().get(args[1]);
                    if (world != null) {
                        world.setGameRuleValue(args[2], args[3]);
                        player.sendMessage(getMessage().get("commands.world.gamerule.changed", args[1], args[2], args[3]));
                    } else player.sendMessage(getMessage().get("error.world.invalid", args[1]));
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                commands.add("add");
                commands.add("create");
                commands.add("difficulty");
                commands.add("info");
                commands.add("gamerule");
                commands.add("remove");
                commands.add("setspawn");
                commands.add("teleport");
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("gamerule") ||
                        args[0].equalsIgnoreCase("remove") ||
                        args[0].equalsIgnoreCase("teleport") ||
                        args[0].equalsIgnoreCase("difficulty") ||
                        args[0].equalsIgnoreCase("info")) {
                    commands.addAll(getWorldHandler().getListed());
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("create")) {
                    commands.add("normal");
                    commands.add("nether");
                    commands.add("the_end");
                } else if (args[0].equalsIgnoreCase("difficulty")) {
                    if (getWorldHandler().get(args[1]) != null) {
                        commands.add("peaceful");
                        commands.add("easy");
                        commands.add("normal");
                        commands.add("hard");
                    }
                } else if (args[0].equalsIgnoreCase("gamerule")) {
                    Collections.addAll(commands, getWorldHandler().get(args[1]).getGameRules());
                }
            } else if (args.length == 4) {
                if (args[0].equalsIgnoreCase("gamerule")) {
                    commands.add(getWorldHandler().get(args[1]).getGameRuleValue(args[2]));
                } else if (args[0].equalsIgnoreCase("create")) {
                    commands.add("random");
                }
            }
        }
        return commands;
    }
}