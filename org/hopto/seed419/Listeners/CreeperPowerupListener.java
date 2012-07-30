package org.hopto.seed419.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.hopto.seed419.CreeperNotify;
import org.hopto.seed419.File.Config;

public class CreeperPowerupListener implements Listener {


    private CreeperNotify cn;
    private final String alert=ChatColor.YELLOW+"["+ChatColor.GREEN+"Creeper Alert"+ChatColor.YELLOW+"]";


    public CreeperPowerupListener(CreeperNotify cn) {
        this.cn = cn;
    }

    @EventHandler
    public void onCreeperTarget(EntityTargetLivingEntityEvent event) {
        if(event.getEntity().getType()==EntityType.CREEPER){
            if(event.getTarget()instanceof Player){
                Player player=(Player)event.getTarget();
                if(player.hasPermission("CreeperNotify.notify") || !cn.getConfig().getBoolean(Config.usePerms)){
                    player.sendMessage(alert);
                }
            }
        }
    }
}