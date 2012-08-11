package org.hopto.seed419.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.hopto.seed419.CreeperNotify;
import org.hopto.seed419.file.Config;

public class CreeperPowerupListener implements Listener {


    private CreeperNotify cn;
    private final String alert = ChatColor.YELLOW + "[" + ChatColor.GREEN + "Creeper Alert" + ChatColor.YELLOW + "]";


    public CreeperPowerupListener(CreeperNotify cn) {
        this.cn = cn;
    }

    @EventHandler
    public void onCreeperTarget(final EntityTargetLivingEntityEvent event) {
        if (event.getEntity().getType() == EntityType.CREEPER  && event.getTarget() instanceof Player) {
            final Player player = (Player) event.getTarget();
            if (cn.getConfig().getBoolean(Config.useCreeperRadar) && player.getInventory().contains(Material.getMaterial(cn.getConfig().getInt(Config.radarToolID)))) {
                if (player.hasPermission("CreeperNotify.notify") || !cn.getConfig().getBoolean(Config.usePerms)) {
                    player.sendMessage(alert);
                }
                if (cn.getConfig().getInt(Config.radarToolID) == 345) {
                    scheduleTask(event, player);
                }
            } else if (!cn.getConfig().getBoolean(Config.useCreeperRadar) && (player.hasPermission("CreeperNotify.notify") || !cn.getConfig().getBoolean(Config.usePerms))){
                player.sendMessage(alert);
                if (player.getInventory().contains(Material.getMaterial(cn.getConfig().getInt(Config.radarToolID)))) {
                    player.setCompassTarget(event.getEntity().getLocation());
                }
            }
        }
    }

    public void scheduleTask(final EntityTargetLivingEntityEvent event, final Player player) {
        cn.getServer().getScheduler().scheduleSyncDelayedTask(cn, new Runnable() {
            @Override
            public void run() {
                if (event.getTarget().equals(player) && !event.getEntity().isDead() && event.getTarget().getNearbyEntities(15.0, 15.0, 15.0).contains(event.getEntity())) {
                    player.setCompassTarget(event.getEntity().getLocation());
                    scheduleTask(event, player);
                } else {
                    player.setCompassTarget(player.getBedSpawnLocation());
                }
            }
        });
    }
 }
