package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Gamerule extends MainSubCommand {
    private Server getHost() {
        return Worlds.getHost();
    }
    public String getName() {
        return "gamerule";
    }
    public String getDescription() {
        return "change gamerule";
    }
    public String getSyntax() {
        return "/worlds gamerule world gamerule value";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 4) {
                if (Worlds.worldExist(args[1])) {
                    getHost().getWorld(args[1]).setGameRuleValue(args[2], args[3]);
                    Worlds.send((Player) sender, args[1] + "&6 changed&f " + args[2] + "&6 to&f " + args[3]);
                } else {
                    Worlds.send((Player) sender, args[1] + "&c does not exist");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 4) {
                if (Worlds.worldExist(args[1])) {
                    getHost().getWorld(args[1]).setGameRuleValue(args[2], args[3]);
                    Worlds.send((ConsoleCommandSender) sender, args[1] + " changed " + args[2] + " to " + args[3]);
                } else {
                    Worlds.send((ConsoleCommandSender) sender, args[1] + " does not exist");
                }
            }
        }
    }
}