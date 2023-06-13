package net.achymake.worlds.commands;

import net.achymake.worlds.commands.sub.*;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    private final ArrayList<MainSubCommand> mainSubCommands = new ArrayList<>();
    public MainCommand() {
        this.mainSubCommands.add(new Add());
        this.mainSubCommands.add(new Create());
        this.mainSubCommands.add(new Gamerule());
        this.mainSubCommands.add(new PVP());
        this.mainSubCommands.add(new Reload());
        this.mainSubCommands.add(new Remove());
        this.mainSubCommands.add(new Setspawn());
        this.mainSubCommands.add(new Teleport());
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0){
            for (MainSubCommand commands : mainSubCommands){
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
            commands.add("gamerule");
            commands.add("pvp");
            commands.add("reload");
            commands.add("remove");
            commands.add("setspawn");
            commands.add("teleport");
        }
        if (args.length == 2) {
            if (!args[0].equalsIgnoreCase("reload")) {
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
            if (args[0].equalsIgnoreCase("pvp")) {
                commands.add("true");
                commands.add("false");
            }
            if (args[0].equalsIgnoreCase("gamerule")) {
                Collections.addAll(commands, sender.getServer().getWorld(args[1]).getGameRules());
            }
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("gamerule")) {
                commands.add(sender.getServer().getWorld(args[1]).getGameRuleValue(args[2]));
            }
        }
        return commands;
    }
}