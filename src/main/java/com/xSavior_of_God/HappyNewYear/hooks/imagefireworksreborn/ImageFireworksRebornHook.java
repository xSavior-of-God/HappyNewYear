package com.xSavior_of_God.HappyNewYear.hooks.imagefireworksreborn;

import com.xSavior_of_God.HappyNewYear.utils.Utils;
import me.lukyn76.imagefireworkspro.core.ImageFirework;
import me.lukyn76.imagefireworkspro.util.ConfigManager;
import org.bukkit.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

/**
 * lukyn76's ImageFireworksPro
 *
 * @Source https://github.com/heyxmirko/ImageFireworksPro
 * @Version 1.1.6
 */
public class ImageFireworksRebornHook {
    private List<String> fireworksTypes = new ArrayList<>();

    public ImageFireworksRebornHook(List<String> fireworksTypes) {
        if(fireworksTypes.stream().anyMatch("RANDOM"::equalsIgnoreCase)) {
            this.fireworksTypes.addAll(this.allFireworks());
        } else {
            this.fireworksTypes = fireworksTypes;
        }
    }

    public void spawnFirework(Location location) throws IOException {
        final Random random = new Random();
        final String firework = fireworksTypes.get(random.nextInt(fireworksTypes.size()));
        ImageFirework imageFirework = ConfigManager.getImageFirework(firework);
        if(imageFirework == null) {
            Utils.log(Level.WARNING, "&e[HappyNewYear] &cImageFirework " + firework + " not found!");
            return;
        }

        imageFirework.explode(location, location.getYaw());
    }

    public Collection<? extends String> allFireworks() {
        return ConfigManager.getAvailableImageFireworks();
    }
}
