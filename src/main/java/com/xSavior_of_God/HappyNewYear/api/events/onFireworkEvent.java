package com.xSavior_of_God.HappyNewYear.api.events;

import com.xSavior_of_God.HappyNewYear.utils.Utils;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.concurrent.ThreadLocalRandom;

/**
 * <h1>onFireworkEvent</h1>
 *
 * @author xSavior_of_God
 * @version 1.0
 * @since 2021-01-01
 */
public class onFireworkEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private Location location;
    private String type;
    private Firework firework;

    public onFireworkEvent(Location location, String type, Firework firework) {
        this.location = location;
        this.type = type;
        this.firework = firework;
    }

    public Firework getFirework() {
        return firework;
    }

    public void setFirework(Firework firework) {
        this.firework = firework;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.firework.teleport(location); // Teleport the firework to the new location
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

}
