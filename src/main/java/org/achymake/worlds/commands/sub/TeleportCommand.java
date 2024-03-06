package org.achymake.worlds.commands.sub;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.commands.WorldsSubCommand;
import org.achymake.worlds.data.Message;
import org.achymake.worlds.data.WorldConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand extends WorldsSubCommand {
    private Worlds getPlugin() {
        return Worlds.getInstance();
    }
    private Message getMessage() {
        return getPlugin().getMessage();
    }
    private WorldConfig getWorldConfig() {
        return getPlugin().getWorldConfig();
    }
    public String getName() {
        return "teleport";
    }
    public String getDescription() {
        return "teleport to different world";
    }
    public String getSyntax() {
        return "/worlds teleport name";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("worlds.command.world.teleport")) {
                if (args.length == 1) {
                    getMessage().send(player, "&cUsage:&f /worlds teleport worldName");
                }
                if (args.length == 2) {
                    if (getWorldConfig().worldExist(args[1])) {
                        player.teleport(getPlugin().getServer().getWorld(args[1]).getSpawnLocation().add(0.5, 0.0, 0.5));
                        getMessage().send(player, "&6Teleporting to&f " + args[1]);
                    } else {
                        getMessage().send(player, args[1] + "&c does not exist");
                    }
                }
            }
        }
    }
}