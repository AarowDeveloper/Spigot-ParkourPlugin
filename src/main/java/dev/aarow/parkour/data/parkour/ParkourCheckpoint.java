package dev.aarow.parkour.data.parkour;

import dev.aarow.parkour.utility.data.CoordinatePair;
import org.bukkit.Material;

public class ParkourCheckpoint {

    private Parkour parkour;
    private CoordinatePair coordinatePair;
    private int id;

    public ParkourCheckpoint(Parkour parkour, CoordinatePair coordinatePair, int id){
        this.parkour = parkour;
        this.coordinatePair = coordinatePair;
        this.id = id;
    }

    public ParkourCheckpoint(Parkour parkour, CoordinatePair coordinatePair){
        this.parkour = parkour;
        this.coordinatePair = coordinatePair;
        this.id = parkour.getCheckpoints().size();
    }

    public void placePressurePlate(Material material){
        this.coordinatePair.convert().getBlock().setType(material);
    }

    public boolean isLastCheckpoint(){
        return this.parkour.getLastCheckpoint().getId() == id;
    }

    public boolean isFirstCheckpoint(){
        return this.id == 0;
    }

    public Parkour getParkour() {
        return parkour;
    }

    public CoordinatePair getCoordinatePair() {
        return coordinatePair;
    }

    public int getId() {
        return id;
    }
}
