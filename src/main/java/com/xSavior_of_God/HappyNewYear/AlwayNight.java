package com.xSavior_of_God.HappyNewYear;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class AlwayNight {
    List<World> worlds;

    public AlwayNight() {
        worlds = Bukkit.getServer().getWorlds();
        if (Main.AlwaysNightWorldsBlacklist) {
            for (World w : worlds) {
                if (Main.AlwaysNightWorldsList.contains(w.getName())) {
                    worlds.remove(w);
                }
            }
        } else {
            if (Main.AlwaysNightWorldsList.contains("ALLWORLDS")) {
                worlds.clear();
                Bukkit.getServer().getWorlds().forEach(world -> {
                    worlds.add(world);
                });
            }
        }

        if (Main.AlwaysNightUseRealTime) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, new Runnable() {

                @Override
                public void run() {
                    for (World w : worlds) {
                        w.setTime(Utilis.parse24(LocalTime.now(ZoneId.of(Main.wm.getTimezone()))
                                .format(DateTimeFormatter.ofPattern("HH:mm")).toString()));
                    }
                }

            }, 20L, 1L);
        } else {
            Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, new Runnable() {

                @Override
                public void run() {
                    for (World w : worlds) {
                        w.setTime(18000L);
                    }
                }

            }, 20L, 1L);
        }

    }
}
