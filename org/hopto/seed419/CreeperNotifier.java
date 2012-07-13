package org.hopto.seed419;import org.bukkit.plugin.java.JavaPlugin;import org.hopto.seed419.Listeners.CreeperPowerupListener;
public class CreeperNotifier extends JavaPlugin {private final static CreeperPowerupListener cpl = new CreeperPowerupListener();
public void onEnable() {getServer().getPluginManager().registerEvents(cpl,this);}}