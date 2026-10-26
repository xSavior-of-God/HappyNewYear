package com.xSavior_of_God.HappyNewYear.presets;

import com.xSavior_of_God.HappyNewYear.utils.CommentedConfiguration;

import java.util.Arrays;

public class NewYearPreset implements Preset {
    @Override
    public String getName() {
        return "newyear";
    }

    @Override
    public void apply(CommentedConfiguration cfg) {
        cfg.set("Enabled", true);
        cfg.set("SpawnAnimationType", "STANDARD_REALISTIC");
        cfg.set("HourlyDuration", 6000);
        cfg.set("HourlyTimezone", "GMT+2");
        cfg.set("Timer", 60);
        cfg.set("Limit", 5);
        cfg.set("AmountPerPlayer", 5);
        cfg.set("FireworkEffectTypes", Arrays.asList("RANDOM"));
        cfg.set("RandomSpawnPosition.Horizontal", 25);
        cfg.set("RandomSpawnPosition.Vertical", 25);
        cfg.set("ExplosionHeight", 40);
        cfg.set("Worlds.OnlyOnNight.Enabled", true);
        cfg.set("Worlds.AlwaysNight.Enabled", false);
        cfg.set("Worlds.UseRealTime.Enabled", true);
        cfg.set("Worlds.UseRealTime.Timezone", "GMT+2");
        cfg.set("Worlds.Month", -1);
        cfg.set("Worlds.Starts", "17:00");
        cfg.set("Worlds.Ends", "05:30");
        cfg.set("Worlds.Blacklist", true);
        cfg.set("Worlds.List", Arrays.asList("BLACKLISTED_WORLD_NAME"));
        cfg.set("Hooks.ImageFireworksPro.Enabled", false);
        cfg.set("Hooks.ImageFireworksPro.FireworkEffectTypes", Arrays.asList("RANDOM"));
    }
}
