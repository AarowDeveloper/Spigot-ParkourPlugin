package dev.aarow.parkour.commands.impl;

import dev.aarow.parkour.commands.BaseCommand;
import dev.aarow.parkour.commands.CommandInfo;
import dev.aarow.parkour.data.parkour.Parkour;
import dev.aarow.parkour.data.parkour.ParkourCheckpoint;
import dev.aarow.parkour.data.parkour.ParkourSave;
import dev.aarow.parkour.data.player.Profile;
import dev.aarow.parkour.utility.chat.CC;
import dev.aarow.parkour.utility.data.CoordinatePair;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@CommandInfo(name = "parkour", playerOnly = true)
public class ParkourCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        Profile profile = plugin.getProfileManager().get(player);

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("restart")){
                if(profile.getCurrentCheckpoint() == null){
                    player.sendMessage(CC.translate("&7[&6&lParkour&7] &cYou didn't start a parkour..."));
                    return;
                }

                ParkourCheckpoint latestCheckpoint = profile.getCurrentCheckpoint();

                player.teleport(latestCheckpoint.getCoordinatePair().convert());
                player.sendMessage(CC.translate("&7[&6&lParkour&7] &eTeleported you to the latest checkpoint. &7&o(#" + latestCheckpoint.getId() + ")"));
                return;
            }
        }

        if(!player.hasPermission("parkour.staff")){
            player.sendMessage(CC.translate("&7[&cCorrect Usage&7] &c/parkour restart"));
            return;
        }

        if(args.length == 2){
            if(args[0].equalsIgnoreCase("create")){
                if(plugin.getParkourManager().getByName(args[1]) != null){
                    player.sendMessage(CC.translate("&7[&6&lParkour&7] &cThere is already a parkour with this name."));
                    return;
                }

                profile.setCreatingParkour(new Parkour(args[1]));
                player.sendMessage(CC.translate("&7[&6&lParkour&7] &eYou started the parkour creation process, use /parkour checkpoint to add a checkpoint and /parkour finish to set the last checkpoint as the finish."));
                return;
            }
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("checkpoint")){
                if(profile.getCreatingParkour() == null){
                    player.sendMessage(CC.translate("&7[&6&lParkour&7] &cYou are not in the parkour creation process..."));
                    return;
                }

                Parkour creatingParkour = profile.getCreatingParkour();
                ParkourCheckpoint parkourCheckpoint = new ParkourCheckpoint(creatingParkour, CoordinatePair.getFrom(player.getLocation()));

                if(creatingParkour.getCheckpointAt(parkourCheckpoint.getCoordinatePair()) != null || plugin.getParkourManager().getParkourByCheckpointLocation(CoordinatePair.getFrom(player.getLocation())) != null){
                    player.sendMessage(CC.translate("&7[&6&lParkour&7] &cThere is already a checkpoint at this location..."));
                    return;
                }

                creatingParkour.getCheckpoints().add(parkourCheckpoint);

                player.sendMessage(CC.translate("&7[&6&lParkour&7] &eYou successfully added a new checkpoint. &7&o(#" + parkourCheckpoint.getId() + ")"));
                return;
            }
            if(args[0].equalsIgnoreCase("finish")){
                if(profile.getCreatingParkour() == null){
                    player.sendMessage(CC.translate("&7[&6&lParkour&7] &cYou are not in the parkour creation process..."));
                    return;
                }
                if(profile.getCreatingParkour().getCheckpoints().size() < 2){
                    player.sendMessage(CC.translate("&7[&6&lParkour&7] &cThere needs to be at least 2 checkpoints to save the parkour..."));
                    return;
                }

                Parkour creatingParkour = profile.getCreatingParkour();
                new ParkourSave(creatingParkour).init();

                creatingParkour.getCheckpoints().forEach(parkourCheckpoint -> {
                    if(parkourCheckpoint.isLastCheckpoint() || parkourCheckpoint.isFirstCheckpoint()){
                        parkourCheckpoint.placePressurePlate(Material.LEGACY_GOLD_PLATE);
                        return;
                    }

                    parkourCheckpoint.placePressurePlate(Material.LEGACY_IRON_PLATE);
                });

                plugin.getParkourManager().getParkours().add(creatingParkour);

                profile.setCreatingParkour(null);

                player.sendMessage(CC.translate("&7[&6&lParkour&7] &eYou successfully created a new parkour."));
                return;
            }
        }

        player.sendMessage(CC.translate("&7&m---------------------------"));
        player.sendMessage(CC.translate("&6&lPARKOUR MANAGER"));
        player.sendMessage(CC.translate("&7&m---------------------------"));
        player.sendMessage(CC.translate(" &7&l• &e/parkour create <name> &7- &fStarts the parkour creation process."));
        player.sendMessage(CC.translate(" &7&l• &e/parkour checkpoint &7- &fSets a new checkpoint."));
        player.sendMessage(CC.translate(" &7&l• &e/parkour finish &7- &fUse the last checkpoint as the finish checkpoint."));
        player.sendMessage(CC.translate(" &7&l• &e/parkour restart &7- &fTeleports you to the latest checkpoint."));
        player.sendMessage(CC.translate("&7&m---------------------------"));
    }
}
