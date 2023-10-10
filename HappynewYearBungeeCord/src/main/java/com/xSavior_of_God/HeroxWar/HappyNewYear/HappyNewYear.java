package com.xSavior_of_God.HeroxWar.HappyNewYear;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import com.xSavior_of_God.HeroxWar.HappyNewYear.events.HappyNewYearListeners;
import com.xSavior_of_God.HeroxWar.HappyNewYear.utils.Utils;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class HappyNewYear extends Plugin {
    public static HappyNewYear instance;
    public static boolean Enabled;

    private static Configuration config;

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
        InputStream inputStream = getResourceAsStream("config.yml");
        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        loadConfig();
        getProxy().getPluginManager().registerListener(this, new HappyNewYearListeners());

        if (Enabled) {

        } else
            Utils.log("&4HEY! &fBefore you can use me, you need to configure and enable me from config.yml ( Remember to set \"Enabled\" to true ! )");
    }

    public void onDisable() {
        Utils.log("&eHappy New Year &cDisabled!");
    }

    private Configuration getConfig() {
        return config;
    }

    private void loadConfig() {
        Enabled = getConfig().getBoolean("Enabled");
    }

}
