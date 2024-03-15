package ru.permasha.vimebedwars.objects.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import ru.permasha.vimebedwars.enums.TeamColor;
import ru.permasha.vimebedwars.objects.player.BedWarsPlayer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Team {

    TeamColor teamColor;
    List<BedWarsPlayer> players;

    Location spawnLocation;
    Bed bed;

    boolean isLose;

    public Team(TeamColor teamColor, Location spawnLocation, Bed bed) {
        this.teamColor = teamColor;
        this.players = new ArrayList<>();
        this.spawnLocation = spawnLocation;
        this.bed = bed;
        this.isLose = false;
    }

    public void addGamePlayer(BedWarsPlayer gamePlayer) {
        players.add(gamePlayer);
        gamePlayer.setTeam(this);
    }

}
