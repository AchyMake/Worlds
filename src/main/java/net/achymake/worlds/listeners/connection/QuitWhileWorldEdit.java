package net.achymake.worlds.listeners.connection;

import net.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitWhileWorldEdit implements Listener {
    public QuitWhileWorldEdit(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuitWhileWorldEdit(PlayerQuitEvent event) {
        if (!Worlds.getWorldConfig().getWorldEditors().contains(event.getPlayer()))return;
        Worlds.getWorldConfig().getWorldEditors().remove(event.getPlayer());
    }
}