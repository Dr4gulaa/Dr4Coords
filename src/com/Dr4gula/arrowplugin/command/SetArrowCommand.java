package com.Dr4gula.arrowplugin.command;

import com.Dr4gula.arrowplugin.Main;
import com.Dr4gula.arrowplugin.tasks.ArrowTask;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class SetArrowCommand implements CommandExecutor {

    private final Main plugin;

    public SetArrowCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando!");
            return true;
        }

        Player player = (Player) sender;
        Map<Player, ArrowTask> activeTasks = plugin.getActiveTasks();

        if (args.length == 0) {
            player.sendMessage("§cUso: /seta <x> <y> <z> ou /seta sair");
            return true;
        }

        if (args[0].equalsIgnoreCase("sair")) {
            if (activeTasks.containsKey(player)) {
                ArrowTask task = activeTasks.get(player);
                task.cancelTask();
                activeTasks.remove(player);
                player.sendMessage("§aHolograma removido com sucesso!");
            } else {
                player.sendMessage("§cVocê não tem nenhuma seta ativa no momento.");
            }
            return true;
        }

        if (args.length == 3) {
            try {
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);

                if (activeTasks.containsKey(player)) {
                    activeTasks.get(player).cancelTask();
                    activeTasks.remove(player);
                }

                player.sendMessage("§aDestino definido para: §fX: " + x + ", Y: " + y + ", Z: " + z);
                ArrowTask task = new ArrowTask(player, new Location(player.getWorld(), x, y, z));
                task.runTaskTimer(plugin, 0L, 5L);
                activeTasks.put(player, task);

            } catch (NumberFormatException e) {
                player.sendMessage("§cAs coordenadas devem ser números válidos!");
            }
            return true;
        }

        player.sendMessage("§cUso: /seta <x> <y> <z> ou /seta sair");
        return true;
    }
}
