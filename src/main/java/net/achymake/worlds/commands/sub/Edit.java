package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.WorldSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Edit extends WorldSubCommand {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    private final Message message = Worlds.getMessage();
    public String getName() {
        return "edit";
    }
    public String getDescription() {
        return "toggles world edit for disabled players";
    }
    public String getSyntax() {
        return "/world edit";
    }
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                if (worldConfig.getWorldEditors().contains(player)) {
                    worldConfig.getWorldEditors().remove(player);
                    message.send(player,"&6You are no longer able to edit worlds");
                } else {
                    worldConfig.getWorldEditors().add(player);
                    message.send(player,"&6You are now able to edit worlds");
                }
            }
            if (args.length == 2) {
                Player player = (Player) sender;
                Player target = player.getServer().getPlayerExact(args[1]);
                if (target != null) {
                    if (worldConfig.getWorldEditors().contains(target)) {
                        worldConfig.getWorldEditors().remove(target);
                        message.send(player,"&6You removed&f " + target.getName() + "&6 from world edit");
                        message.send(target,"&6You are no longer able to edit worlds");
                    } else {
                        worldConfig.getWorldEditors().add(target);
                        message.send(player,"&6You added&f " + target.getName() + "&6 to world edit");
                        message.send(target,"&6You are now able to edit worlds");
                    }
                }
            }
        }
    }
}