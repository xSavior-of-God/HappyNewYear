package com.xSavior_of_God.HappyNewYear.tasks;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.xSavior_of_God.HappyNewYear.HappyNewYear;
import com.xSavior_of_God.HappyNewYear.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

public class AlwaysNightTask {
    private BukkitTask realTimeTask, alwaysNightTask;

    List<World> worlds;

    public AlwaysNightTask() {
        worlds = Bukkit.getServer().getWorlds();
        if (HappyNewYear.wm.getBlacklist()) {
            worlds.removeIf(w -> HappyNewYear.wm.getWorldsName().contains(w.getName()));
        } else {
            worlds.removeIf(w -> !HappyNewYear.wm.getWorldsName().contains(w.getName()));
        }
        // Remove all worlds that are not NORMAL (like NETHER, THE_END, etc.)
        worlds.removeIf(w -> !w.getEnvironment().equals(World.Environment.NORMAL));

        if (HappyNewYear.wm.getInRealLifeEnabled()) {
            this.realTimeTask = Bukkit.getScheduler().runTaskTimer(HappyNewYear.instance, () -> {
                for (World w : worlds) {
                    long time = Utils.parse24(LocalTime.now(ZoneId.of(HappyNewYear.wm.getTimezone()))
                            .format(DateTimeFormatter.ofPattern("HH:mm"))) + 1;
                    if (w.getTime() != time)
                        w.setTime(time);
                }
            }, 20L, 1L);
        } else if (HappyNewYear.wm.getAlwaysNightEnabled()) {
            this.alwaysNightTask = Bukkit.getScheduler().runTaskTimer(HappyNewYear.instance, () -> {
                for (World w : worlds) {
                    if (w.getTime() != 18000L) {
                        w.setTime(18000L);
                    }
                }
            }, 20L, 1L);
        }

    }

    public void StopTask() {
        if (this.realTimeTask != null)
            this.realTimeTask.cancel();
        if (this.alwaysNightTask != null) {
            this.alwaysNightTask.cancel();
            for (World w : worlds) {
                w.setGameRule(org.bukkit.GameRule.DO_DAYLIGHT_CYCLE, true);
            }
        }
    }
}
