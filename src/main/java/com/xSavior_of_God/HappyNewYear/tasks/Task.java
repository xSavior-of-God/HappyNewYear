package com.xSavior_of_God.HappyNewYear.tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import com.xSavior_of_God.HappyNewYear.HappyNewYear;
import com.xSavior_of_God.HappyNewYear.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.xSavior_of_God.HappyNewYear.api.events.OnFireworkEvent;
import org.bukkit.scheduler.BukkitTask;

public class Task {
    private final BukkitTask fireworkTask;
    private int startHour = 0;
    private int duration = 0;

    public Task(String spawnAnimationType, int hourlyDuration, String hourlyTimezone) {
        if(spawnAnimationType.contains("HOURLY")) {
            fireworkTask = Bukkit.getScheduler().runTaskTimerAsynchronously(HappyNewYear.instance, () -> {
                // get current hours based on timezone specified
                int currentHour = LocalTime.now(ZoneId.of(hourlyTimezone)).getHour();
                if(duration == 0 && startHour != currentHour) {
                    startHour = currentHour;
                    duration = hourlyDuration;
                }
                if(duration != 0) {
                    duration = duration - 20;
                    firworkTask(spawnAnimationType);
                }
            }, 5 * 20L, 20L);
        } else {
            fireworkTask = Bukkit.getScheduler().runTaskTimerAsynchronously(HappyNewYear.instance, () -> {
                firworkTask(spawnAnimationType);
            }, 5 * 20L, HappyNewYear.timer);
        }

    }

    private void firworkTask(String spawnAnimationType) {
        // This variable is reset every time the thread is called
        Map<Chunk, Integer> check = new HashMap<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (HappyNewYear.forceStop)
                return;

            // check if the World name is in BlackList
            if ((HappyNewYear.wm.getBlacklist() && HappyNewYear.wm.getWorldsName().contains(player.getWorld().getName()))
                    // check if the World name is not in WhiteList
                    || (!HappyNewYear.wm.getBlacklist() && !HappyNewYear.wm.getWorldsName().contains(player.getWorld().getName())))
                return;

            if (HappyNewYear.wm.getOnNightEnabled()) {
                boolean between = false;

                if (HappyNewYear.wm.getMonth() == -1 || LocalDate.now(ZoneId.of(HappyNewYear.wm.getTimezone())).getMonthValue() == HappyNewYear.wm.getMonth()) {
                    if (HappyNewYear.wm.getInRealLifeEnabled())
                        between = Utils.stringTimeIsBetween(HappyNewYear.wm.getOnNightStarts(), HappyNewYear.wm.getOnNightEnds(), LocalTime
                                .now(ZoneId.of(HappyNewYear.wm.getTimezone())).format(DateTimeFormatter.ofPattern("HH:mm")));
                    else
                        between = Utils.stringTimeIsBetween(HappyNewYear.wm.getOnNightStarts(), HappyNewYear.wm.getOnNightEnds(),
                                Utils.format(player.getWorld().getTime()));
                }

                if (!between)
                    return;
            }

            Chunk chunk = player.getLocation().getChunk();
            if (check.containsKey(chunk)) {
                if (check.get(chunk) > HappyNewYear.limit) {
                    continue;
                }
                check.replace(chunk, check.get(chunk) + 1);
            } else {
                check.put(chunk, 1);
            }


            if (spawnAnimationType.contains("REALISTIC")) {
                // To allow the spawn of the fireworks, we proceed to switch to the primary thread
                // (to optimize and not burden the primary thread we perform the checks in the async)
                Bukkit.getScheduler().runTask(HappyNewYear.instance, () -> {
                    // run your custom Event that Spawns fireworks
                    OnFireworkEvent event = new OnFireworkEvent(player);
                    Bukkit.getPluginManager().callEvent(event);
                    // Check if the event is canceled
                    if (event.isCancelled())
                        return;

                    Random rand = new Random();
                    for (int c = 0; c < HappyNewYear.amountPerPlayer; c++) {
                        Bukkit.getScheduler().runTaskLater(HappyNewYear.instance, () -> {
                            spawnFireworks(randomLocation(player.getLocation()), HappyNewYear.fireworkEffectTypes
                                    .get(ThreadLocalRandom.current().nextInt(0, HappyNewYear.fireworkEffectTypes.size())));
                        }, (rand.nextInt(HappyNewYear.timer) + 1));
                    }
                });
            } else {
                // To allow the spawn of the fireworks, we proceed to switch to the primary thread
                // (to optimize and not burden the primary thread we perform the checks in the async)
                Bukkit.getScheduler().runTask(HappyNewYear.instance, () -> {
                    // run your custom Event that Spawns fireworks
                    OnFireworkEvent event = new OnFireworkEvent(player);
                    Bukkit.getPluginManager().callEvent(event);
                    // Check if the event is canceled
                    if (event.isCancelled())
                        return;

                    for (int c = 0; c < HappyNewYear.amountPerPlayer; c++) {
                        spawnFireworks(randomLocation(player.getLocation()), HappyNewYear.fireworkEffectTypes
                                .get(ThreadLocalRandom.current().nextInt(0, HappyNewYear.fireworkEffectTypes.size())));
                    }
                });
            }
        }
    }

    private void spawnFireworks(final Location LOC, final String TYPE) {
        if (LOC.getWorld() == null) {
            Bukkit.getLogger().log(Level.WARNING, "Something went wrong while spawning fireworks. World is null!");
            return;
        }

        Firework firework;
        try {
            firework = (Firework) LOC.getWorld().spawnEntity(LOC, EntityType.valueOf("FIREWORK_ROCKET"));
        } catch (Exception ignored) {
            firework = (Firework) LOC.getWorld().spawnEntity(LOC, EntityType.valueOf("FIREWORK"));
        }
        final FireworkMeta meta = firework.getFireworkMeta();
        final FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.with(FireworkEffect.Type.valueOf((TYPE.equalsIgnoreCase("RANDOM")
                ? FireworkEffect.Type.values()[ThreadLocalRandom.current().nextInt(0, FireworkEffect.Type.values().length)]
                .name()
                : TYPE)));
        setColor(builder);
        meta.addEffect(builder.build());
        firework.setFireworkMeta(meta);
        Bukkit.getScheduler().runTaskLater(HappyNewYear.instance, firework::detonate, 1L);
    }

    private void setColor(final Builder BUILDER) {
        int random = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        for (int i = 0; i < random; ++i) {
            final Color color = Color.fromBGR(ThreadLocalRandom.current().nextInt(1, 255 + 1),
                    ThreadLocalRandom.current().nextInt(1, 255 + 1), ThreadLocalRandom.current().nextInt(1, 255 + 1));
            BUILDER.withColor(color);
        }
    }

    private Location randomLocation(final Location LOC) {
        int Horizontal = ThreadLocalRandom.current().nextInt(HappyNewYear.randomSpawnPosition_Horizontal * -1,
                HappyNewYear.randomSpawnPosition_Horizontal + 1);
        int Horizontal2 = ThreadLocalRandom.current().nextInt(HappyNewYear.randomSpawnPosition_Horizontal * -1,
                HappyNewYear.randomSpawnPosition_Horizontal + 1);
        int Vertical = ThreadLocalRandom.current().nextInt(HappyNewYear.randomSpawnPosition_Vertical * -1,
                HappyNewYear.randomSpawnPosition_Vertical + 1);
        return LOC.add(Horizontal, Vertical + HappyNewYear.explosionHeight, Horizontal2);
    }

    public void StopTask() {
        fireworkTask.cancel();
    }

}
