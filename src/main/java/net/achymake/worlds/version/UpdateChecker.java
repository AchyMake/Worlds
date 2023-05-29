package net.achymake.worlds.version;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.Message;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private final Worlds worlds;
    private final int resourceId;
    public UpdateChecker(Worlds worlds, int resourceId) {
        this.worlds = worlds;
        this.resourceId = resourceId;
    }
    private final Message message = Worlds.getMessage();
    public void getVersion(Consumer<String> consumer) {
        worlds.getServer().getScheduler().runTaskAsynchronously(worlds, () -> {
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
                message.sendLog(e.getMessage());
            }
        });
    }
    public void getUpdate() {
        if (worlds.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(worlds, resourceId)).getVersion((latest) -> {
                if (worlds.getDescription().getVersion().equals(latest)) {
                    message.sendLog("You are using the latest version");
                } else {
                    message.sendLog("New Update: " + latest);
                    message.sendLog("Current Version: " + worlds.getDescription().getVersion());
                }
            });
        }
    }
    public void sendMessage(Player player) {
        if (worlds.getConfig().getBoolean("notify-update.enable")) {
            (new UpdateChecker(worlds, resourceId)).getVersion((latest) -> {
                if (!worlds.getDescription().getVersion().equalsIgnoreCase(latest)) {
                    message.send(player,"&6" + worlds.getName() + " Update:&f " + latest);
                    message.send(player,"&6Current Version: &f" + worlds.getDescription().getVersion());
                }
            });
        }
    }
}