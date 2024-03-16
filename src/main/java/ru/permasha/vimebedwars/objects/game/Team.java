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

    private final TeamColor teamColor;
    private final List<BedWarsPlayer> players;

    private final Location spawnLocation;
    private final Bed bed;

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
