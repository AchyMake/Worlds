package org.achymake.worlds.net;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.data.Message;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;

public record UpdateChecker(Worlds plugin) {
    private Message getMessage() {
        return plugin.getMessage();
    }
    public void getUpdate(Player player) {
        if (notifyUpdate()) {
            getLatest((latest) -> {
                if (!plugin.getDescription().getVersion().equals(latest)) {
                    getMessage().send(player, plugin.getDescription().getName() + "&6 has new update:");
                    getMessage().send(player, "-&a https://www.spigotmc.org/resources/106196/");
                }
            });
        }
    }
    public void getUpdate() {
        if (notifyUpdate()) {
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    getLatest((latest) -> {
                        if (plugin.getDescription().getVersion().equals(latest)) {
                            getMessage().sendLog(Level.INFO, "You are using the latest version");
                        } else {
                            getMessage().sendLog(Level.INFO, plugin.getDescription().getName() + " has new update:");
                            getMessage().sendLog(Level.INFO, "- https://www.spigotmc.org/resources/106196/");
                        }
                    });
                }
            });
        }
    }
    public void getLatest(Consumer<String> consumer) {
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
    }
    public boolean notifyUpdate() {
        return plugin.getConfig().getBoolean("notify-update");
    }
}
