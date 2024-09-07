package dev.aarow.parkour.managers.impl;

import dev.aarow.parkour.data.player.Profile;
import dev.aarow.parkour.managers.Manager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager extends Manager {

    private Map<UUID, Profile> profiles = new HashMap<>();

    @Override
    public void setup() {

    }

    public Profile get(Player player){
        return this.get(player.getUniqueId());
    }

    public Profile get(UUID uuid){
        if(this.profiles.get(uuid) == null) this.profiles.put(uuid, new Profile(uuid));

        return this.profiles.get(uuid);
    }
}
