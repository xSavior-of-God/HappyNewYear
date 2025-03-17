package com.xSavior_of_God.HappyNewYear.imgFireworks;

import com.xSavior_of_God.HappyNewYear.HappyNewYear;
import org.bukkit.*;
import org.bukkit.util.Vector;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class imgFireworks {

    public imgFireworks() {
        loadImages();
    }

    private void loadImages() {
        //HappyNewYear.instance.getConfig().getStringList("")
    }


    public void spawnFirework(Location explodeLocation, double yawRotation, BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        // Create a map to store all spawn particle information
        Map<Location, Particle.DustOptions> particleMap = new HashMap<>();

        // Run the computation asynchronously
        Bukkit.getScheduler().runTaskAsynchronously(HappyNewYear.instance, () -> {
            // Convert yaw to radians.
            double yawRadians = Math.toRadians(yawRotation);

            // Calculate directional vectors based on the provided yaw.
            // Forward vector (Minecraft uses: forward = (-sin, 0, cos))
            Vector forward = new Vector(-Math.sin(yawRadians), 0, Math.cos(yawRadians));
            // The Right vector is the forward vector rotated by -90° around the Y axis.
            Vector right = forward.clone().rotateAroundY(-Math.PI / 2);
            // Up vector is simply (0, 1, 0).
            Vector up = new Vector(0, 1, 0);

            // Define a scale factor to adjust the spacing of the particles.
            double scale = 10.0;

            // Loop over each pixel in the image.
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int color = image.getRGB(x, y);
                    int alpha = (color >> 24) & 0xff;
                    // Only process non-transparent pixels.
                    if (alpha != 0) {
                        int red = (color >> 16) & 0xff;
                        int green = (color >> 8) & 0xff;
                        int blue = color & 0xff;

                        // Calculate the offset relative to the center of the image.
                        double offsetX = (x - (width / 2.0)) / scale;
                        double offsetY = ((height / 2.0) - y) / scale;  // Invert y if needed

                        // Create the offset vector by combining the right and up vectors.
                        Vector offset = right.clone().multiply(offsetX).add(up.clone().multiply(offsetY));
                        // Determine the final spawn location by adding the offset to the base location.
                        Location particleLocation = explodeLocation.clone().add(offset);
                        // Create the dust options with the extracted color.
                        Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(red, green, blue), 1);

                        // Store the spawn info in the map.
                        particleMap.put(particleLocation, dust);
                    }
                }
            }

            // After processing all pixels asynchronously, schedule a synchronous task
            // to spawn all the particles in the main thread.
            Bukkit.getScheduler().runTask(HappyNewYear.instance, () -> {
                World world = explodeLocation.getWorld();
                if (world != null) {
                    for (Map.Entry<Location, Particle.DustOptions> entry : particleMap.entrySet()) {
                        try {
                            world.spawnParticle(Particle.valueOf("DUST"), entry.getKey(), 0, 0, 0, 0, entry.getValue());
                        } catch (Exception ignored) {
                            world.spawnParticle(Particle.valueOf("REDSTONE"), entry.getKey(), 0, 0, 0, 0, entry.getValue());
                        }
                    }
                }
                // Optionally clear the map if it won't be reused.
                particleMap.clear();
            });
        });
    }
}
