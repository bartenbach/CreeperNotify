package co.proxa.creepernotify.file;

import org.bukkit.ChatColor;
import co.proxa.creepernotify.CreeperNotify;

public class ConfigurationFile {

    private CreeperNotify cn;
    private String notificationMessage;
    private int creeperRadarToolID;
    private boolean usePermissions;
    private boolean useCreeperRadar;

    public ConfigurationFile(CreeperNotify cn) {
        this.cn = cn;
    }

    public void checkFiles() {
        if (!cn.getDataFolder().exists()) {
            cn.getDataFolder().mkdirs();
        }
        cn.getConfig().options().copyDefaults(true);
        cn.saveConfig();
    }

    public void loadConfig() {
        this.notificationMessage = ChatColor.translateAlternateColorCodes('&', cn.getConfig().getString(Config.notifySentence));
        this.creeperRadarToolID = cn.getConfig().getInt(Config.radarToolID);
        this.usePermissions = cn.getConfig().getBoolean(Config.usePerms);
        this.useCreeperRadar = cn.getConfig().getBoolean(Config.useCreeperRadar);
    }

    public boolean getUseCreeperRadar() {
        return this.useCreeperRadar;
    }

    public boolean getUsePermissions() {
        return this.usePermissions;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public int getCreeperRadarToolID() {
        return creeperRadarToolID;
    }
}
