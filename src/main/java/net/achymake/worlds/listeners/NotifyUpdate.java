package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.version.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NotifyUpdate implements Listener {
    public NotifyUpdate(Worlds plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onNotifyUpdate(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission("worlds.command"))return;
        new UpdateChecker().sendMessage(event.getPlayer());
    }
}