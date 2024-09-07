package dev.aarow.parkour.managers.impl;

import dev.aarow.parkour.data.parkour.Parkour;
import dev.aarow.parkour.data.parkour.ParkourCheckpoint;
import dev.aarow.parkour.managers.Manager;
import dev.aarow.parkour.utility.data.CoordinatePair;
import dev.aarow.parkour.utility.data.ParkourSerializator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParkourManager extends Manager {

    private List<Parkour> parkours = new ArrayList<>();

    @Override
    public void setup() {
        String query = "SELECT NAME, CHECKPOINTS FROM parkours";

        try (PreparedStatement stmt = plugin.getDatabaseManager().getConnection().prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("NAME");

                Parkour parkour = new Parkour(name);

                parkour.getCheckpoints().addAll(ParkourSerializator.deserializeParkourCheckpoints(parkour, resultSet.getString("CHECKPOINTS")));

                this.parkours.add(parkour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Parkour> getParkours() {
        return parkours;
    }

    public Parkour getByFirstCheckpoint(CoordinatePair coordinatePair){
        return this.parkours.stream().filter(parkour -> parkour.getCheckpoints().stream().findFirst().orElse(null).getCoordinatePair().equals(coordinatePair)).findFirst().orElse(null);
    }

    public Parkour getParkourByCheckpointLocation(CoordinatePair coordinatePair){
        return this.parkours.stream().filter(parkour -> parkour.getCheckpointAt(coordinatePair) != null).findFirst().orElse(null);
    }

    public Parkour getByName(String name){
        return this.parkours.stream().filter(parkour -> parkour.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
