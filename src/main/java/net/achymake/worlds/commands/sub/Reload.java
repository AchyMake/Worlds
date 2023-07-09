package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.MainSubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Reload extends MainSubCommand {
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
        if (sender instanceof Player) {
            if (args.length == 1) {
                Worlds.reload();
                Worlds.send((Player) sender, "&6Worlds:&f files reloaded");
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                Worlds.reload();
                Worlds.send((ConsoleCommandSender) sender, "Worlds: files reloaded");
            }
        }
    }
}