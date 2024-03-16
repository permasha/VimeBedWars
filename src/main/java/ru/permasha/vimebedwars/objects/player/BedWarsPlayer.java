package ru.permasha.vimebedwars.objects.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.permasha.vimebedwars.objects.game.Team;

@Getter
@Setter
public class BedWarsPlayer {

    private final Player player;
    private Team team;
    private boolean isSpectator;
    private BedWarsPlayer damager;

    public BedWarsPlayer(Player player) {
        this.player = player;
        this.isSpectator = false;
    }

    public void teleport(Location location) {
        if (location == null) {
            return;
        }
        getPlayer().teleport(location);
    }

}
