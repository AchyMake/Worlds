package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.command.CommandSender;

public class Reload extends MainSubCommand {
    private Worlds getPlugin() {
        return Worlds.getInstance();
    }
    public String getName() {
        return "reload";
    }
    public String getDescription() {
        return "reload config";
    }
    public String getSyntax() {
        return "/worlds reload";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 1) {
            getPlugin().reload();
            Worlds.send(sender, "Worlds reloaded");
        }
    }
}