package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.WorldSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class Add extends WorldSubCommand {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    private final Message message = Worlds.getMessage();
    public String getName() {
        return "add";
    }
    public String getDescription() {
        return "add existing world";
    }
    public String getSyntax() {
        return "/world add name normal";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 2) {
            message.send(sender,"&cusage: &f/world add world normal");
        }
        if (args.length == 3) {
            if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                if (worldConfig.folderExist(args[1])) {
                    if (!worldConfig.worldExist(args[1])) {
                        message.send(sender, args[1] + "&6 is about to be added");
                        worldConfig.create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                        message.send(sender, args[1] + "&6 is added with environment &f" + World.Environment.valueOf(args[2].toUpperCase()).name().toLowerCase());
                    } else {
                        message.send(sender, args[1] + "&c already exist");
                    }
                }
            } else {
                message.send(sender,"&cYou have to add&f environment&c to add your world");
            }
        }
    }
}