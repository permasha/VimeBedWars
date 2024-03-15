package ru.permasha.vimebedwars.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.permasha.vimebedwars.VimeBedWars;
import ru.permasha.vimebedwars.objects.player.BedWarsPlayer;

public class PlayerJoin implements Listener {

    VimeBedWars plugin;

    public PlayerJoin(VimeBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getBedWarsDatabase().isPlayerExists(player)) {
            plugin.getBedWarsDatabase().addPlayer(player);
        }

        BedWarsPlayer bedWarsPlayer =  new BedWarsPlayer(player);

        plugin.getGameManager().getGame().joinGame(bedWarsPlayer);
        player.setGameMode(GameMode.SURVIVAL);
    }

}
