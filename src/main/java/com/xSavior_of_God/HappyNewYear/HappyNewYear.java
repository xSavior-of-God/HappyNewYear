package com.xSavior_of_God.HappyNewYear;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.xSavior_of_God.HappyNewYear.commands.Command;
import com.xSavior_of_God.HappyNewYear.events.HappyNewYearListeners;
import com.xSavior_of_God.HappyNewYear.manager.WorldManager;
import com.xSavior_of_God.HappyNewYear.tasks.AlwaysNightTask;
import com.xSavior_of_God.HappyNewYear.tasks.Task;
import com.xSavior_of_God.HappyNewYear.utils.CommentedConfiguration;
import com.xSavior_of_God.HappyNewYear.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HappyNewYear extends JavaPlugin {
    public static HappyNewYear instance;
    public static boolean
            enabled,
            forceStart,
            forceStop;
    public static int
            hourlyDuration,
            timer,
            amountPerPlayer,
            randomSpawnPosition_Horizontal,
            randomSpawnPosition_Vertical,
            explosionHeight,
            limit;
    public static List<String>
            fireworkEffectTypes;

    private AlwaysNightTask alwaysNightTask;
    private Task fireworkTask;
    private String
            spawnAnimationType,
            hourlyTimezone;

    public static WorldManager wm = new WorldManager();

    public void onEnable() {
        instance = this;
        Utils.log("\r\n" +
                "\r\n" +
                "\r\n"
                + "&e  |  |    \\    _ \\  _ \\ \\ \\  /     \\ |  __| \\ \\      /   \\ \\  /  __|    \\    _ \\   | \r\n"
                + "  __ |   _ \\   __/  __/  \\  /     .  |  _|   \\ \\ \\  /     \\  /   _|    _ \\     /  _| \r\n"
                + " _| _| _/  _\\ _|   _|     _|     _|\\_| ___|   \\_/\\_/       _|   ___| _/  _\\ _|_\\  _)\r\n"
                + "\r\n&e© Developed by &fxSavior_of_God &ewith &4<3 &7v" + getDescription().getVersion()
                + "\r\n"
                + "\r\n");

        final File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        CommentedConfiguration cfg = CommentedConfiguration.loadConfiguration(configFile);
        try {
            cfg.syncWithConfig(configFile, this.getResource("config.yml"), "FireworkEffectTypes", "Worlds.List");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        forceStart = false;

        loadConfig();
        getCommand("happynewyear").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(new HappyNewYearListeners(), this);

        if (enabled) {
            fireworkTask = new Task(spawnAnimationType, hourlyDuration, hourlyTimezone);
            if (wm.getAlwaysNightEnabled() || wm.getInRealLifeEnabled())
                this.alwaysNightTask = new AlwaysNightTask();
        } else
            Utils.log("&4HEY! &fBefore you can use me, you need to configure and enable me from config.yml ( Remember to set \"Enabled\" to true ! )");
    }

    public void onDisable() {
        if ((this.alwaysNightTask != null || wm.getAlwaysNightEnabled()) && this.alwaysNightTask != null)
            this.alwaysNightTask.StopTask();
        if ((this.fireworkTask != null || enabled) && this.fireworkTask != null)
            this.fireworkTask.StopTask();
        Utils.log("&eHappy New Year &cDisabled!");
    }

    private void loadConfig() {
        enabled = getConfig().getBoolean("Enabled");
        spawnAnimationType = getConfig().getString("SpawnAnimationType");
        hourlyDuration = getConfig().getInt("HourlyDuration");
        hourlyTimezone = getConfig().getString("HourlyTimezone");
        limit = getConfig().getInt("Limit");
        timer = getConfig().getInt("Timer");
        amountPerPlayer = getConfig().getInt("AmountPerPlayer");
        randomSpawnPosition_Horizontal = getConfig().getInt("RandomSpawnPosition.Horizontal");
        randomSpawnPosition_Vertical = getConfig().getInt("RandomSpawnPosition.Vertical");
        explosionHeight = getConfig().getInt("ExplosionHeight");
        fireworkEffectTypes = getConfig().getStringList("FireworkEffectTypes");
        wm.setBlacklist(getConfig().getBoolean("Worlds.Blacklist"));
        wm.setOnNightEnabled(getConfig().getBoolean("Worlds.OnlyOnNight.Enabled"));
        wm.setAlwaysNightEnabled(getConfig().getBoolean("Worlds.AlwaysNight.Enabled"));
        wm.setInRealLifeEnabled(getConfig().getBoolean("Worlds.UseRealTime.Enabled"));
        wm.setTimezone(getConfig().getString("Worlds.UseRealTime.Timezone"));
        wm.setMonth(getConfig().getInt("Worlds.Month"));
        wm.setOnNightStarts(getConfig().getString("Worlds.Starts"));
        wm.setOnNightEnds(getConfig().getString("Worlds.Ends"));
        List<String> worlds = getConfig().getStringList("Worlds.List");
        if (worlds.contains("ALLWORLDS")) {
            worlds.clear();
            Bukkit.getServer().getWorlds().forEach(world -> worlds.add(world.getName()));
        }
        Utils.log("&aHappyNewYear will work in this worlds: &f" + worlds.stream().reduce((a, b) -> a + ", " + b).orElse("&cNO WORLD FOUND!"));
        wm.setWorldsName(worlds);
    }

}
