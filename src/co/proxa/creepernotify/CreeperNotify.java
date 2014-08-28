package co.proxa.creepernotify;

import co.proxa.creepernotify.listeners.EntityTargetListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import co.proxa.creepernotify.file.ConfigurationFile;

public class CreeperNotify extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigurationFile cf = new ConfigurationFile(this);
        cf.loadConfig();
        EntityTargetListener cpl = new EntityTargetListener(this, cf);
        getServer().getPluginManager().registerEvents(cpl,this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("cn")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    this.reloadConfig();
                    sender.sendMessage(ChatColor.DARK_GREEN + "[CN] Configuration file reloaded.");
                    return true;
                }
            }
        }
        return false;
    }

}
