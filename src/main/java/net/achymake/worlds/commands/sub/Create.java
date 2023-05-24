package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.WorldSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class Create extends WorldSubCommand {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    private final Message message = Worlds.getMessage();
    public String getName() {
        return "create";
    }
    public String getDescription() {
        return "create new world";
    }
    public String getSyntax() {
        return "/world create name";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 2) {
            message.send(sender,"&cusage: &f/world create world normal");
        } else if (args.length == 3) {
            if (World.Environment.valueOf(args[2]).equals(World.Environment.valueOf(args[2]))) {
                if (!worldConfig.folderExist(args[1])) {
                    message.send(sender,args[1] + "&6 is about to be created");
                    worldConfig.create(args[1], World.Environment.valueOf(args[2]));
                    message.send(sender,args[1] + "&6 created with environment &f" + World.Environment.valueOf(args[2]).toString().toLowerCase());
                } else {
                    message.send(sender,args[1] + "&c already exist");
                }
            } else {
                message.send(sender,"&cYou have to add&f environment&c to create your world");
            }
        }
    }
    private void createWorld(CommandSender sender, String worldName, World.Environment environment) {
    }
}