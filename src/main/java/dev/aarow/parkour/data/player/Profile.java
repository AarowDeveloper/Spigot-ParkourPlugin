package dev.aarow.parkour.data.player;

import dev.aarow.parkour.data.parkour.Parkour;
import dev.aarow.parkour.data.parkour.ParkourCheckpoint;
import dev.aarow.parkour.utility.general.StringUtility;

import java.util.UUID;

public class Profile {

    private UUID uuid;

    private Parkour creatingParkour;

    private ParkourCheckpoint currentCheckpoint;
    private long startedParkour;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public Parkour getCreatingParkour() {
        return creatingParkour;
    }

    public ParkourCheckpoint getCurrentCheckpoint() {
        return currentCheckpoint;
    }

    public long getStartedParkour() {
        return startedParkour;
    }

    public String getFormattedParkourTime(){
        return StringUtility.formatDuration((System.currentTimeMillis() - this.startedParkour) / 1000);
    }

    public void setCreatingParkour(Parkour creatingParkour) {
        this.creatingParkour = creatingParkour;
    }

    public void setCurrentCheckpoint(ParkourCheckpoint currentCheckpoint) {
        this.currentCheckpoint = currentCheckpoint;
    }

    public void setStartedParkour(long startedParkour) {
        this.startedParkour = startedParkour;
    }
}
