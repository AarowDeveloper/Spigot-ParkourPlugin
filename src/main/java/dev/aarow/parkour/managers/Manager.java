package dev.aarow.parkour.managers;

import dev.aarow.parkour.ParkourPlugin;
import dev.aarow.parkour.utility.other.Task;

public abstract class Manager {

    public ParkourPlugin plugin = ParkourPlugin.getInstance();

    public Manager(){
        Task.runLater(this::setup);
    }

    public abstract void setup();
}
