package dev.aarow.parkour.events;

import dev.aarow.parkour.data.parkour.Parkour;
import dev.aarow.parkour.data.parkour.ParkourCheckpoint;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ParkourCheckpointEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private ParkourCheckpoint checkpoint;

    public ParkourCheckpointEvent(Player player, ParkourCheckpoint checkpoint){
        this.player = player;
        this.checkpoint = checkpoint;
    }

    public Player getPlayer() {
        return player;
    }

    public ParkourCheckpoint getCheckpoint() {
        return checkpoint;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
