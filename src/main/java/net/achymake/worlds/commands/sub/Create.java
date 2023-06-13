package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class Create extends MainSubCommand {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
    private Message getMessage() {
        return Worlds.getMessage();
    }
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
            getMessage().send(sender,"&cusage: &f/world create world normal");
        } else if (args.length == 3) {
            if (World.Environment.valueOf(args[2].toUpperCase()).equals(World.Environment.valueOf(args[2].toUpperCase()))) {
                if (!getWorldConfig().folderExist(args[1])) {
                    getMessage().send(sender,args[1] + "&6 is about to be created");
                    getWorldConfig().create(args[1], World.Environment.valueOf(args[2].toUpperCase()));
                    getMessage().send(sender,args[1] + "&6 created with environment &f" + World.Environment.valueOf(args[2].toUpperCase()).toString().toLowerCase());
                } else {
                    getMessage().send(sender,args[1] + "&c already exist");
                }
            } else {
                getMessage().send(sender,"&cYou have to add&f environment&c to create your world");
            }
        }
    }
}