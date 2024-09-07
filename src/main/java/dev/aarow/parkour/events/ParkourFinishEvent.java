package dev.aarow.parkour.events;

import dev.aarow.parkour.data.parkour.Parkour;
import dev.aarow.parkour.data.parkour.ParkourCheckpoint;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ParkourFinishEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private Parkour parkour;

    public ParkourFinishEvent(Player player, Parkour parkour){
        this.player = player;
        this.parkour = parkour;
    }

    public Player getPlayer() {
        return player;
    }

    public Parkour getParkour() {
        return parkour;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
