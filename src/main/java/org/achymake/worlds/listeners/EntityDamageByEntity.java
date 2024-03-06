package org.achymake.worlds.listeners;

import org.achymake.worlds.Worlds;
import org.achymake.worlds.data.Message;
import org.achymake.worlds.data.WorldConfig;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Trident;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public record EntityDamageByEntity(Worlds plugin) implements Listener {
    private WorldConfig getWorldConfig() {
        return plugin.getWorldConfig();
    }
    private Message getMessage() {
        return plugin.getMessage();
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (damager instanceof Player player) {
            if (entity instanceof Player target) {
                if (getWorldConfig().isPVP(target.getWorld()))return;
                event.setCancelled(true);
                getMessage().send(player, "&cHey!&7 Sorry but PVP is Disabled in this world!");
            }
        } else if (damager instanceof Arrow arrow) {
            if (arrow.getShooter() instanceof Player shooter) {
                if (entity instanceof Player target) {
                    if (shooter == target)return;
                    if (getWorldConfig().isPVP(target.getWorld()))return;
                    event.setCancelled(true);
                    getMessage().send(shooter, "&cHey!&7 Sorry but PVP is Disabled in this world!");
                }
            }
        } else if (damager instanceof Snowball snowball) {
            if (snowball.getShooter() instanceof Player shooter) {
                if (entity instanceof Player target) {
                    if (shooter == target)return;
                    if (getWorldConfig().isPVP(target.getWorld()))return;
                    event.setCancelled(true);
                    getMessage().send(shooter, "&cHey!&7 Sorry but PVP is Disabled in this world!");
                }
            }
        } else if (damager instanceof SpectralArrow spectralArrow) {
            if (spectralArrow.getShooter() instanceof Player shooter) {
                if (entity instanceof Player target) {
                    if (shooter == target)return;
                    if (getWorldConfig().isPVP(target.getWorld()))return;
                    event.setCancelled(true);
                    getMessage().send(shooter, "&cHey!&7 Sorry but PVP is Disabled in this world!");
                }
            }
        } else if (damager instanceof ThrownPotion thrownPotion) {
            if (thrownPotion.getShooter() instanceof Player shooter) {
                if (entity instanceof Player target) {
                    if (shooter == target)return;
                    if (getWorldConfig().isPVP(target.getWorld()))return;
                    event.setCancelled(true);
                    getMessage().send(shooter, "&cHey!&7 Sorry but PVP is Disabled in this world!");
                }
            }
        } else if (damager instanceof Trident trident) {
            if (trident.getShooter() instanceof Player shooter) {
                if (entity instanceof Player target) {
                    if (shooter == target)return;
                    if (getWorldConfig().isPVP(target.getWorld()))return;
                    event.setCancelled(true);
                    getMessage().send(shooter, "&cHey!&7 Sorry but PVP is Disabled in this world!");
                }
            }
        }
    }
}