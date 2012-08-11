package org.hopto.seed419;

import org.bukkit.plugin.java.JavaPlugin;
import org.hopto.seed419.file.ConfigurationFile;
import org.hopto.seed419.listeners.CreeperPowerupListener;

public class CreeperNotify extends JavaPlugin {

    private CreeperPowerupListener cpl = new CreeperPowerupListener(this);
    private ConfigurationFile cf;

    public void onEnable() {
        cf = new ConfigurationFile(this);
        getServer().getPluginManager().registerEvents(cpl,this);
    }
}