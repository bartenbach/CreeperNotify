package org.hopto.seed419.creepernotify.file.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.hopto.seed419.CreeperNotify;
import org.hopto.seed419.creepernotify.file.Config;

import java.util.HashSet;

/**
 * Attribute Only (Public) License
 * Version 0.a3, July 11, 2011
 * <p/>
 * Copyright (C) 2012 Blake Bartenbach <seed419@gmail.com> (@seed419)
 * <p/>
 * Anyone is allowed to copy and distribute verbatim or modified
 * copies of this license document and altering is allowed as long
 * as you attribute the author(s) of this license document / files.
 * <p/>
 * ATTRIBUTE ONLY PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 * <p/>
 * 1. Attribute anyone attached to the license document.
 * Do not remove pre-existing attributes.
 * <p/>
 * Plausible attribution methods:
 * 1. Through comment blocks.
 * 2. Referencing on a site, wiki, or about page.
 * <p/>
 * 2. Do whatever you want as long as you don't invalidate 1.
 *
 * @license AOL v.a3 <http://aol.nexua.org>
 */
public class RadarJamListener implements Listener {


    private CreeperNotify cn;
    private final String radarJammed = ChatColor.YELLOW + "[" + ChatColor.DARK_RED + "Radar Jammed" + ChatColor.YELLOW + "]";
    private final String radarClear = ChatColor.YELLOW + "[" + ChatColor.GRAY + "Radar Online" + ChatColor.YELLOW + "]";
    private final HashSet<String> jammedPlayers = new HashSet<String>();


    public RadarJamListener(CreeperNotify cn) {
        this.cn = cn;
    }


    @EventHandler
    void onEndermanNearby(PlayerMoveEvent event) {
        if (event.getPlayer().getInventory().contains(Material.COMPASS) && cn.getConfig().getBoolean(Config.radarJam)) {
            boolean jammed = false;
            for (Entity x : event.getPlayer().getNearbyEntities(15.0,15.0, 15.0)) {
                if (x.getType() == EntityType.ENDERMAN && !jammedPlayers.contains(event.getPlayer().getName())) {
                    System.out.println("enderman detected");
                    jammedPlayers.add(event.getPlayer().getName());
                    jammed = true;
                    World world = event.getPlayer().getWorld();
                    event.getPlayer().sendMessage(radarJammed);
                    jamCompass(event, world, event.getPlayer().getLocation().getY());
                    return;
                }
            }
            if (!jammed && jammedPlayers.contains(event.getPlayer().getName())) {
                boolean ender = false;
                for (Entity x : event.getPlayer().getNearbyEntities(15.0,15.0,15.0)) {
                    if (x.getType() == EntityType.ENDERMAN) {
                        ender = true;
                        return;
                    }
                }
                if (!ender) {
                    event.getPlayer().setCompassTarget(event.getPlayer().getBedSpawnLocation());
                    event.getPlayer().sendMessage(radarClear);
                    jammedPlayers.remove(event.getPlayer().getName());
                }
            }
        }

    }

    public void jamCompass(final PlayerMoveEvent event, final World world,  final double y) {
        cn.getServer().getScheduler().scheduleAsyncDelayedTask(cn, new Runnable() {
            @Override
            public void run() {
                for (Entity x : event.getPlayer().getNearbyEntities(15.0, 15.0, 15.0)) {
                    if (x.getType() == EntityType.ENDERMAN) {
                        event.getPlayer().setCompassTarget(new Location(world, event.getPlayer().getLocation().getX(), y, (event.getPlayer().getLocation().getZ() + 5.0)));
                        sleep();
                        event.getPlayer().setCompassTarget(new Location(world, (event.getPlayer().getLocation().getX() - 5.0), y, (event.getPlayer().getLocation().getZ() + 5.0)));
                        sleep();
                        event.getPlayer().setCompassTarget(new Location(world, (event.getPlayer().getLocation().getX() - 5.0), y, event.getPlayer().getLocation().getX()));
                        sleep();
                        event.getPlayer().setCompassTarget(new Location(world, (event.getPlayer().getLocation().getX() - 5.0), y, (event.getPlayer().getLocation().getZ() - 5.0)));
                        sleep();
                        event.getPlayer().setCompassTarget(new Location(world, event.getPlayer().getLocation().getX(), y, (event.getPlayer().getLocation().getZ() - 5.0)));
                        sleep();
                        event.getPlayer().setCompassTarget(new Location(world, (event.getPlayer().getLocation().getX() + 5.0), y, (event.getPlayer().getLocation().getZ() - 5.0)));
                        sleep();
                        event.getPlayer().setCompassTarget(new Location(world, (event.getPlayer().getLocation().getX() + 5.0), y, event.getPlayer().getLocation().getZ()));
                        sleep();
                        event.getPlayer().setCompassTarget(new Location(world, (event.getPlayer().getLocation().getX() + 5.0), y, (event.getPlayer().getLocation().getZ() + 5.0)));
                        sleep();
                        jamCompass(event, world, y);
                    }
                }
                event.getPlayer().setCompassTarget(event.getPlayer().getBedSpawnLocation());

            }
        });
    }

    void sleep() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}
    }
}
