package dev.aarow.parkour.utility.data;

import dev.aarow.parkour.data.parkour.Parkour;
import dev.aarow.parkour.data.parkour.ParkourCheckpoint;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ParkourSerializator {

    public static String serializeParkourCheckpoints(List<ParkourCheckpoint> parkourCheckpoints) {
        StringBuilder serializedCheckpoints = new StringBuilder();

        for (ParkourCheckpoint checkpoint : parkourCheckpoints) {
            CoordinatePair coordinatePair = checkpoint.getCoordinatePair();
            serializedCheckpoints.append(coordinatePair.getWorld().getName())
                    .append(',')
                    .append(coordinatePair.getX())
                    .append(',')
                    .append(coordinatePair.getY())
                    .append(',')
                    .append(coordinatePair.getZ())
                    .append(',')
                    .append(checkpoint.getId())
                    .append(';');
        }

        if (serializedCheckpoints.length() > 0) {
            serializedCheckpoints.setLength(serializedCheckpoints.length() - 1);
        }

        return serializedCheckpoints.toString();
    }

    public static List<ParkourCheckpoint> deserializeParkourCheckpoints(Parkour parkour, String serialized) {
        List<ParkourCheckpoint> checkpoints = new ArrayList<>();

        String[] checkpointStrings = serialized.split(";");

        for (String checkpointString : checkpointStrings) {
            String[] attributes = checkpointString.split(",");

            World world = Bukkit.getWorld(attributes[0]);
            int x = Integer.parseInt(attributes[1]);
            int y = Integer.parseInt(attributes[2]);
            int z = Integer.parseInt(attributes[3]);
            int id = Integer.parseInt(attributes[4]);

            checkpoints.add(new ParkourCheckpoint(parkour, new CoordinatePair(world, x, y, z), id));
        }

        return checkpoints;
    }
}
