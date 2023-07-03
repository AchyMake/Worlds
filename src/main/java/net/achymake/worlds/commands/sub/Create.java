package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class Create extends MainSubCommand {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
    public String getName() {
        return "create";
    }
    public String getDescription() {
        return "create new world";
    }
    public String getSyntax() {
        return "/worlds create name";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Worlds.send(sender,"Usage: /worlds create worldName normal");
        } else if (args.length == 3) {
            if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                if (!getWorldConfig().folderExist(args[1])) {
                    Worlds.send(sender, args[1] + " is about to be created");
                    getWorldConfig().create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                    Worlds.send(sender, args[1] + " created with environment " + World.Environment.valueOf(args[2].toUpperCase()).toString().toLowerCase());
                } else {
                    Worlds.send(sender, args[1] + " already exist");
                }
            } else {
                Worlds.send(sender, "You have to add environment to create your world");
            }
        }
    }
}