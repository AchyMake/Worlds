package org.achymake.worlds.commands.sub;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.commands.WorldsSubCommand;
import org.achymake.worlds.data.Message;
import org.achymake.worlds.data.WorldConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GameruleCommand extends WorldsSubCommand {
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
        return "gamerule";
    }
    public String getDescription() {
        return "change gamerule";
    }
    public String getSyntax() {
        return "/worlds gamerule world gamerule value";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("worlds.command.world.gamerule")) {
                if (args.length == 4) {
                    if (getWorldConfig().worldExist(args[1])) {
                        player.getServer().getWorld(args[1]).setGameRuleValue(args[2], args[3]);
                        getMessage().send(player, args[1] + "&6 changed&f " + args[2] + "&6 to&f " + args[3]);
                    } else {
                        getMessage().send(player, args[1] + "&c does not exist");
                    }
                }
            }
        }
        if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            if (args.length == 4) {
                if (getWorldConfig().worldExist(args[1])) {
                    consoleCommandSender.getServer().getWorld(args[1]).setGameRuleValue(args[2], args[3]);
                    getMessage().send(consoleCommandSender, args[1] + " changed " + args[2] + " to " + args[3]);
                } else {
                    getMessage().send(consoleCommandSender, args[1] + " does not exist");
                }
            }
        }
    }
}