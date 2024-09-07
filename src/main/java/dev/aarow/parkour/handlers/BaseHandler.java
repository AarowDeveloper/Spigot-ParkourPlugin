package dev.aarow.parkour.handlers;

import dev.aarow.parkour.ParkourPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class BaseHandler implements Listener {

    public ParkourPlugin plugin = ParkourPlugin.getInstance();

    public BaseHandler(){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
