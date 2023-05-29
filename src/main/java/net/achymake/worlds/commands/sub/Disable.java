package net.achymake.worlds.commands.sub;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.commands.WorldSubCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.command.CommandSender;

public class Disable extends WorldSubCommand {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    private final Message message = Worlds.getMessage();
    public String getName() {
        return "disable";
    }
    public String getDescription() {
        return "reload config";
    }
    public String getSyntax() {
        return "/world disable world events/physicals/spawn entity true/false";
    }
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 5) {
            if (worldConfig.worldExist(args[1])) {
                if (args[2].equalsIgnoreCase("events")) {
                    worldConfig.setBoolean(args[1], "disable." + args[2] + "." + args[3].toUpperCase(), Boolean.valueOf(args[4]));
                    message.send(sender, args[1] + "&6 disable&f " + args[3] + " " + args[2] + "&6 is set to&f " + args[4]);
                }
                if (args[2].equalsIgnoreCase("physicals")) {
                    worldConfig.setBoolean(args[1], "disable." + args[2] + "." + args[3].toUpperCase(), Boolean.valueOf(args[4]));
                    message.send(sender, args[1] + "&6 disable&f " + args[3] + " " + args[2] + "&6 is set to&f " + args[4]);
                }
                if (args[2].equalsIgnoreCase("spawn")) {
                    worldConfig.setBoolean(args[1], "disable." + args[2] + "." + args[3].toUpperCase(), Boolean.valueOf(args[4]));
                    message.send(sender, args[1] + "&6 disable&f " + args[3] + " " + args[2] + "&6 is set to&f " + args[4]);
                }
            }
        }
        if (args.length == 4) {
            if (worldConfig.worldExist(args[1])) {
                if (args[2].equalsIgnoreCase("redstone")) {
                    worldConfig.setBoolean(args[1], "disable.redstone", Boolean.valueOf(args[3]));
                    message.send(sender, args[1] + "&6 disable&f " + args[2] + "&6 is set to&f " + args[3]);
                }
            }
        }
    }
}