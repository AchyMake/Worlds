package org.achymake.worlds.commands.sub;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.commands.WorldsSubCommand;
import org.achymake.worlds.data.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends WorldsSubCommand {
    private Worlds getPlugin() {
        return Worlds.getInstance();
    }
    private Message getMessage() {
        return getPlugin().getMessage();
    }
    public String getName() {
        return "reload";
    }
    public String getDescription() {
        return "reload config";
    }
    public String getSyntax() {
        return "/worlds reload";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("worlds.command.world.reload")) {
                if (args.length == 1) {
                    getPlugin().reload();
                    getMessage().send(player, "&6Worlds:&f reloaded");
                }
            }
        }
        if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            if (args.length == 1) {
                getPlugin().reload();
                getMessage().send(consoleCommandSender, "Worlds: reloaded");
            }
        }
    }
}