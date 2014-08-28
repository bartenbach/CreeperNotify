package co.proxa.creepernotify.listeners;

import co.proxa.creepernotify.CreeperNotify;
import co.proxa.creepernotify.file.ConfigurationFile;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.Date;
import java.util.HashMap;

public class EntityTargetListener implements Listener {

    private CreeperNotify cn;
    private ConfigurationFile cf;
    private HashMap<Player, Long> playerHashMap = new HashMap<Player, Long>();

    public EntityTargetListener(CreeperNotify cn, ConfigurationFile cf) {
        this.cn = cn;
        this.cf = cf;
    }

    @EventHandler
    public void onCreeperTarget(final EntityTargetLivingEntityEvent event) {
        if (event.getEntity().getType() == EntityType.CREEPER  && event.getTarget() instanceof Player) {
            final Player player = (Player) event.getTarget();
            if (cf.getUseCreeperRadar() && player.getInventory().contains(Material.getMaterial(cf.getCreeperRadarToolID()))) {
                if (player.hasPermission("creepernotify.notify") || !cf.getUsePermissions()) {
                    checkHashmap(player);
                }
            } else if (!cf.getUseCreeperRadar() && (player.hasPermission("creepernotify.notify") || !cf.getUsePermissions())){
                checkHashmap(player);
                if (player.getInventory().contains(Material.getMaterial(cf.getCreeperRadarToolID()))) {
                    //System.out.println("Setting compass target...");
                    player.setCompassTarget(event.getEntity().getLocation());
                }
            }
            if (cf.getCreeperRadarToolID() == 345 && player.getInventory().contains(Material.COMPASS)) {  //TODO test this
                scheduleTask(event, player);
            }
        }
    }

    public void checkHashmap(Player player) {
        if (playerHashMap.containsKey(player)) {
            if ((new Date().getTime() - 10000) > playerHashMap.get(player)) {
                sendNotification(player);
                playerHashMap.put(player, new Date().getTime());
            }
        } else {
            playerHashMap.put(player, new Date().getTime());
            sendNotification(player);
        }
    }

    public void sendNotification(Player player) {
        player.sendMessage(cf.getNotificationMessage());
        //cn.getLogger().info(player.getName() + " has been targeted by a creeper!");
    }

    public void scheduleTask(final EntityTargetLivingEntityEvent event, final Player player) {
        cn.getServer().getScheduler().scheduleSyncDelayedTask(cn, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Running here.");
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
