package ru.permasha.vimebedwars.objects.npc;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@Getter
public class VillagerShop {

    Location location;
    Entity entity;

    public VillagerShop(Location location) {
        this.location = location;
    }

    public void createEntity() {
        location.getWorld().spawnEntity(location, EntityType.VILLAGER);
    }

}
