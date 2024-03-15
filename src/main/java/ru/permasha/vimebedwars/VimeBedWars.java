package ru.permasha.vimebedwars;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.permasha.vimebedwars.configuration.Configuration;
import ru.permasha.vimebedwars.database.BedWarsDatabase;
import ru.permasha.vimebedwars.listeners.*;
import ru.permasha.vimebedwars.managers.GameManager;
import ru.permasha.vimebedwars.managers.ShopManager;

import java.sql.SQLException;

@Getter
public class VimeBedWars extends JavaPlugin {

    static VimeBedWars instance;
    Configuration configuration;
    BedWarsDatabase bedWarsDatabase;
    GameManager gameManager;
    ShopManager shopManager;

    @Override
    public void onEnable() {
        instance = this;

        // Configuration create and load
        configuration = new Configuration(this, "config.yml");
        configuration.refresh();

        gameManager = new GameManager(this);
        shopManager = new ShopManager(this);

        initListeners();

        initDatabase();
    }

    public void initDatabase() {
        try {
            // Ensure the plugin's data folder exists
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            bedWarsDatabase = new BedWarsDatabase(getDataFolder().getAbsolutePath() + "/database.db");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to database! " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void initListeners() {
        getEvents(
                new BlockBreak(this),
                new EntityDamage(this),
                new InventoryClick(this),
                new PlayerInteractEntity(this),
                new PlayerJoin(this)
        );
    }

    private void getEvents(Listener... listeners) {
        byte b;
        int i;
        Listener[] arrayOfListener;
        for (i = (arrayOfListener = listeners).length, b = 0; b < i; ) {
            Listener l = arrayOfListener[b];
            getServer().getPluginManager().registerEvents(l, this);
            b++;
        }
    }

    @Override
    public void onDisable() {
        try {
            bedWarsDatabase.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static VimeBedWars getInstance() {
        return instance;
    }

}
