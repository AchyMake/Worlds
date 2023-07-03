package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Gamerule extends MainSubCommand {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
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
            Player player = (Player) sender;
            if (args.length == 4) {
                if (getWorldConfig().worldExist(args[1])) {
                    sender.getServer().getWorld(args[1]).setGameRuleValue(args[2], args[3]);
                    Worlds.send(player, args[1] + "&6 changed&f " + args[2] + "&6 to&f " + args[3]);
                } else {
                    Worlds.send(player, args[1] + "&c does not exist");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender commandSender = (ConsoleCommandSender) sender;
            if (args.length == 4) {
                if (getWorldConfig().worldExist(args[1])) {
                    sender.getServer().getWorld(args[1]).setGameRuleValue(args[2], args[3]);
                    Worlds.send(commandSender, args[1] + " changed " + args[2] + " to " + args[3]);
                } else {
                    Worlds.send(commandSender, args[1] + " does not exist");
                }
            }
        }
    }
}