package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.WorldSubCommand;
import net.achymake.worlds.files.Message;
import org.bukkit.command.CommandSender;

public class Reload extends WorldSubCommand {
    private Worlds getPlugin() {
        return Worlds.getInstance();
    }
    private Message getMessage() {
        return Worlds.getMessage();
    }
    public String getName() {
        return "reload";
    }
    public String getDescription() {
        return "reload config";
    }
    public String getSyntax() {
        return "/world reload";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 1) {
            getPlugin().reload();
            getMessage().send(sender,"&6Worlds reloaded");
        }
    }
}