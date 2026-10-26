package com.xSavior_of_God.HappyNewYear.presets;

import com.xSavior_of_God.HappyNewYear.HappyNewYear;
import com.xSavior_of_God.HappyNewYear.utils.CommentedConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresetManager {
    private static final File configFile = new File(HappyNewYear.instance.getDataFolder(), "config.yml");
    private static final Map<String, Preset> presets = new HashMap<>();

    static {
        registerPreset(new NewYearPreset());
    }

    private static void registerPreset(Preset preset) {
        presets.put(preset.getName().toLowerCase(), preset);
    }

    public static void init() {
        // Presets registered
    }

    public static List<String> getPresetNames() {
        return new ArrayList<>(presets.keySet());
    }

    public static boolean presetExists(String name) {
        if (name == null) return false;
        return presets.containsKey(name.toLowerCase());
    }

    public static boolean applyPreset(String name) {
        if (name == null) return false;
        Preset preset = presets.get(name.toLowerCase());
        if (preset == null) return false;
        try {
            if (!HappyNewYear.instance.getDataFolder().exists()) {
                HappyNewYear.instance.getDataFolder().mkdirs();
            }
            if (configFile.exists()) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                File backupDir = new File(HappyNewYear.instance.getDataFolder(), "backup");
                if (!backupDir.exists()) {
                    backupDir.mkdirs();
                }
                File backupFile = new File(backupDir, "config." + timestamp + ".bk.yml");
                Files.copy(configFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            CommentedConfiguration cfg = CommentedConfiguration.loadConfiguration(configFile);
            if (cfg.hasFailed()) {
                cfg = new CommentedConfiguration();
            }

            preset.apply(cfg);

            try {
                cfg.syncWithConfig(configFile, HappyNewYear.instance.getResource("config.yml"), "FireworkEffectTypes", "Worlds.List");
            } catch (IOException e) {
                e.printStackTrace();
            }

            cfg.save(configFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
