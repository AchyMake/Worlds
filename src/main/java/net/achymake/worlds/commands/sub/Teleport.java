package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport extends MainSubCommand {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
    private Message getMessage() {
        return Worlds.getMessage();
    }
    public String getName() {
        return "teleport";
    }
    public String getDescription() {
        return "teleport to different world";
    }
    public String getSyntax() {
        return "/world teleport name";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                getMessage().send(player, "&cusage: &f/world teleport world");
            }
            if (args.length == 2) {
                String worldName = args[1];
                Server server = player.getServer();
                if (getWorldConfig().worldExist(worldName)) {
                    player.teleport(server.getWorld(worldName).getSpawnLocation().add(0.5, 0.0, 0.5));
                    getMessage().send(player,"&6Teleporting to &f" + worldName);
                } else {
                    getMessage().send(player,worldName + "&c does not exist");
                }
            }
        }
    }
}