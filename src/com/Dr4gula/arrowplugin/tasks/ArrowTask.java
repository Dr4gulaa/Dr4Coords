package com.Dr4gula.arrowplugin.tasks;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ArrowTask extends BukkitRunnable {
    private final Player player;
    private final Location target;
    private Hologram hologram;

    public ArrowTask(Player player, Location target) {
        this.player = player;
        this.target = target;
        Location playerLoc = player.getLocation();
        double distance = playerLoc.distance(target);

        Location hologramLoc = player.getLocation().add(0, 2, 0);
        this.hologram = com.gmail.filoghost.holographicdisplays.api.HologramsAPI.createHologram(
                org.bukkit.Bukkit.getPluginManager().getPlugin("Dr4Coords"), hologramLoc);

        hologram.appendTextLine("§6                /\\                   ");
        hologram.appendTextLine("§6               /  \\                  ");
        hologram.appendTextLine("§6              /_  _\\                 ");
        hologram.appendTextLine("§6               |  |                  ");
        hologram.appendTextLine("§6               |  |                  ");
    }

    @Override
    public void run() {
        if (!player.isOnline()) {
            cancelTask();
            return;
        }

        Location playerLoc = player.getLocation();


        if (!playerLoc.getWorld().equals(target.getWorld())) {
            player.sendMessage("§cVocê não está no mesmo mundo que o destino!");
            cancelTask();
            return;
        }

        double distance = playerLoc.distance(target);

        if (distance < 2) {
            player.sendMessage("§aVocê chegou ao destino!");
            cancelTask();
            return;
        }

        Location hologramLoc = playerLoc.clone().add(target.clone().subtract(playerLoc).toVector().normalize().multiply(3));
        hologramLoc.setY(playerLoc.getY() + 4);
        hologram.teleport(hologramLoc);


        hologram.clearLines();
        hologram.appendTextLine("§6                /\\                   ");
        hologram.appendTextLine("§6               /  \\                  ");
        hologram.appendTextLine("§6              /_  _\\                 ");
        hologram.appendTextLine("§6               |  |                  ");
        hologram.appendTextLine("§6               |  |                  ");
        hologram.appendTextLine("§dDistância: " + String.format("§a%.1f §eblocos", distance));
    }

    public void cancelTask() {
        if (hologram != null) {
            hologram.delete();
        }
        cancel();
    }
}
