package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.WorldSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Setspawn extends WorldSubCommand {
    private Message getMessage() {
        return Worlds.getMessage();
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
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                player.getWorld().setSpawnLocation(player.getLocation());
                getMessage().send(player, player.getWorld().getName() + "&6 changed spawn point");
            }
        }
    }
}