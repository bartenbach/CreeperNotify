package org.hopto.seed419.creepernotify.listeners;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.hopto.seed419.creepernotify.CreeperNotify;
import org.hopto.seed419.creepernotify.file.Config;

public class CreeperPowerupListener implements Listener {


    private CreeperNotify cn;
    private final String creeperAlert = ChatColor.YELLOW + "[" + ChatColor.GREEN + "Creeper Alert" + ChatColor.YELLOW + "]";
    private final String creeperLost = ChatColor.YELLOW + "[" + ChatColor.DARK_RED + "Creeper Lost" + ChatColor.YELLOW + "]";


    public CreeperPowerupListener(CreeperNotify cn) {
        this.cn = cn;
    }

    @EventHandler
    public void onCreeperTarget(final EntityTargetLivingEntityEvent event) {
        if (event.getEntity().getType() == EntityType.CREEPER  && event.getTarget() instanceof Player) {

            final Player player = (Player) event.getTarget();

            if (cn.getConfig().getBoolean(Config.requireCreeperRadar) && player.getInventory().contains(Material.getMaterial(cn.getConfig().getInt(Config.radarToolID)))) {
                if (cn.hasPerms(player)) {
                    player.sendMessage(creeperAlert);
                    if (cn.getConfig().getBoolean(Config.sound)) {
                        //Useless - Click 1, blaze_shoot, record play
                        //Terrifying - Zombie_Destroy_Door
                        player.playEffect(player.getLocation(), Effect.ZOMBIE_CHEW_IRON_DOOR, 1);
                    }
                }
            } else if (!cn.getConfig().getBoolean(Config.requireCreeperRadar)) {
                if (cn.hasPerms(player)){
                    player.sendMessage(creeperAlert);
                    if (cn.getConfig().getBoolean(Config.updateCompass) && player.getInventory().contains(Material.COMPASS)) {
                        updateCompass(event, player);
                    }
                }
            }
        }
    }

    public void updateCompass(final EntityTargetLivingEntityEvent event, final Player player) {
        cn.getServer().getScheduler().scheduleSyncDelayedTask(cn, new Runnable() {
            @Override
            public void run() {
                if (event.getTarget().equals(player) && !event.getEntity().isDead() && event.getTarget().getNearbyEntities(15.0, 15.0, 15.0).contains(event.getEntity())) {
                    player.setCompassTarget(event.getEntity().getLocation());
                    updateCompass(event, player);
                } else {
                    if (player.getBedSpawnLocation() != null) {
                        player.setCompassTarget(player.getBedSpawnLocation());
                    }
                    player.sendMessage(creeperLost);
                }
            }
        });
    }


 }
