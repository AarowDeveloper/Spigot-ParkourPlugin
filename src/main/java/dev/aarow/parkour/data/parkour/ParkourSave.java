package dev.aarow.parkour.data.parkour;

import dev.aarow.parkour.ParkourPlugin;
import dev.aarow.parkour.utility.data.ParkourSerializator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkourSave {

    private Connection connection = ParkourPlugin.getInstance().getDatabaseManager().getConnection();
    private Parkour parkour;

    public ParkourSave(Parkour parkour){
        this.parkour = parkour;
    }

    public void init(){
        if(exists()){
            this.update();
        }else{
            this.create();
        }
    }

    public void create() {
        try {
            PreparedStatement insert = connection
                        .prepareStatement("INSERT INTO parkours (NAME,CHECKPOINTS) VALUES (?,?)");

            insert.setString(1, parkour.getName());
            insert.setString(2, ParkourSerializator.serializeParkourCheckpoints(parkour.getCheckpoints()));

            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            PreparedStatement update = connection.prepareStatement(
                    "UPDATE parkours SET CHECKPOINTS = ? WHERE NAME = ?"
            );

            update.setString(1, ParkourSerializator.serializeParkourCheckpoints(parkour.getCheckpoints()));

            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean exists() {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM parkours WHERE NAME=?");
            statement.setString(1, parkour.getName());

            ResultSet results = statement.executeQuery();
            if (results.next()) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
