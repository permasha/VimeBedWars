package ru.permasha.vimebedwars.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import ru.permasha.vimebedwars.VimeBedWars;

public class PlayerInteractEntity implements Listener {

    VimeBedWars plugin;

    public PlayerInteractEntity(VimeBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof Villager) {
            event.setCancelled(true);
            plugin.getShopManager().openShop(player);
            player.sendMessage("Открываем магазин");
        }
    }

}
