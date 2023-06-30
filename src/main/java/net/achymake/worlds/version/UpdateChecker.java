package net.achymake.worlds.version;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.Message;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;

public class UpdateChecker {
    private Worlds getPlugin() {
        return Worlds.getInstance();
    }
    private Message getMessage() {
        return Worlds.getMessage();
    }
    private FileConfiguration getConfig() {
        return Worlds.getConfiguration();
    }
    private void getVersion(Consumer<String> consumer) {
        getPlugin().getServer().getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            try {
                InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + 106196)).openStream();
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
        if (getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker()).getVersion((latest) -> {
                getMessage().sendLog(Level.INFO, "Checking latest release");
                if (getPlugin().getDescription().getVersion().equals(latest)) {
                    getMessage().sendLog(Level.INFO, "You are using the latest version");
                } else {
                    getMessage().sendLog(Level.INFO, "New Update: " + latest);
                    getMessage().sendLog(Level.INFO, "Current Version: " + getPlugin().getDescription().getVersion());
                }
            });
        }
    }
    public void sendMessage(Player player) {
        if (getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker()).getVersion((latest) -> {
                if (!getPlugin().getDescription().getVersion().equalsIgnoreCase(latest)) {
                    getMessage().send(player,"&6" + getPlugin().getName() + " Update:&f " + latest);
                    getMessage().send(player,"&6Current Version: &f" + getPlugin().getDescription().getVersion());
                }
            });
        }
    }
}