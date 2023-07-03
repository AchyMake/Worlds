package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class Add extends MainSubCommand {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
    public String getName() {
        return "add";
    }
    public String getDescription() {
        return "add existing world";
    }
    public String getSyntax() {
        return "/worlds add name normal";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Worlds.send(sender, "Usage: /worlds add worldName normal");
        }
        if (args.length == 3) {
            if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                if (getWorldConfig().folderExist(args[1])) {
                    if (!getWorldConfig().worldExist(args[1])) {
                        Worlds.send(sender, args[1] + " is about to be added");
                        getWorldConfig().create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                        Worlds.send(sender, args[1] + " is added with environment " + World.Environment.valueOf(args[2].toUpperCase()).name().toLowerCase());
                    } else {
                        Worlds.send(sender, args[1] + " already exist");
                    }
                }
            } else {
                Worlds.send(sender, "You have to add environment to add your world");
            }
        }
    }
}