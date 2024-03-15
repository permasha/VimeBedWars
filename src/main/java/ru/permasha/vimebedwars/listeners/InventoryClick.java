package ru.permasha.vimebedwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.permasha.vimebedwars.VimeBedWars;

public class InventoryClick implements Listener {

    VimeBedWars plugin;

    public InventoryClick(VimeBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

    }

}
