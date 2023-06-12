package net.achymake.worlds.version;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.Message;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;

public class UpdateChecker {
    private final Worlds plugin;
    private final int resourceId;
    public UpdateChecker(Worlds plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }
    private Message getMessage() {
        return Worlds.getMessage();
    }
    public void getVersion(Consumer<String> consumer) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId)).openStream();
                Scanner scanner = new Scanner(inputStream);
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                    scanner.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
        });
    }
    public void getUpdate() {
        if (plugin.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(plugin, resourceId)).getVersion((latest) -> {
                if (plugin.getDescription().getVersion().equals(latest)) {
                    getMessage().sendLog(Level.INFO, "You are using the latest version");
                } else {
                    getMessage().sendLog(Level.INFO, "New Update: " + latest);
                    getMessage().sendLog(Level.INFO, "Current Version: " + plugin.getDescription().getVersion());
                }
            });
        }
    }
    public void sendMessage(Player player) {
        if (plugin.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(plugin, resourceId)).getVersion((latest) -> {
                if (!plugin.getDescription().getVersion().equalsIgnoreCase(latest)) {
                    getMessage().send(player,"&6" + plugin.getName() + " Update:&f " + latest);
                    getMessage().send(player,"&6Current Version: &f" + plugin.getDescription().getVersion());
                }
            });
        }
    }
}