package org.achymake.worlds.listeners;

import org.achymake.worlds.UpdateChecker;
import org.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;

public class PlayerJoin implements Listener {
    private Worlds getInstance() {
        return Worlds.getInstance();
    }
    private UpdateChecker getUpdates() {
        return getInstance().getUpdateChecker();
    }
    private PluginManager getManager() {
        return getInstance().getPluginManager();
    }
    public PlayerJoin() {
        getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        getUpdates().getUpdate(event.getPlayer());
    }
}