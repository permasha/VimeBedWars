package ru.permasha.vimebedwars.objects.game;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import ru.permasha.vimebedwars.VimeBedWars;
import ru.permasha.vimebedwars.enums.GameState;
import ru.permasha.vimebedwars.objects.npc.VillagerShop;
import ru.permasha.vimebedwars.objects.player.BedWarsPlayer;
import ru.permasha.vimebedwars.tasks.ResourceDropTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class Game {

    Collection<BedWarsPlayer> players;
    GameState gameState = GameState.WAITING;

    int minPlayers;
    int maxPlayers;

    List<Team> teams;
    List<Location> resourcesDrop;
    List<Location> shopsLocation;
    List<VillagerShop> villagers;

    public Game(int minPlayers, int maxPlayers, List<Team> teams, List<Location> resourcesDrop, List<Location> shopsLocation) {
        this.players = Collections.emptyList();
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.teams = teams;
        this.resourcesDrop = resourcesDrop;
        this.shopsLocation = shopsLocation;
    }

    public void joinGame(BedWarsPlayer player) {

        if (players.size() >= maxPlayers || gameState.equals(GameState.GAME)) {
            player.getPlayer().kickPlayer(ChatColor.RED + "Ухади, игра идёт");
            return;
        }

        // Adding player to game players collection
        getSmallestTeam().addGamePlayer(player);
        players.add(player);

        if (players.size() == minPlayers) {
            startGame();
        }

    }

    public void startGame() {
        gameState = GameState.GAME;

        players.forEach(player -> {
            player.getPlayer().setGameMode(GameMode.SURVIVAL);
            player.getPlayer().teleport(player.getTeam().getSpawnLocation());
        });

        shopsLocation.forEach(location -> {
            VillagerShop shop = new VillagerShop(location);
            shop.createEntity();
            villagers.add(shop);
        });

        new ResourceDropTask(this).runTaskTimerAsynchronously(VimeBedWars.getInstance(), 600, 600);
    }

    public void endGame() {
        villagers.forEach(shop -> {
            shop.getEntity().remove();
        });

        Bukkit.getScheduler().runTaskLater(VimeBedWars.getInstance(), () -> {
            Bukkit.getServer().shutdown();
        }, 120L);
    }

    public Team getSmallestTeam() {
        Team smallestTeam = getTeams().get(1);
        for (Team team : getTeams()) {
            if (getPlayersInTeam(team).size() < getPlayersInTeam(smallestTeam).size())
                smallestTeam = team;
        }
        return smallestTeam;
    }

    public List<BedWarsPlayer> getPlayersInTeam(Team team) {
        return team.getPlayers();
    }

    public List<BedWarsPlayer> getAlivePlayers(Team team) {
        List<BedWarsPlayer> alive = new ArrayList<>();
        team.getPlayers().forEach(player -> {
            if (!player.isSpectator()) {
                alive.add(player);
            }
        });
        return alive;
    }

}
