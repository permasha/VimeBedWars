package ru.permasha.vimebedwars.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.permasha.vimebedwars.VimeBedWars;

public class InventoryClick implements Listener {

    VimeBedWars plugin;

    public InventoryClick(VimeBedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (inventory.getTitle().equals("Магазин")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            switch (clickedItem.getItemMeta().getDisplayName()) {
                case "Предмет 1" : {
                    buyItem(player, new ItemStack(Material.WOOL), 1);
                    break;
                }
                case "Предмет 2" : {
                    buyItem(player, new ItemStack(Material.DIAMOND_SWORD), 2);
                    break;
                }
                case "Предмет 3" : {
                    buyItem(player, new ItemStack(Material.SHEARS), 3);
                    break;
                }
                default: break;
            }

        }
    }

    private void buyItem(Player player, ItemStack buyStack, int cost) {
        if (player.getInventory().contains(Material.GOLD_INGOT, cost)) {
            player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, cost));
            player.getInventory().addItem(buyStack);
            player.sendMessage("Покупка прошла успешно!");
        } else {
            player.sendMessage("У вас недостаточно золотых слитков!");
        }
    }

}
