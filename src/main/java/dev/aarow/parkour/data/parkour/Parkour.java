package dev.aarow.parkour.data.parkour;

import dev.aarow.parkour.utility.data.CoordinatePair;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Parkour {

    private String name;
    private List<ParkourCheckpoint> checkpoints = new ArrayList<>();

    public Parkour(String name){
        this.name = name;
    }

    public ParkourCheckpoint getCheckpointAt(CoordinatePair coordinatePair){
        return this.checkpoints.stream().filter(checkpoint -> checkpoint.getCoordinatePair().equals(coordinatePair)).findFirst().orElse(null);
    }

    public ParkourCheckpoint getNextCheckpoint(int currentID){
        return this.checkpoints.stream().filter(parkourCheckpoint -> parkourCheckpoint.getId()-1 == currentID).findFirst().orElse(null);
    }

    public ParkourCheckpoint getLastCheckpoint(){
        return this.checkpoints.get(this.checkpoints.size()-1);
    }

    public String getName() {
        return name;
    }

    public List<ParkourCheckpoint> getCheckpoints() {
        return checkpoints;
    }
}
