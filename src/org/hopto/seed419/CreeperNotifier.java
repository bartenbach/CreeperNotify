package org.hopto.seed419;

/**
 * Created with IntelliJ IDEA.
 * User: seed419
 * Time: 2:03 AM
 * To change this template use File | Settings | File Templates.
 */

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.hopto.seed419.Listeners.CreeperPowerupListener;


public class CreeperNotifier extends JavaPlugin {

    private final static CreeperPowerupListener cpl = new CreeperPowerupListener();

    public void onEnable() {
        PluginManager pm =   getServer().getPluginManager();
        pm.registerEvents(cpl, this);
    }

}
