package dev.aarow.parkour.handlers.impl;

import dev.aarow.parkour.data.parkour.Parkour;
import dev.aarow.parkour.data.parkour.ParkourCheckpoint;
import dev.aarow.parkour.data.player.Profile;
import dev.aarow.parkour.events.ParkourCheckpointEvent;
import dev.aarow.parkour.events.ParkourFinishEvent;
import dev.aarow.parkour.events.ParkourStartEvent;
import dev.aarow.parkour.handlers.BaseHandler;
import dev.aarow.parkour.utility.chat.CC;
import dev.aarow.parkour.utility.data.CoordinatePair;
import dev.aarow.parkour.utility.general.StringUtility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CheckpointHandler extends BaseHandler {

    @EventHandler
    public void onPressurePlate(PlayerInteractEvent event){
        if(event.getAction() != Action.PHYSICAL) return;
        if(event.getClickedBlock().getType() != Material.LEGACY_IRON_PLATE && event.getClickedBlock().getType() != Material.LEGACY_GOLD_PLATE);

        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);
        CoordinatePair coordinatePair = CoordinatePair.getFrom(event.getClickedBlock().getLocation());

        if(handleStart(player)) return;
        if(profile.getCurrentCheckpoint() == null) return;

        ParkourCheckpoint currentCheckpoint = profile.getCurrentCheckpoint();
        Parkour parkour = currentCheckpoint.getParkour();

        if(parkour.getCheckpointAt(coordinatePair) == null) return;

        if(parkour.getCheckpointAt(coordinatePair).getId() != currentCheckpoint.getId()+1) return;

        Bukkit.getPluginManager().callEvent(new ParkourCheckpointEvent(player, parkour.getCheckpointAt(coordinatePair)));
    }


    @EventHandler
    public void onStart(ParkourStartEvent event){
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);
        ParkourCheckpoint parkourCheckpoint = event.getFirstCheckpoint();

        profile.setStartedParkour(System.currentTimeMillis());
        profile.setCurrentCheckpoint(parkourCheckpoint);

        player.sendMessage(CC.translate("&7[&6&lParkour&7] &eStarted a new parkour!"));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2F, 2F);
    }

    @EventHandler
    public void onCheckpoint(ParkourCheckpointEvent event){
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);
        ParkourCheckpoint parkourCheckpoint = event.getCheckpoint();

        if(parkourCheckpoint.isLastCheckpoint()){
            Bukkit.getPluginManager().callEvent(new ParkourFinishEvent(player, parkourCheckpoint.getParkour()));
            return;
        }

        profile.setCurrentCheckpoint(parkourCheckpoint);

        player.sendMessage(CC.translate("&7[&6&lParkour&7] &eNew checkpoint! &6("
                + profile.getFormattedParkourTime()
                + ")"));

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2F, 2F);
    }

    @EventHandler
    public void onFinish(ParkourFinishEvent event){
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);
        Parkour parkour = event.getParkour();

        player.sendMessage(CC.translate("&7[&6&lParkour&7] &eYou finished the &6" + parkour.getName() + " &eparkour in &6" +
                profile.getFormattedParkourTime()
         + "&e!"));

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2F, 2F);

        profile.setCurrentCheckpoint(null);
        profile.setStartedParkour(-1);
    }

    private boolean handleStart(Player player){
        CoordinatePair coordinatePair = CoordinatePair.getFrom(player.getLocation());
        Parkour parkour = plugin.getParkourManager().getByFirstCheckpoint(coordinatePair);

        if(parkour == null) return false;

        Profile profile = plugin.getProfileManager().get(player);

        if(profile.getCurrentCheckpoint() != null){
            if(System.currentTimeMillis() - profile.getStartedParkour() > 1500){
                profile.setCurrentCheckpoint(null);
                profile.setStartedParkour(-1);

                Bukkit.getPluginManager().callEvent(new ParkourStartEvent(player, parkour.getCheckpoints().stream().findFirst().orElse(null)));
                return true;
            }
            return false;
        }

        Bukkit.getPluginManager().callEvent(new ParkourStartEvent(player, parkour.getCheckpoints().stream().findFirst().orElse(null)));
        return true;
    }
}
