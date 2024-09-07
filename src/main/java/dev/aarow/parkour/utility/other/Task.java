package dev.aarow.parkour.utility.other;

import dev.aarow.parkour.ParkourPlugin;
import org.bukkit.Bukkit;

public class Task {

    public static void runLater(Call call){
        Bukkit.getScheduler().runTaskLater(ParkourPlugin.getInstance(), call::call, 1L);
    }

    public interface Call {
        void call();
    }
}
