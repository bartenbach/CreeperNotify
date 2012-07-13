package org.hopto.seed419.Listeners;import org.bukkit.ChatColor;import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;import org.bukkit.event.EventHandler;import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
public class CreeperPowerupListener implements Listener{
private final String alert=ChatColor.YELLOW+"["+ChatColor.GREEN+"Creeper Alert"+ChatColor.YELLOW+"]";
@EventHandler
public void onCreeperTarget(EntityTargetLivingEntityEvent event){
if(event.getEntity().getType()==EntityType.CREEPER){if(event.getTarget()instanceof Player){Player player=(Player)event.getTarget();
if(player.hasPermission("CreeperNotify.notify")){player.sendMessage(alert);}}}}}