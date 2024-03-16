package ru.permasha.vimebedwars.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ru.permasha.vimebedwars.VimeBedWars;
import ru.permasha.vimebedwars.objects.game.Game;

import java.util.List;

public class ResourceDropTask extends BukkitRunnable {

    private final Game game;

    public ResourceDropTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT);
        List<Location> resourceDropLocs = game.getResourcesDrop();

        for (Location location : resourceDropLocs) {
            location.getWorld().dropItemNaturally(location, itemStack);
        }
    }

}
