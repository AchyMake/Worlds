package net.achymake.worlds.listeners.connection;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitWhileWorldEdit implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public QuitWhileWorldEdit(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuitWhileWorldEdit(PlayerQuitEvent event) {
        if (!worldConfig.getWorldEditors().contains(event.getPlayer()))return;
        worldConfig.getWorldEditors().remove(event.getPlayer());
    }
}