package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.WorldSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PVP extends WorldSubCommand {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    private final Message message = Worlds.getMessage();
    public String getName() {
        return "pvp";
    }
    public String getDescription() {
        return "reload config";
    }
    public String getSyntax() {
        return "/world pvp world true";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String worldName = player.getWorld().getName();
                if (worldConfig.worldExist(worldName)) {
                    worldConfig.setPVP(worldName, !worldConfig.isPVP(worldName));
                    if (worldConfig.isPVP(worldName)){
                        message.send(sender, worldName + "&6 is now pvp mode");
                    } else {
                        message.send(sender, worldName + "&6 is no longer pvp mode");
                    }
                }
            }
        }
        if (args.length == 2) {
            String worldName = args[1];
            if (worldConfig.worldExist(worldName)) {
                worldConfig.setPVP(worldName, !worldConfig.isPVP(worldName));
                if (worldConfig.isPVP(worldName)) {
                    message.send(sender, worldName + "&6 is now pvp mode");
                } else {
                    message.send(sender, worldName + "&6 is no longer pvp mode");
                }
            }
        }
        if (args.length == 3) {
            String worldName = args[1];
            boolean value = Boolean.valueOf(args[2]);
            if (worldConfig.worldExist(worldName)) {
                worldConfig.setPVP(worldName, value);
                if (worldConfig.isPVP(worldName)) {
                    message.send(sender, worldName + "&6 is now pvp mode");
                } else {
                    message.send(sender, worldName + "&6 is no longer pvp mode");
                }
            }
        }
    }
}