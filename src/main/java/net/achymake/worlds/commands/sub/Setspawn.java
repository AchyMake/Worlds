package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Setspawn extends MainSubCommand {
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
            if (args.length == 1) {
                Player player = (Player) sender;
                player.getWorld().setSpawnLocation(player.getLocation());
                Worlds.send(player, player.getWorld().getName() + "&6 changed spawn point");
            }
        }
    }
}