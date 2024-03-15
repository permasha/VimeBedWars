package ru.permasha.vimebedwars.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import ru.permasha.vimebedwars.VimeBedWars;

public class PlayerInteractAtEntity implements Listener {

    VimeBedWars plugin;

    public PlayerInteractAtEntity(VimeBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

    }

}
