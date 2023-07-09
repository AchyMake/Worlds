package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport extends MainSubCommand {
    private Server getHost() {
        return Worlds.getHost();
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
        if (sender instanceof Player) {
            if (args.length == 1) {
                Worlds.send((Player) sender, "&cUsage:&f /worlds teleport worldName");
            }
            if (args.length == 2) {
                if (Worlds.worldExist(args[1])) {
                    Player player = (Player) sender;
                    player.teleport(getHost().getWorld(args[1]).getSpawnLocation().add(0.5, 0.0, 0.5));
                    Worlds.send(player, "&6Teleporting to&f " + args[1]);
                } else {
                    Worlds.send((Player) sender, args[1] + "&c does not exist");
                }
            }
        }
    }
}