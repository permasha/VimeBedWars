package ru.permasha.vimebedwars.objects.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class Bed {

    Location location;
    boolean isAlive;

    public Bed(Location location) {
        this.location = location;
        this.isAlive = true;
    }

}
