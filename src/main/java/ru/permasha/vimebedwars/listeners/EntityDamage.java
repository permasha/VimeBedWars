package ru.permasha.vimebedwars.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import ru.permasha.vimebedwars.VimeBedWars;
import ru.permasha.vimebedwars.database.DataType;
import ru.permasha.vimebedwars.enums.GameState;
import ru.permasha.vimebedwars.objects.game.Team;
import ru.permasha.vimebedwars.objects.player.BedWarsPlayer;

public class EntityDamage implements Listener {

    VimeBedWars plugin;

    public EntityDamage(VimeBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        Entity damager = event.getDamager();

        if (!(victim instanceof Player) || !(damager instanceof Player)) {
            return;
        }

        BedWarsPlayer victimPlayer = plugin.getGameManager().getBedWarsPlayer((Player) victim);
        BedWarsPlayer damagerPlayer = plugin.getGameManager().getBedWarsPlayer((Player) damager);

            if (victimPlayer.getTeam().getPlayers().contains(damagerPlayer)) {
                event.setCancelled(true);
            } // is player and damager in one team

        victimPlayer.setDamager(damagerPlayer);
    }

    @EventHandler
    public void onKill(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (plugin.getGameManager().getGame().getGameState().equals(GameState.WAITING)) {
            event.setCancelled(true);
            return;
        }

        if (!(entity instanceof Player)) {
            return;
        }

        BedWarsPlayer bedWarsPlayer = plugin.getGameManager().getBedWarsPlayer((Player) entity);

        if (bedWarsPlayer.getPlayer().getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);
            deathGamePlayer(bedWarsPlayer);
        }

    }

    public void deathGamePlayer(BedWarsPlayer gamePlayer) {
        if (gamePlayer.getDamager() != null) { // If Damager exists
            BedWarsPlayer damager = gamePlayer.getDamager();
            plugin.getGameManager().addPlayerScore(damager.getPlayer(), DataType.KILLS);
            gamePlayer.setDamager(null);
        }

        Team team = gamePlayer.getTeam();

        if (!team.getBed().isAlive()) {
            gamePlayer.getPlayer().setGameMode(GameMode.SPECTATOR);
            gamePlayer.setSpectator(true);
        }

        int alivePlayers = plugin.getGameManager()
                .getGame()
                .getAlivePlayers(team)
                .size();

        if (alivePlayers < 1) {
            team.setLose(true);

            Team victoryTeam = plugin.getGameManager().getVictoryTeam();
            victoryTeam.getPlayers().forEach(player -> {
                plugin.getGameManager().addPlayerScore(player.getPlayer(), DataType.WINS);
                player.getPlayer().sendMessage("Ваша команда победила!");
            });

            plugin.getGameManager().getGame().endGame();
        }

        gamePlayer.getPlayer().setHealth(20D);
        gamePlayer.teleport(team.getSpawnLocation());

    }

}
