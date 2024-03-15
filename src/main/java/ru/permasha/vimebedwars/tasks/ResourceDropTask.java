package ru.permasha.vimebedwars.tasks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ru.permasha.vimebedwars.objects.game.Game;

public class ResourceDropTask extends BukkitRunnable {

    private final Game game;

    public ResourceDropTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.getResourcesDrop().forEach(location -> {
            location.getWorld().dropItemNaturally(location, new ItemStack(Material.GOLD_INGOT));
        });
    }

}
