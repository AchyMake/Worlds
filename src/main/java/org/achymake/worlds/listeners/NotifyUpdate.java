package org.achymake.worlds.listeners;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.net.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record NotifyUpdate(Worlds plugin) implements Listener {
    private UpdateChecker getUpdateChecker() {
        return plugin.getUpdateChecker();
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onNotifyUpdate(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("worlds.event.join.getupdate"))return;
        getUpdateChecker().getUpdate(player);
    }
}