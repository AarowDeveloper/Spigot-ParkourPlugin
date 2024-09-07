package dev.aarow.parkour.events;

import dev.aarow.parkour.data.parkour.Parkour;
import dev.aarow.parkour.data.parkour.ParkourCheckpoint;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ParkourStartEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private ParkourCheckpoint firstCheckpoint;

    public ParkourStartEvent(Player player, ParkourCheckpoint firstCheckpoint){
        this.player = player;
        this.firstCheckpoint = firstCheckpoint;
    }

    public Player getPlayer() {
        return player;
    }

    public ParkourCheckpoint getFirstCheckpoint() {
        return firstCheckpoint;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
