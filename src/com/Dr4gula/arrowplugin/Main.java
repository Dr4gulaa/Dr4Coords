package com.Dr4gula.arrowplugin;

import com.Dr4gula.arrowplugin.command.SetArrowCommand;
import com.Dr4gula.arrowplugin.tasks.ArrowTask;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    private final Map<Player, ArrowTask> activeTasks = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("Dr4Coords ativado com sucesso!");

        // Registrar comandos
        getCommand("seta").setExecutor(new SetArrowCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Dr4Coords desativado!");
        activeTasks.values().forEach(ArrowTask::cancelTask);
        activeTasks.clear();
    }

    public Map<Player, ArrowTask> getActiveTasks() {
        return activeTasks;
    }
}
