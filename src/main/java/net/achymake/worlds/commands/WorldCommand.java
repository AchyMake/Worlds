package net.achymake.worlds.commands;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.sub.*;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabCompleter {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    private final ArrayList<WorldSubCommand> worldSubCommands = new ArrayList<>();
    public WorldCommand() {
        this.worldSubCommands.add(new Add());
        this.worldSubCommands.add(new Create());
        this.worldSubCommands.add(new Disable());
        this.worldSubCommands.add(new Edit());
        this.worldSubCommands.add(new Gamerule());
        this.worldSubCommands.add(new PVP());
        this.worldSubCommands.add(new Reload());
        this.worldSubCommands.add(new Remove());
        this.worldSubCommands.add(new Setspawn());
        this.worldSubCommands.add(new Teleport());
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0){
            for (WorldSubCommand commands : worldSubCommands){
                if (args[0].equalsIgnoreCase(commands.getName())){
                    commands.perform(sender,args);
                }
            }
        }
        return true;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            commands.add("add");
            commands.add("create");
            commands.add("disable");
            commands.add("edit");
            commands.add("gamerule");
            commands.add("pvp");
            commands.add("reload");
            commands.add("remove");
            commands.add("setspawn");
            commands.add("teleport");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("edit")) {
                for (Player players : sender.getServer().getOnlinePlayers()) {
                    commands.add(players.getName());
                }
            } else {
                for (World worlds : sender.getServer().getWorlds()) {
                    commands.add(worlds.getName());
                }
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("add")) {
                commands.add("normal");
                commands.add("nether");
                commands.add("the_end");
            }
            if (args[0].equalsIgnoreCase("create")) {
                commands.add("normal");
                commands.add("nether");
                commands.add("the_end");
            }
            if (args[0].equalsIgnoreCase("disable")){
                commands.add("events");
                commands.add("physicals");
                commands.add("spawn");
                commands.add("redstone");
            }
            if (args[0].equalsIgnoreCase("pvp")) {
                commands.add("true");
                commands.add("false");
            }
            if (args[0].equalsIgnoreCase("gamerule")) {
                for (String gamerules : sender.getServer().getWorld(args[1]).getGameRules()) {
                    commands.add(gamerules);
                }
            }
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("disable")) {
                if (args[2].equalsIgnoreCase("events")) {
                    for (EntityType entityType : EntityType.values()) {
                        commands.add(entityType.toString().toLowerCase());
                    }
                }
                if (args[2].equalsIgnoreCase("physicals")) {
                    for (Material material : Material.values()) {
                        commands.add(material.toString().toLowerCase());
                    }
                }
                if (args[2].equalsIgnoreCase("spawn")) {
                    for (EntityType entityType : EntityType.values()) {
                        commands.add(entityType.toString().toLowerCase());
                    }
                }
                if (args[2].equalsIgnoreCase("redstone")) {
                    commands.add(String.valueOf(worldConfig.isRedstoneCancelled(args[1])));
                }
            }
            if (args[0].equalsIgnoreCase("gamerule")) {
                commands.add(sender.getServer().getWorld(args[1]).getGameRuleValue(args[2]));
            }
        }
        if (args.length == 5){
            if (args[0].equalsIgnoreCase("disable")){
                if (args[2].equalsIgnoreCase("events")){
                    commands.add(String.valueOf(Boolean.valueOf(worldConfig.get(args[1]).getBoolean("disable."+args[2]+"."+args[3].toUpperCase()))));
                }
                if (args[2].equalsIgnoreCase("physicals")){
                    commands.add(String.valueOf(Boolean.valueOf(worldConfig.get(args[1]).getBoolean("disable."+args[2]+"."+args[3].toUpperCase()))));
                }
                if (args[2].equalsIgnoreCase("spawn")){
                    commands.add(String.valueOf(Boolean.valueOf(worldConfig.get(args[1]).getBoolean("disable."+args[2]+"."+args[3].toUpperCase()))));
                }
            }
        }
        return commands;
    }
}