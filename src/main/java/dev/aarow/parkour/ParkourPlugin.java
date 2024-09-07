package dev.aarow.parkour;

import dev.aarow.parkour.commands.impl.ParkourCommand;
import dev.aarow.parkour.handlers.impl.CheckpointHandler;
import dev.aarow.parkour.managers.impl.DatabaseManager;
import dev.aarow.parkour.managers.impl.ParkourManager;
import dev.aarow.parkour.managers.impl.ProfileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ParkourPlugin extends JavaPlugin {

    private static ParkourPlugin instance;

    private DatabaseManager databaseManager;
    private ParkourManager parkourManager;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        instance = this;

        this.registerConfiguration();

        this.databaseManager = new DatabaseManager();
        this.parkourManager = new ParkourManager();
        this.profileManager = new ProfileManager();

        this.registerCommands();
        this.registerHandlers();
    }

    protected void registerConfiguration(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    protected void registerCommands(){
        new ParkourCommand();
    }

    protected void registerHandlers(){
        new CheckpointHandler();
    }

    public static ParkourPlugin getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public ParkourManager getParkourManager() {
        return parkourManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }
}
