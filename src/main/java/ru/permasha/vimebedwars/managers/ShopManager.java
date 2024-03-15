package ru.permasha.vimebedwars.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.permasha.vimebedwars.VimeBedWars;
import ru.permasha.vimebedwars.objects.game.Game;

import java.util.Arrays;

public class ShopManager {

    VimeBedWars plugin;

    public ShopManager(VimeBedWars plugin) {
        this.plugin = plugin;
    }

    public void openShop(Player player) {
        Inventory inventory = Bukkit.getServer().createInventory(null, 9, "Магазин");

        inventory.setItem(0, createItem(Material.WOOL, "Предмет 1", "Цена: 1 золотой слиток"));
        inventory.setItem(1, createItem(Material.DIAMOND_SWORD, "Предмет 2", "Цена: 2 золотой слиток"));
        inventory.setItem(2, createItem(Material.SHEARS, "Предмет 3", "Цена: 3 золотой слиток"));

        player.openInventory(inventory);
    }

    private ItemStack createItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

}
