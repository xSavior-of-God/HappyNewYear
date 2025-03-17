package com.xSavior_of_God.HappyNewYear.hooks.imagefireworksreborn;

import com.xSavior_of_God.HappyNewYear.api.events.onFireworkEvent;
import com.xSavior_of_God.HappyNewYear.utils.Utils;
import me.lukyn76.imagefireworkspro.core.ImageFirework;
import me.lukyn76.imagefireworkspro.util.ConfigManager;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * lukyn76's ImageFireworksPro
 *
 * @Source https://github.com/heyxmirko/ImageFireworksPro
 * @Version 1.1.6
 */
public class ImageFireworksRebornHook implements Listener {
    private List<String> fireworksTypes = new ArrayList<>();

    public ImageFireworksRebornHook(List<String> fireworksTypes) {
        final Collection<? extends String> availableImageFireworks = this.allFireworks();
        for (String s : fireworksTypes) {
            String type = s.split("%")[0];
            if (type.equalsIgnoreCase("RANDOM")) {
                String chance = "100";
                if (s.split("%").length > 1) {
                    chance = s.split("%")[1];
                }
                for(String imageFirework : availableImageFireworks) {
                    this.fireworksTypes.add(imageFirework + "%" + chance);
                }
            } else {
                if (availableImageFireworks.contains(type.toUpperCase())) {
                    this.fireworksTypes.add(s);
                }
            }
        }
    }

    public List<String> getFireworksTypes() {
        return fireworksTypes;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onFireworkExplodeEvent(FireworkExplodeEvent event) {
        Firework firework = event.getEntity();
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        if (fireworkMeta.hasCustomModelData()) {
            firework.getWorld().playSound(firework.getLocation(), Sound.valueOf("ENTITY_FIREWORK_ROCKET_BLAST_FAR"), 100, 1);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onFireworkEvent(onFireworkEvent event) {
        if (allFireworks().contains(event.getType())) {
            String TYPE = event.getType().toLowerCase();
            ImageFirework imageFirework = ConfigManager.getImageFirework(TYPE.toLowerCase());
            if (imageFirework == null) {
                Utils.log(Level.WARNING, "&e[HappyNewYear] &cImageFirework " + TYPE + " not found!");
                event.setCancelled(true);
                return;
            }
            Firework firework = event.getFirework();
            FireworkMeta meta = firework.getFireworkMeta();
            meta.setCustomModelData(imageFirework.getCustomModelData());
            firework.setFireworkMeta(meta);
            event.setType("");
            event.setFirework(firework);
        }
    }

    public Collection<? extends String> allFireworks() {
        return ConfigManager.getAvailableImageFireworks().stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
