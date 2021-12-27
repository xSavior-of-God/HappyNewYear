package com.xSavior_of_God.HappyNewYear;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main instance;

    public static boolean Enabled, AlwaysNightEnabled, AlwaysNightUseRealTime, AlwaysNightWorldsBlacklist, ForceStart, ForceStop;
    public static int Timer, AmountPerPlayer, RandomSpawnPosition_Horizontal, RandomSpawnPosition_Vertical,
            ExplosionHeight, Limit;
    public static List<String> FireworkEffectTypes, AlwaysNightWorldsList = new ArrayList<String>();
    public static WorldManager wm = new WorldManager();

    public void onEnable() {
        Utilis.log("\r\n" + "\r\n"
                + "&e&l  |  |    \\    _ \\  _ \\ \\ \\  /     \\ |  __| \\ \\      /   \\ \\  /  __|    \\    _ \\   | \r\n"
                + "  __ |   _ \\   __/  __/  \\  /     .  |  _|   \\ \\ \\  /     \\  /   _|    _ \\     /  _| \r\n"
                + " _| _| _/  _\\ _|   _|     _|     _|\\_| ___|   \\_/\\_/       _|   ___| _/  _\\ _|_\\  _)\r\n"
                + "\r\n&cDeveloped by xSavior_of_God with &c❤" + "\r\n&7Version " + getDescription().getVersion() + "\r\n"
                + "\r\n");
        final File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        CommentedConfiguration cfg = CommentedConfiguration.loadConfiguration(configFile);
        try {
            cfg.syncWithConfig(configFile, this.getResource("config.yml"), "AlwaysNight.Worlds.List", "Worlds.List",
                    "FireworkEffectTypes");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        instance = this;
        ForceStart = false;
        loadConfig();
        if (Enabled)
            new Runtime();
        else
            Utilis.log(
                    "\r\n&cHEY! &fBefore you can use me, you need to configure and enable me from config.yml ( Remember to set \"Enabled\" to true ! )\r\n");
        if (AlwaysNightEnabled)
            new AlwayNight();
        getCommand("happynewyear").setExecutor(new Commands());
    }

    public void onDisable() {
        Utilis.log("&e&lHappy New Year &cDisabled!");
    }

    private void loadConfig() {
        Enabled = getConfig().getBoolean("Enabled");
        Limit = getConfig().getInt("Limit");
        AlwaysNightEnabled = getConfig().getBoolean("AlwaysNight.Enabled");
        AlwaysNightUseRealTime = getConfig().getBoolean("AlwaysNight.UseRealTime");
        AlwaysNightWorldsBlacklist = getConfig().getBoolean("AlwaysNight.Worlds.Blacklist");
        AlwaysNightWorldsList = getConfig().getStringList("AlwaysNight.Worlds.List");
        Timer = getConfig().getInt("Timer");
        AmountPerPlayer = getConfig().getInt("AmountPerPlayer");
        RandomSpawnPosition_Horizontal = getConfig().getInt("RandomSpawnPosition.Horizontal");
        RandomSpawnPosition_Vertical = getConfig().getInt("RandomSpawnPosition.Vertical");
        ExplosionHeight = getConfig().getInt("ExplosionHeight");
        FireworkEffectTypes = getConfig().getStringList("FireworkEffectTypes");
        wm.setBlacklist(getConfig().getBoolean("Worlds.Blacklist"));
        wm.setOnlyNightEnabled(getConfig().getBoolean("Worlds.OnlyOnNight.Enabled"));
        wm.setInRealLifeEnabled(getConfig().getBoolean("Worlds.OnlyOnNight.InRealLife.Enabled"));
        wm.setTimezone(getConfig().getString("Worlds.OnlyOnNight.InRealLife.Timezone"));
        wm.setMonth(getConfig().getInt("Worlds.OnlyOnNight.Month"));
        wm.setOnlyNightStarts(getConfig().getString("Worlds.OnlyOnNight.Starts"));
        wm.setOnlyNightEnds(getConfig().getString("Worlds.OnlyOnNight.Ends"));
        List<String> worlds = getConfig().getStringList("Worlds.List");
        if (worlds.contains("ALLWORLDS")) {
            worlds.clear();
            Bukkit.getServer().getWorlds().forEach(world -> {
                worlds.add(world.getName());
            });
        }
        wm.setWorldsName(worlds);
    }

}
