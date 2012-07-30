package org.hopto.seed419;

import org.bukkit.plugin.java.JavaPlugin;
import org.hopto.seed419.File.ConfigurationFile;
import org.hopto.seed419.Listeners.CreeperPowerupListener;

public class CreeperNotify extends JavaPlugin {

    private CreeperPowerupListener cpl = new CreeperPowerupListener(this);
    private ConfigurationFile cf;

    public void onEnable() {
        cf = new ConfigurationFile(this);
        getServer().getPluginManager().registerEvents(cpl,this);
    }
}