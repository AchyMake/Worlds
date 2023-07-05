package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport extends MainSubCommand {
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
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                Worlds.send(player, "&cUsage:&f /worlds teleport worldName");
            }
            if (args.length == 2) {
                String worldName = args[1];
                Server server = player.getServer();
                if (Worlds.worldExist(worldName)) {
                    player.teleport(server.getWorld(worldName).getSpawnLocation().add(0.5, 0.0, 0.5));
                    Worlds.send(player, "&6Teleporting to&f " + worldName);
                } else {
                    Worlds.send(player, worldName + "&c does not exist");
                }
            }
        }
    }
}