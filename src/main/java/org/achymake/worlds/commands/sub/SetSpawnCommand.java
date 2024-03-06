package org.achymake.worlds.commands.sub;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.commands.WorldsSubCommand;
import org.achymake.worlds.data.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends WorldsSubCommand {
    private Worlds getPlugin() {
        return Worlds.getInstance();
    }
    private Message getMessage() {
        return getPlugin().getMessage();
    }
    public String getName() {
        return "setspawn";
    }
    public String getDescription() {
        return "set world spawn";
    }
    public String getSyntax() {
        return "/worlds setspawn";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("worlds.command.world.setspawn")) {
                if (args.length == 1) {
                    player.getWorld().setSpawnLocation(player.getLocation());
                    getMessage().send(player, player.getWorld().getName() + "&6 changed spawn point");
                }
            }
        }
    }
}