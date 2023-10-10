package com.xSavior_of_God.HappyNewYear;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
            Enabled,
            ForceStart,
            ForceStop;
    public static int Timer,
            AmountPerPlayer,
            RandomSpawnPosition_Horizontal,
            RandomSpawnPosition_Vertical,
            ExplosionHeight,
            Limit;
    public static List<String>
            FireworkEffectTypes;

    private com.xSavior_of_God.HappyNewYear.tasks.AlwaysNightTask AlwaysNightTask;
    private Task FireworkTask;

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

        ForceStart = false;

        loadConfig();
        getCommand("happynewyear").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(new HappyNewYearListeners(), this);

        if (Enabled) {
            FireworkTask = new Task();
            if (wm.getAlwaysNightEnabled() || wm.getInRealLifeEnabled())
                this.AlwaysNightTask = new AlwaysNightTask();
        }
        else
            Utils.log("&4HEY! &fBefore you can use me, you need to configure and enable me from config.yml ( Remember to set \"Enabled\" to true ! )");
    }

    public void onDisable() {
        if ((this.AlwaysNightTask != null || wm.getAlwaysNightEnabled()) && this.AlwaysNightTask != null)
            this.AlwaysNightTask.StopTask();
        if ((this.FireworkTask != null || Enabled) && this.FireworkTask != null)
            this.FireworkTask.StopTask();
        Utils.log("&eHappy New Year &cDisabled!");
    }

    private void loadConfig() {
        Enabled = getConfig().getBoolean("Enabled");
        Limit = getConfig().getInt("Limit");
        Timer = getConfig().getInt("Timer");
        AmountPerPlayer = getConfig().getInt("AmountPerPlayer");
        RandomSpawnPosition_Horizontal = getConfig().getInt("RandomSpawnPosition.Horizontal");
        RandomSpawnPosition_Vertical = getConfig().getInt("RandomSpawnPosition.Vertical");
        ExplosionHeight = getConfig().getInt("ExplosionHeight");
        FireworkEffectTypes = getConfig().getStringList("FireworkEffectTypes");
        wm.setBlacklist(getConfig().getBoolean("Worlds.Blacklist"));
        wm.setOnlyNightEnabled(getConfig().getBoolean("Worlds.OnlyOnNight.Enabled"));
        wm.setAlwaysNightEnabled(getConfig().getBoolean("Worlds.AlwaysNight.Enabled"));
        wm.setInRealLifeEnabled(getConfig().getBoolean("Worlds.UseRealTime.Enabled"));
        wm.setTimezone(getConfig().getString("Worlds.UseRealTime.Timezone"));
        wm.setMonth(getConfig().getInt("Worlds.Month"));
        wm.setOnlyNightStarts(getConfig().getString("Worlds.Starts"));
        wm.setOnlyNightEnds(getConfig().getString("Worlds.Ends"));
        List<String> worlds = getConfig().getStringList("Worlds.List");
        if (worlds.contains("ALLWORLDS")) {
            worlds.clear();
            Bukkit.getServer().getWorlds().forEach(world -> worlds.add(world.getName()));
        }
        wm.setWorldsName(worlds);
    }

}
