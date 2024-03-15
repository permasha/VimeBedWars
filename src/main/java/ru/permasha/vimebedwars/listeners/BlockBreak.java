package ru.permasha.vimebedwars.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.permasha.vimebedwars.VimeBedWars;
import ru.permasha.vimebedwars.database.DataType;
import ru.permasha.vimebedwars.enums.GameState;
import ru.permasha.vimebedwars.objects.game.Team;
import ru.permasha.vimebedwars.objects.player.BedWarsPlayer;

public class BlockBreak implements Listener {

    VimeBedWars plugin;

    public BlockBreak(VimeBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();

        if (plugin.getGameManager().getGame().getGameState().equals(GameState.WAITING)) {
            event.setCancelled(true);
            return;
        }

        if (!block.getType().equals(Material.BED_BLOCK)) {
            return;
        }

        BedWarsPlayer bedWarsPlayer = plugin.getGameManager().getBedWarsPlayer(player);

        Team bedTeam = plugin.getGameManager().getTeamOfBedLocation(location);
        if (bedTeam == null) {
            return;
        }

        if (bedTeam.equals(bedWarsPlayer.getTeam())) {
            player.sendMessage("Нельзя ломать свою кровать!");
            event.setCancelled(true);
            return;
        }

        player.sendMessage("Ты сломал вражескую кровать!");
        bedTeam.getBed().setAlive(false);
        plugin.getGameManager().addPlayerScore(player, DataType.BEDS);
    }

}
