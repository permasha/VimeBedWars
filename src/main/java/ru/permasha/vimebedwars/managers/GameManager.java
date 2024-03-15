package ru.permasha.vimebedwars.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.permasha.vimebedwars.VimeBedWars;
import ru.permasha.vimebedwars.database.DataType;
import ru.permasha.vimebedwars.enums.TeamColor;
import ru.permasha.vimebedwars.objects.game.Bed;
import ru.permasha.vimebedwars.objects.game.Game;
import ru.permasha.vimebedwars.objects.game.Team;
import ru.permasha.vimebedwars.objects.player.BedWarsPlayer;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameManager {

    VimeBedWars plugin;
    Game game;

    public GameManager(VimeBedWars plugin) {
        this.plugin = plugin;
        this.game = getOrCreateGame();
    }

    public Game getOrCreateGame() {
        int minPlayers = plugin.getConfiguration().getInt("minPlayers");
        int maxPlayers = plugin.getConfiguration().getInt("maxPlayers");

        List<String> rawResourceLocs = plugin.getConfiguration().getList("spawnLocations.resource");
        List<Location> resourceLocs = new ArrayList<>();
        rawResourceLocs.forEach(rawLoc -> resourceLocs.add(getLocationFromString(rawLoc)));

        List<String> rawShopsLocs = plugin.getConfiguration().getList("spawnLocations.shops");
        List<Location> shopsLocs = new ArrayList<>();
        rawShopsLocs.forEach(rawLoc -> shopsLocs.add(getLocationFromString(rawLoc)));

        return new Game(minPlayers, maxPlayers, getTeams(), resourceLocs, shopsLocs);
    }

    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();

        plugin.getConfiguration()
                .getSection("teams")
                .getKeys(false)
                .forEach(teamStr -> {
                    TeamColor teamColor = TeamColor.valueOf(teamStr.toUpperCase());

                    String rawSpawn = plugin.getConfiguration()
                            .getString("teams." + teamStr + ".spawn");
                    Location spawnTeam = getLocationFromString(rawSpawn);

                    String rawBed = plugin.getConfiguration()
                            .getString("teams." + teamStr + ".bed");
                    Bed bed = new Bed(getLocationFromString(rawBed));

                    Team team = new Team(teamColor, spawnTeam, bed);
                    teams.add(team);
        });

        return teams;
    }

    public Location getLocationFromString(String rawLocation) {
        String[] splitLoc = rawLocation.split(";");
        World world = Bukkit.getWorld("world");

        return new Location(world,
                Integer.parseInt(splitLoc[0]),
                Integer.parseInt(splitLoc[1]),
                Integer.parseInt(splitLoc[2])
        );
    }

    public BedWarsPlayer getBedWarsPlayer(Player player) {
        for (BedWarsPlayer bedWarsPlayer : getGame().getPlayers()) {
            if (bedWarsPlayer.getPlayer().equals(player)) {
                return bedWarsPlayer;
            }
        }
        return new BedWarsPlayer(player);
    }

    public void addPlayerScore(Player player, DataType dataType) {
        int data = plugin.getBedWarsDatabase().getPlayerData(player, dataType);
        plugin.getBedWarsDatabase().updatePlayerData(player, dataType, data + 1);
    }

    public Team getVictoryTeam() {
        for (Team team : game.getTeams()) {
            if (!team.isLose()) {
                return team;
            }
        }
        return null;
    }

    public Team getTeamOfBedLocation(Location location) {
        for (Team team : game.getTeams()) {
            Bed bed = team.getBed();
            if (location.distance(bed.getLocation()) < 2D) {
                return team;
            }
        }
        return null;
    }

}
