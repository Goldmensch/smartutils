package de.goldmensch.smartutils.plugin;

import de.goldmensch.smartutils.plugin.exceptions.NoCommandFoundException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SmartPlugin extends JavaPlugin {

    public void registerCommand(String name, CommandExecutor executor) {
        PluginCommand command = getCommand(name);
        if(command == null) throw new NoCommandFoundException(name);
        command.setExecutor(executor);
    }

    public void registerListener(Listener... listeners) {
        PluginManager manager = getServer().getPluginManager();
        for(Listener c : listeners) {
            manager.registerEvents(c, this);
        }
    }

}
