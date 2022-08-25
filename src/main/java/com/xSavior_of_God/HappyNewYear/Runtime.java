package com.xSavior_of_God.HappyNewYear;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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

import com.xSavior_of_God.HappyNewYear.Events.onFireworkEvent;

public class Runtime {

    public Runtime() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, new Runnable() {

            @Override
            public void run() {
                Map<Chunk, Integer> check = new HashMap<Chunk, Integer>();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    Chunk chunk = player.getLocation().getChunk();
                    if (check.containsKey(chunk)) {
                        if (check.get(chunk) > Main.Limit) {
                            continue;
                        }
                        check.replace(chunk, check.get(chunk) + 1);
                    } else {
                        check.put(chunk, 1);
                    }
                    Bukkit.getScheduler().runTask(Main.instance, new Runnable() {
                        @Override
                        public void run() {
                            onFireworkEvent event = new onFireworkEvent(player);
                            Bukkit.getPluginManager().callEvent(event);
                            if (event.isCancelled())
                                return;

                            if (Main.ForceStop)
                                return;

                            if (!Main.ForceStart) {
                                if ((Main.wm.getBlacklist() == true && Main.wm.getWorldsName().contains(player.getWorld().getName()))
                                        || (Main.wm.getBlacklist() == false && !Main.wm.getWorldsName().contains(player.getWorld().getName())))
                                    return;

                                if (Main.wm.getOnlyNightEnabled()) {
                                    boolean between = false;
                                    if (Main.wm.getMonth() == -1 || LocalDate.now(ZoneId.of(Main.wm.getTimezone())).getMonthValue() == Main.wm.getMonth()) {
                                        if (Main.wm.getInRealLifeEnabled())
                                            between = Utilis.stringTimeIsBetween(Main.wm.getOnlyNightStarts(), Main.wm.getOnlyNightEnds(), LocalTime
                                                    .now(ZoneId.of(Main.wm.getTimezone())).format(DateTimeFormatter.ofPattern("HH:mm")).toString());
                                        else
                                            between = Utilis.stringTimeIsBetween(Main.wm.getOnlyNightStarts(), Main.wm.getOnlyNightEnds(),
                                                    Utilis.format(player.getWorld().getTime()).toString());
                                    }

                                    if (!between)
                                        return;
                                }
                            }


                            for (int c = 0; c < Main.AmountPerPlayer; c++) {
                                if (!Bukkit.isPrimaryThread()) {
                                    Bukkit.getScheduler().runTask(Main.instance, new Runnable() {
                                        @Override
                                        public void run() {
                                            spawnFireworks(randomLocation(player.getLocation()), Main.FireworkEffectTypes
                                                    .get(ThreadLocalRandom.current().nextInt(0, Main.FireworkEffectTypes.size())));
                                        }
                                    });
                                } else {
                                    spawnFireworks(randomLocation(player.getLocation()), Main.FireworkEffectTypes
                                            .get(ThreadLocalRandom.current().nextInt(0, Main.FireworkEffectTypes.size())));
                                }
                            }
                        }
                    });
                }
            }

        }, 5 * 20L, Main.Timer);
    }

    private void spawnFireworks(final Location LOC, final String TYPE) {
        final Firework firework = (Firework) LOC.getWorld().spawnEntity(LOC, EntityType.FIREWORK);
        final FireworkMeta meta = firework.getFireworkMeta();
        final FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.with(FireworkEffect.Type.valueOf((TYPE.equalsIgnoreCase("RANDOM")
                ? FireworkEffect.Type.values()[ThreadLocalRandom.current().nextInt(0, FireworkEffect.Type.values().length)]
                .name()
                : TYPE)));
        setColor(builder);
        meta.addEffect(builder.build());
        firework.setFireworkMeta(meta);
        Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable() {

            @Override
            public void run() {
                firework.detonate();
            }

        }, 1L);
    }

    private void setColor(final Builder BUILDER) {
        int random = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        for (int colors = random, i = 0; i < colors; ++i) {
            final Color color = Color.fromBGR((int) ThreadLocalRandom.current().nextInt(1, 255 + 1),
                    (int) ThreadLocalRandom.current().nextInt(1, 255 + 1), (int) ThreadLocalRandom.current().nextInt(1, 255 + 1));
            BUILDER.withColor(color);
        }
    }

    private Location randomLocation(final Location LOC) {
        int Horizontal = ThreadLocalRandom.current().nextInt(Main.RandomSpawnPosition_Horizontal * -1,
                Main.RandomSpawnPosition_Horizontal + 1);
        int Horizontal2 = ThreadLocalRandom.current().nextInt(Main.RandomSpawnPosition_Horizontal * -1,
                Main.RandomSpawnPosition_Horizontal + 1);
        int Vertical = ThreadLocalRandom.current().nextInt(Main.RandomSpawnPosition_Vertical * -1,
                Main.RandomSpawnPosition_Vertical + 1);
        return LOC.add((int) Horizontal, (int) Vertical + Main.ExplosionHeight, (int) Horizontal2);
    }

}
