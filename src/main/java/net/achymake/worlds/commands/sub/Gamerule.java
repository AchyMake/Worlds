package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.WorldSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.command.CommandSender;

public class Gamerule extends WorldSubCommand {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    private final Message message = Worlds.getMessage();
    public String getName() {
        return "gamerule";
    }
    public String getDescription() {
        return "change gamerule";
    }
    public String getSyntax() {
        return "/world gamerule world gamerule value";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 4) {
            if (worldConfig.worldExist(args[1])) {
                sender.getServer().getWorld(args[1]).setGameRuleValue(args[2], args[3]);
                message.send(sender,args[1] + "&6 changed &f" + args[2] + "&6 to &f" + args[3]);
            } else {
                message.send(sender,args[1] + "&c does not exist");
            }
        }
    }
}