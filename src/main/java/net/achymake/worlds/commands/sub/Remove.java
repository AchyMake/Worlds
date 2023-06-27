package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.command.CommandSender;

import java.io.File;

public class Remove extends MainSubCommand {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
    private Message getMessage() {
        return Worlds.getMessage();
    }
    public String getName() {
        return "remove";
    }
    public String getDescription() {
        return "save and remove world";
    }
    public String getSyntax() {
        return "/world remove name";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 1) {
            getMessage().send(sender, "&cusage: &f/world remove world");
        }
        if (args.length == 2) {
            String worldName = args[1];
            if (getWorldConfig().worldExist(worldName)) {
                File file = new File(Worlds.getInstance().getDataFolder(), "worlds/" + worldName + ".yml");
                if (file.exists()) {
                    file.delete();
                }
                sender.getServer().unloadWorld(worldName, true);
                getMessage().send(sender, worldName + "&6 is saved and removed");
            } else {
                getMessage().send(sender, worldName + "&c does not exist");
            }
        }
    }
}